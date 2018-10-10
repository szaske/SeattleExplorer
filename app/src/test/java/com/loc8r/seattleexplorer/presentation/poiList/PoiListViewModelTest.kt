package com.loc8r.seattleexplorer.presentation.poiList

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.loc8r.seattleexplorer.presentation.utils.PoiMapper
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.*
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class PoiListViewModelTest {

    // This test rule swaps the executor and allows us to test Jetpack components
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //Mock objects needed to instantiate the class under test
    private val mockGetPois = mock<GetPois>()
    private val mockMapper = mock<PoiMapper>()
    private val mockObserver = mock<Observer<List<PoiPresentation>>>()

    // Class being tested
    private val poiListViewModel = PoiListViewModel(mockGetPois,mockMapper)

    private fun stubMapper(poi_Domain: PoiDomain, poiPresentation: PoiPresentation) {
        whenever(mockMapper.mapToPresentation(poi_Domain)).thenReturn(poiPresentation)
    }

    @Test
    fun fetchAllPoisExecutesUseCaseOnStart(){

        verify(mockGetPois, times(1)).execute(com.nhaarman.mockitokotlin2.any(),eq(null))
    }

    @Test
    fun poiSubscriberOnNextBecomesLiveData(){
        /* Given */
        val poiDomain = TestDataFactory.makePoiDomain()
        val poiPresentation = TestDataFactory.makePoiPresentation()
        stubMapper(poiDomain,poiPresentation)

        // Create mockObserver on poiData liveData
        poiListViewModel.getAllPois().observeForever(mockObserver)

        /* When */   // insert item into RxJava Observable
        poiListViewModel.PoiSubscriber().onNext(listOf(poiDomain))

        /* Then */   // Check live data
        val results = poiListViewModel.getAllPois().value
        assertEquals(results?.get(0)?.name,poiPresentation.name)
    }
}