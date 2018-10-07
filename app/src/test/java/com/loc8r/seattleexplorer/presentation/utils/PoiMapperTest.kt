package com.loc8r.seattleexplorer.presentation.utils

import com.loc8r.seattleexplorer.utils.TestDataFactory
import org.junit.Test

import org.junit.Assert.*

class PoiMapperTest {

    // Class under Test
    private val poiMapper = PoiMapper()

    @Test
    fun mapToPresentationMapsDataCorrectly() {
        val poiDomain = TestDataFactory.makePoi_Domain()
        val poiPresentation = poiMapper.mapToPresentation(poiDomain)

        assertEquals(poiDomain.id, poiPresentation.id)
        assertEquals(poiDomain.name, poiPresentation.name)
        assertEquals(poiDomain.description, poiPresentation.description)
        assertEquals(poiDomain.img_url, poiPresentation.img_url)
        assertEquals(poiDomain.latitude, poiPresentation.latitude,.001)
        assertEquals(poiDomain.longitude, poiPresentation.longitude,.001)
        assertEquals(poiDomain.imgFocalpointX, poiPresentation.imgFocalpointX,.001)
        assertEquals(poiDomain.imgFocalpointY, poiPresentation.imgFocalpointY,.001)
        assertEquals(poiDomain.collection, poiPresentation.collection)
        assertEquals(poiDomain.collectionPosition, poiPresentation.collectionPosition)
        assertEquals(poiDomain.release, poiPresentation.release)
        assertEquals(poiDomain.stampText, poiPresentation.stampText)
    }
}