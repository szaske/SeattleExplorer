package com.loc8r.seattleexplorer.presentation.poi_list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.presentation.models.Poi_Presentation
import com.loc8r.seattleexplorer.presentation.utils.PoiMapper
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class PoiListViewModelTest {

    // This test rule swaps the executor and allows us to test Jetpack components
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //Mock objects needed to instantiate the class under test
    @Mock lateinit var mockGetPois: GetPois
    @Mock lateinit var mockMapper: PoiMapper
    @Mock lateinit var mockObserver: Observer<List<Poi_Presentation>>

    // Class being tested
    lateinit var poiListViewModel: PoiListViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        poiListViewModel = PoiListViewModel(mockGetPois,mockMapper)
    }

//    private fun stubMapper(poiPresentation: Poi_Presentation) {
//        whenever(mockMapper.mapToPresentation(anyObject())).thenReturn(poiPresentation)
//    }

    @Test
    fun fetchAllPoisExecutesUseCaseOnStart(){

        verify(mockGetPois, times(1)).execute(com.nhaarman.mockitokotlin2.any(),eq(null))
    }

    // Not a great unit test as the logic includes mapper code being run.  Unfortunately I have not
    // found a way to stub the mapper
    @Test
    fun poiSubscriberOnNextBecomesLiveData(){
        val poiDomains = TestDataFactory.makePoiDomainList(2)
        // stubMapper(poiPresentation)

        // Create mockObserver on poiData liveData
        poiListViewModel.getAllPois().observeForever(mockObserver)

        // insert item into RxJava Observable
        poiListViewModel.PoiSubscriber().onNext(poiDomains)

        // Check live data
        val results = poiListViewModel.getAllPois().value

        assertEquals(results?.get(0)?.name,poiDomains[0].name)
    }
}