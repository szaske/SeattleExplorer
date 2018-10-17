package com.loc8r.seattleexplorer.remote

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.RETURNS_DEEP_STUBS


@RunWith(JUnit4::class)
class FireStoreManagerTest {

    // Mocks
    private val mockDatabase = mock<FirebaseFirestore>(
            defaultAnswer = RETURNS_DEEP_STUBS
    )
    private val mockQuerySnapshot = mock<QuerySnapshot>()
    private val mockQuerySnapshotTask = mock<Task<QuerySnapshot>>()

    private val firestoreManager = FireStoreManager(mockDatabase)
    private val poiList = listOf(TestDataFactory.makePoiRepo(),TestDataFactory.makePoiRepo())

    @Before
    fun setup(){
        // Step #1 return the query Task on get()
        whenever(mockDatabase.collection(ArgumentMatchers.anyString()).orderBy(ArgumentMatchers.anyString()).get()).thenReturn(mockQuerySnapshotTask)

        // Step #2 return a queryTask when registering the listener
        whenever(mockQuerySnapshotTask.addOnCompleteListener(anyOrNull()))
                .thenReturn(mockQuerySnapshotTask)

        // Step #3 task IS successful is stubbed

        // Step #4 the results of the task is a QuerySnapshot
        whenever(mockQuerySnapshotTask.result).thenReturn(mockQuerySnapshot)

        // Step #5 QuerySnapshot = is empty or not is stubbed

        // Step #6 when we try to convert snapShot to objects
        whenever(mockQuerySnapshot.toObjects(PoiRepository::class.java)).thenReturn(poiList)
    }

    private fun stubQuerySnapshotIsEmpty(boolean: Boolean){
        whenever(mockQuerySnapshot.isEmpty).thenReturn(boolean)
    }

    private fun stubQueryTaskIsSuccessful(boolean: Boolean){
        whenever(mockQuerySnapshotTask.isSuccessful).thenReturn(boolean)
    }


    @Test
    fun getPoisCompletes() {

        // GIVEN
        stubQueryTaskIsSuccessful(true)
        stubQuerySnapshotIsEmpty(false)

        val testObserver = firestoreManager.getPois().test()

        Mockito.verify(mockQuerySnapshotTask, times(1)).addOnCompleteListener(anyOrNull())

        // Trigger callback reply
        // see: https://fernandocejas.com/2014/04/08/unit-testing-asynchronous-methods-with-mockito/
        val captor = argumentCaptor<OnCompleteListener<QuerySnapshot>>()
        verify(mockQuerySnapshotTask).addOnCompleteListener(captor.capture())
        captor.firstValue.onComplete(mockQuerySnapshotTask)

        // THEN
        testObserver
                .assertNoErrors()
                .assertValueCount(1)
                .assertComplete()
    }

    @Test
    fun getPoisErrorsOnEmptyQuerySnapshot() {

        // GIVEN
        stubQueryTaskIsSuccessful(true)
        stubQuerySnapshotIsEmpty(true)

        val testObserver = firestoreManager.getPois().test()

        Mockito.verify(mockQuerySnapshotTask, times(1)).addOnCompleteListener(anyOrNull())

        // Trigger callback reply
        // see: https://fernandocejas.com/2014/04/08/unit-testing-asynchronous-methods-with-mockito/
        val captor = argumentCaptor<OnCompleteListener<QuerySnapshot>>()
        verify(mockQuerySnapshotTask).addOnCompleteListener(captor.capture())
        captor.firstValue.onComplete(mockQuerySnapshotTask)

        testObserver.assertError(IllegalStateException::class.java)

    }

    @Test
    fun getPoisErrorsOnNoSuccessQuerySnapshot() {

        // GIVEN
        stubQueryTaskIsSuccessful(false)
        stubQuerySnapshotIsEmpty(true)

        val testObserver = firestoreManager.getPois().test()

        Mockito.verify(mockQuerySnapshotTask, times(1)).addOnCompleteListener(anyOrNull())

        // Trigger callback reply
        // see: https://fernandocejas.com/2014/04/08/unit-testing-asynchronous-methods-with-mockito/
        val captor = argumentCaptor<OnCompleteListener<QuerySnapshot>>()
        verify(mockQuerySnapshotTask).addOnCompleteListener(captor.capture())
        captor.firstValue.onComplete(mockQuerySnapshotTask)

        // task Exception not mocked, so unknown is passed via Elvis operator
        testObserver.assertError(UnknownError::class.java)

    }


}