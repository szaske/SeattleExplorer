package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.repository.models.CollectionRepository
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
    private val mockMapper = mock<RepositoryMapper>()

    // Class under test
    private val dataRepository = DataRepository(mockCacheDataStoreBroker,mockRemoteDataStoreBroker, mockMapper)

    private fun stubCacheArePoisCached(areCached: Boolean) {
        whenever(mockCacheDataStoreBroker.arePoisCached())
                .thenReturn(Single.just(areCached))
    }

    private fun stubCacheIsPoisCacheExpired(areCached: Boolean) {
        whenever(mockCacheDataStoreBroker.isPoisCacheExpired())
                .thenReturn(Single.just(areCached))
    }

    private fun stubCacheSavePois() {
        whenever(mockCacheDataStoreBroker.savePois(any()))
                .thenReturn(Completable.complete())
    }

    private fun stubPoiMapper(to: PoiDomain, from: PoiRepository ) {
        whenever(mockMapper.mapPoiToDomain(from))
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
        stubCacheIsPoisCacheExpired(false)
        stubGetPois(listOf(poiRepository, poiRepository))
        stubCacheSavePois()
        // note that the mapper is not actually returning the correct conversion, but
        // remember we're not testing the mapper
        stubPoiMapper(poiDomain,poiRepository)

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
        stubCacheIsPoisCacheExpired(false)
        stubGetPois(listOf(poiRepository, poiRepository))
        stubCacheSavePois()
        stubPoiMapper(poiDomain,poiRepository)

        /* Then */
        val testObserver = dataRepository.getPois().test()
        testObserver.assertValue(listOf(poiDomain, poiDomain))

    }

    private fun stubCacheAreColsCached(areCached: Boolean) {
        whenever(mockCacheDataStoreBroker.areCollectionsCached())
                .thenReturn(Single.just(areCached))
    }

    private fun stubCacheIsColCacheExpired(areCached: Boolean) {
        whenever(mockCacheDataStoreBroker.isCollectionsCacheExpired())
                .thenReturn(Single.just(areCached))
    }

    private fun stubGetCollections(col_RepoList: List<CollectionRepository>) {
        whenever(mockRemoteDataStoreBroker.getCollections())
                .thenReturn(Single.just(col_RepoList))
    }

    private fun stubCacheSaveCollections() {
        whenever(mockCacheDataStoreBroker.saveCollections(any()))
                .thenReturn(Completable.complete())
    }

    private fun stubColMapper(to: CollectionDomain, from: CollectionRepository ) {
        whenever(mockMapper.mapColToDomain(from))
                .thenReturn(to)
    }

    @Test
    fun getCollectionsCompletes(){
        /* Given */
        val colRepository = TestDataFactory.makeCollectionRepo()
        val colDomain = TestDataFactory.makeCollectionDomain()

        /* When */
        stubCacheAreColsCached(false)
        stubCacheIsColCacheExpired(false)
        stubGetCollections(listOf(colRepository, colRepository))
        stubCacheSaveCollections()
        // note that the mapper is not actually returning the correct conversion, but
        // remember we're not testing the mapper
        stubColMapper(colDomain,colRepository)

        /* Then */
        val testObserver = dataRepository.getCollections().test()
        testObserver.assertComplete()
    }

    @Test
    fun getCollectionsReturnsCorrectData(){
        /* Given */
        val colRepository = TestDataFactory.makeCollectionRepo()
        val colDomain = TestDataFactory.makeCollectionDomain()

        /* When */
        stubCacheAreColsCached(false)
        stubCacheIsColCacheExpired(false)
        stubGetCollections(listOf(colRepository, colRepository))
        stubCacheSaveCollections()
        // note that the mapper is not actually returning the correct conversion, but
        // remember we're not testing the mapper
        stubColMapper(colDomain,colRepository)

        /* Then */
        val testObserver = dataRepository.getCollections().test()
        testObserver.assertValue(listOf(colDomain, colDomain))
    }
}