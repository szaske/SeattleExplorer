package com.loc8r.seattleexplorer.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import com.loc8r.seattleexplorer.cache.models.PoiCache
import com.loc8r.seattleexplorer.cache.poiCache.CacheMapper
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import com.loc8r.seattleexplorer.utils.TestDataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.util.concurrent.TimeUnit

// I use the Robolectric Test runner because I'm going to be using parts of the Android framework and I need this accessible to me
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExplorerCacheImplTest {

    // I add this rule from the Jetpack testing library because I'm using Maybes in our tests,
    // this ensures that my tests are executed instantly, so I can retrieve the results and assert
    // the values that I get back from them.
    // see: https://developer.android.com/reference/android/arch/core/executor/testing/InstantTaskExecutorRule
    // see: http://www.baeldung.com/kotlin-jvm-field-annotation
    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    // I need a database and mapper to test the Projects Cache implementation, so I'll create them.
    // Here I use the Robolectric RuntimeEnvironment
    private val explorerDatabase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ExplorerDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val mockPoiCacheMapper = mock<CacheMapper>()

    // Class Under Test
    private val explorerCacheImpl = ExplorerCacheImpl(explorerDatabase,mockPoiCacheMapper)

    @Before
    fun setUp() {

    }

    fun stubMapperToCache(poi: PoiCache){
        whenever(mockPoiCacheMapper.mapPoiToCache(any())).thenReturn(poi)
    }

    fun stubMapperToRepo(poi: PoiRepository){
        whenever(mockPoiCacheMapper.mapPoiToRepo(any())).thenReturn(poi)
    }

    @Test
    fun savePoisCompletes(){
        val poiCache = TestDataFactory.makePoiCache()
        stubMapperToCache(poiCache)
        val pois = TestDataFactory.makePoiRepoList(3)
        val testObserver = explorerCacheImpl.savePois(pois).test()
        testObserver.assertComplete()
    }

    @Test
    fun savePoisSavesPoisToDB(){
        val poiCache = TestDataFactory.makePoiCache()
        val poiRepo = TestDataFactory.makePoiRepo()
        stubMapperToCache(poiCache)
        stubMapperToRepo(poiRepo)
        val pois = listOf(poiRepo)
        val testObserver = explorerCacheImpl.savePois(pois).test()
        testObserver.assertComplete()

        val testObserver2 = explorerCacheImpl.getPois().test()

        testObserver2.assertValue(listOf(poiRepo))
        assertEquals(testObserver2.values()[0].firstOrNull()?.name,poiRepo.name)
    }


    @Test
    fun setLastCacheTimeCompletes(){
        val testObserver = explorerCacheImpl.setLastPoisCacheTime(10L).test()
        testObserver.assertComplete()
    }

    @Test
    fun setLastCacheSavesToDB(){
        val testObserver = explorerCacheImpl.setLastPoisCacheTime(10L).test()
        val testCacheStatusObserver = explorerDatabase.cacheStatusDao().getPoisCacheStatus().test()

        assertEquals(10L,testCacheStatusObserver.values()[0].lastCacheTime)
    }

    @Test
    fun getPoisCompletes(){
        val testObserver = explorerCacheImpl.getPois().test()
        testObserver.assertComplete()
    }

    // Since I'm not setting the cache, it should return "Expired" by default
    @Test
    fun isPoisCacheExpiredReturnsTrueWhenNull(){
        val testObserver = explorerCacheImpl.isPoisCacheExpired().test()
        testObserver.assertValue(true)
    }

    // Here I set the cache to 23 hours ago, so it should pass as false since the cache as
    // not yet expired
    @Test
    fun isPoisCacheExpiredReturnsFalseWhenWithin24Hours(){
        val cacheTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(23)
        explorerCacheImpl.setLastPoisCacheTime(cacheTime).test()
        val testObserver = explorerCacheImpl.isPoisCacheExpired().test()
        testObserver.assertValue(false)
    }

    // Here I set the cache to 24 hours ago, so it should pass as expired, since the expiration time is set for 24 hr
    @Test
    fun isProjectsCacheExpiredReturnsTrueWhenOldCacheTime(){
        val cacheTime = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
        explorerCacheImpl.setLastPoisCacheTime(cacheTime).test()
        val testObserver = explorerCacheImpl.isPoisCacheExpired().test()
        testObserver.assertValue(true)
    }

    @Test
    fun arePoisCachedReturnsFalseOnNull(){
        val testObserver = explorerCacheImpl.arePoisCached().test()
        testObserver.assertValue(false)
    }

    @Test
    fun arePoisCachedReturnsTrueOnAny(){

        val poiCache = TestDataFactory.makePoiCache()
        stubMapperToCache(poiCache)
        val pois = TestDataFactory.makePoiRepoList(3)
        explorerCacheImpl.savePois(pois).test()

        val testObserver = explorerCacheImpl.arePoisCached().test()
        testObserver.assertValue(true)
    }
}