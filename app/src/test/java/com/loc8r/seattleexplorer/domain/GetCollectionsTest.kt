package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.interfaces.DomainRepositoryInterface
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThreadInterface
import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class GetCollectionsTest {
    private val repository = mock<DomainRepositoryInterface>()
    private val observingThread = mock<ObservingThreadInterface>()

    private val getCollections = GetCollections(repository,observingThread)

    private fun stubGetCollections(observable: Observable<List<CollectionDomain>>){
        whenever(repository.getCollections()).thenReturn(observable)
    }

    @Test
    fun getPoisCompletes(){
        stubGetCollections(Observable.just(TestDataFactory.makeColDomainList(1)))
        val testObserver = getCollections.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getPoisReturnsCorrectData(){
        val cols = TestDataFactory.makeColDomainList(1)
        stubGetCollections(Observable.just(cols))
        val testObserver = getCollections.buildUseCaseObservable().test()
        testObserver.assertValue(cols)
    }
}