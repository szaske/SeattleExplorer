package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.utils.TestDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepositoryMapperTest {

    //create a new RepositoryMapper
    private val mapper = RepositoryMapper()

    @Test
    fun mapToDomainMapsCorrectly(){
        /* Given */
        val poiRepository = TestDataFactory.makePoiRepo()

        /* When */
        val results = mapper.mapPoiToDomain(poiRepository)

        /* Then */
        assertEquals(poiRepository.id, results.id)
        assertEquals(poiRepository.name, results.name)
        assertEquals(poiRepository.description, results.description)
        assertEquals(poiRepository.img_url, results.img_url)
        assertEquals(poiRepository.latitude, results.latitude,.001)
        assertEquals(poiRepository.longitude, results.longitude,.001)
        assertEquals(poiRepository.imgFocalpointX, results.imgFocalpointX,.001)
        assertEquals(poiRepository.imgFocalpointY, results.imgFocalpointY,.001)
        assertEquals(poiRepository.collection, results.collection)
        assertEquals(poiRepository.collectionPosition, results.collectionPosition)
        assertEquals(poiRepository.release, results.release)
        assertEquals(poiRepository.stampText, results.stampText)
    }
}