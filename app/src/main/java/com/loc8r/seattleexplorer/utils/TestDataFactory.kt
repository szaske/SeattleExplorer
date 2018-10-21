package com.loc8r.seattleexplorer.utils

import com.loc8r.seattleexplorer.cache.models.PoiCache
import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import com.loc8r.seattleexplorer.repository.models.CollectionRepository
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object TestDataFactory {
    private fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    private fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1,20)
    }

    private fun randomDouble(): Double {
        return ThreadLocalRandom.current().nextDouble(1.0,500.0)
    }

    private fun randomLatitude(): Double {
        return ThreadLocalRandom.current().nextDouble(47.5,47.7)
    }

    private fun randomLongitude(): Double {
        return ThreadLocalRandom.current().nextDouble(-122.45,-122.2)
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makePoiDomain(): PoiDomain {
        return PoiDomain(randomString(),randomString(),randomString(),randomString(),
                randomLatitude(), randomLongitude(), randomDouble(), randomDouble(),
                randomString(), randomInt(), randomInt(), randomString())
    }

    fun makePoiDomainList(count: Int): List<PoiDomain> {
        val pois = mutableListOf<PoiDomain>()
        repeat(count){
            pois.add(makePoiDomain())
        }
        return pois
    }

    fun makePoiPresentation(): PoiPresentation {
        return PoiPresentation(randomString(),randomString(),randomString(),randomString(),
                randomLatitude(), randomLongitude(), randomDouble(), randomDouble(),
                randomString(), randomInt(), randomInt(), randomString())
    }

    fun makePoiPresentationList(count: Int): List<PoiPresentation> {
        val pois = mutableListOf<PoiPresentation>()
        repeat(count){
            pois.add(makePoiPresentation())
        }
        return pois
    }

    fun makePoiRepo(): PoiRepository {
        return PoiRepository(randomString(),randomString(),randomString(),randomString(),
                randomLatitude(), randomLongitude(), randomDouble(), randomDouble(),
                randomString(), randomInt(), randomInt(), randomString())
    }

    fun makePoiRepoList(count: Int): List<PoiRepository> {
        val pois = mutableListOf<PoiRepository>()
        repeat(count){
            pois.add(makePoiRepo())
        }
        return pois
    }

    fun makePoiCache(): PoiCache {
        return PoiCache(randomString(),randomString(),randomString(),randomString(),
                randomLatitude(), randomLongitude(), randomDouble(), randomDouble(),
                randomString(), randomInt(), randomInt(), randomString())
    }

    fun makePoicacheList(count: Int): List<PoiCache> {
        val pois = mutableListOf<PoiCache>()
        repeat(count){
            pois.add(makePoiCache())
        }
        return pois
    }

    //////////////////////////////////////////
    // Collection Factory
    //////////////////////////////////////////

    fun makeCollectionRepo(): CollectionRepository {
        return CollectionRepository(randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makeColRepoList(count: Int): List<CollectionRepository> {
        val cols = mutableListOf<CollectionRepository>()
        repeat(count){
            cols.add(makeCollectionRepo())
        }
        return cols
    }

    fun makeCollectionDomain(): CollectionDomain {
        return CollectionDomain(randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString(),
                randomString())
    }

    fun makeColDomainList(count: Int): List<CollectionDomain> {
        val cols = mutableListOf<CollectionDomain>()
        repeat(count){
            cols.add(makeCollectionDomain())
        }
        return cols
    }




}