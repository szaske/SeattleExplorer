package com.loc8r.seattleexplorer.remote.models

import com.google.firebase.firestore.Exclude
import com.loc8r.seattleexplorer.repository.models.PoiRepository

/**
 * Excluded fields are temporarily in Firestore for backwards compat with other client app.
 */
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
                                val stampText: String = "",
                                @Exclude val stamp: String = "",
                                @Exclude val stamped: Boolean = false,
                                @Exclude val stampChecked: Boolean = false){
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
