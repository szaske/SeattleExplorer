package com.loc8r.seattleexplorer.domain

import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.interfaces.ObservingThread
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class GetPoisTest {
    private val repository = mock<DomainRepository>()
    private val observingThread = mock<ObservingThread>()

    private val getPois = GetPois(repository,observingThread)

    private fun stubGetPois(observable: Observable<List<PoiDomain>>){
        whenever(repository.getPois()).thenReturn(observable)
    }

    @Test
    fun getPoisCompletes(){
        stubGetPois(Observable.just(TestDataFactory.makePoiDomainList(1)))
        val testObserver = getPois.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getPoisReturnsCorrectData(){
        val pois = TestDataFactory.makePoiDomainList(1)
        stubGetPois(Observable.just(pois))
        val testObserver = getPois.buildUseCaseObservable().test()
        testObserver.assertValue(pois)
    }
}