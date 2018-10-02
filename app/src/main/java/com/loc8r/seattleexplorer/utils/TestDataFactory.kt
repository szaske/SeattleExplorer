package com.loc8r.seattleexplorer.utils

import com.loc8r.seattleexplorer.domain.models.Poi_Domain
import java.util.*
import java.util.concurrent.ThreadLocalRandom

object TestDataFactory {
    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(1,20)
    }

    fun randomDouble(): Double {
        return ThreadLocalRandom.current().nextDouble(1.0,500.0)
    }

    fun randomLatitude(): Double {
        return ThreadLocalRandom.current().nextDouble(47.5,47.7)
    }

    fun randomLongitude(): Double {
        return ThreadLocalRandom.current().nextDouble(-122.45,-122.2)
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makePoi_Domain(): Poi_Domain {
        return Poi_Domain(randomString(),randomString(),randomString(),randomString(),
                randomLatitude(), randomLongitude(), randomDouble(), randomDouble(),
                randomString(), randomInt(), randomInt(), randomString())
    }

    fun makePOIList(count: Int): List<Poi_Domain> {
        val POIs = mutableListOf<Poi_Domain>()
        repeat(count){
            POIs.add(makePoi_Domain())
        }
        return POIs
    }
}