package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DataRepositoryTest {

    // Mocks needed
    private val mockCacheDataStoreBroker = mock<CacheDataStoreBroker>()
    private val mockRemoteDataStoreBroker = mock<RemoteDataStoreBroker>()
    private val mockMapper = mock<PoiRepoMapper>()

    // Class under test
    private val dataRepository = DataRepository(mockCacheDataStoreBroker,mockRemoteDataStoreBroker, mockMapper)

    private fun stubCacheArePoisCached(areCached: Boolean) {
        whenever(mockCacheDataStoreBroker.arePoisCached())
                .thenReturn(Single.just(areCached))
    }

    private fun stubCacheIsCacheExpired(areCached: Boolean) {
        whenever(mockCacheDataStoreBroker.isPoisCacheExpired())
                .thenReturn(Single.just(areCached))
    }

    private fun stubCacheSavePois() {
        whenever(mockCacheDataStoreBroker.savePois(any()))
                .thenReturn(Completable.complete())
    }

    private fun stubMapper(to: PoiDomain, from: PoiRepository ) {
        whenever(mockMapper.mapToDomain(from))
                .thenReturn(to)
    }

    private fun stubGetPois(poi_RepoList: List<PoiRepository>) {
        whenever(mockRemoteDataStoreBroker.getPois())
                .thenReturn(Single.just(poi_RepoList))
    }

    /**
     * This is a complex test and requires a bunch of stubs
     * Workflow steps:
     *  1. cachebroker says cache is empty (false)
     *  2. cachebroker says cache is not expired (false)
     *  3. getPois returns List<Pois_Repo>
     *  4. cache completes save
     *  5. mapper maps anything
     */

    @Test
    fun getPoisCompletes(){
        /* Given */
        val poiRepository = TestDataFactory.makePoiRepo()
        val poiDomain = TestDataFactory.makePoiDomain()

        /* When */
        stubCacheArePoisCached(false)
        stubCacheIsCacheExpired(false)
        stubGetPois(listOf(poiRepository, poiRepository))
        stubCacheSavePois()
        // note that the mapper is not actually returning the correct conversion, but
        // remember we're not testing the mapper
        stubMapper(poiDomain,poiRepository)

        /* Then */
        val testObserver = dataRepository.getPois().test()
        testObserver.assertComplete()
    }

    @Test
    fun getPoisReturnsCorrectData(){
        /* Given */
        val poiRepository = TestDataFactory.makePoiRepo()
        val poiDomain = TestDataFactory.makePoiDomain()

        /* When */
        stubCacheArePoisCached(false)
        stubCacheIsCacheExpired(false)
        stubGetPois(listOf(poiRepository, poiRepository))
        stubCacheSavePois()
        stubMapper(poiDomain,poiRepository)

        /* Then */
        val testObserver = dataRepository.getPois().test()
        testObserver.assertValue(listOf(poiDomain, poiDomain))

    }

}