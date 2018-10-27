package com.loc8r.seattleexplorer.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.loc8r.seattleexplorer.domain.GetCollections
import com.loc8r.seattleexplorer.domain.GetPois
import com.loc8r.seattleexplorer.domain.RefreshAll
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.loc8r.seattleexplorer.presentation.utils.PresentationMapper
import com.loc8r.seattleexplorer.presentation.utils.Resource
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SharedViewModelTest {
    // This test rule swaps the executor and allows us to test Jetpack components
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    //Mock objects needed to instantiate the class under test
    private val mockGetPois = mock<GetPois>()
    private val mockGetCollections = mock<GetCollections>()
    private val mockRefreshAll = mock<RefreshAll>()
    private val mockMapper = mock<PresentationMapper>()
    private val mockObserver = mock<Observer<Resource<List<PoiPresentation>>>>()

    // Class being tested
    private lateinit var sharedViewModel: SharedViewModel

    @Before
    fun setup() {
        // Configures RxJava to use the Trampoline scheduler, allowing us to unit test RxJava code
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {
            _ -> Schedulers.trampoline()
        }
        sharedViewModel = SharedViewModel(mockGetCollections,mockGetPois,mockRefreshAll,mockMapper)
    }


    private fun stubMapper(poi_Domain: PoiDomain, poiPresentation: PoiPresentation) {
        whenever(mockMapper.mapPoiToPresentation(poi_Domain)).thenReturn(poiPresentation)
    }

    // TODO Need to fill out and add more unit tests

    @Test
    fun poiSubscriberOnNextBecomesLiveData(){
        /* Given */
        val poiDomain = TestDataFactory.makePoiDomain()
        val poiPresentation = TestDataFactory.makePoiPresentation()
        stubMapper(poiDomain,poiPresentation)

        // Create mockObserver on poiData liveData
        sharedViewModel.getAllPois().observeForever(mockObserver)

        /* When */   // insert item into RxJava Observable
        sharedViewModel.PoiSubscriber().onNext(listOf(poiDomain))

        /* Then */   // Check live data
        val results = sharedViewModel.getAllPois().value
        assertEquals(results?.data?.get(0)?.name,poiPresentation.name)
    }
}