package com.loc8r.seattleexplorer.remote.models

import com.loc8r.seattleexplorer.repository.models.PoiRepository

data class FireStorePoiResponse(var id: String = "",
                                val name: String = "",
                                val description: String = "",
                                val img_url: String = "",
                                val latitude: Double = 0.0,
                                val longitude: Double = 0.0,
                                val imgFocalpointX: Double = 0.0,
                                val imgFocalpointY: Double = 0.0,
                                val collection: String = "",
                                val collectionPosition: Int = 0,
                                val release: Int = 0,
                                val stampText: String = ""){
    fun mapToRepository() = PoiRepository(id,
            name,
            description,
            img_url,
            latitude,
            longitude,
            imgFocalpointX,
            imgFocalpointY,
            collection,
            collectionPosition,
            release,
            stampText)
}
