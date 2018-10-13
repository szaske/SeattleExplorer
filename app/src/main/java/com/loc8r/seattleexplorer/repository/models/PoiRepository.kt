package com.loc8r.seattleexplorer.repository.models

data class PoiRepository(
        var id: String,
        val name: String,
        val description: String,
        val img_url: String,
        val latitude: Double,
        val longitude: Double,
        val imgFocalpointX: Double,
        val imgFocalpointY: Double,
        val collection: String,
        val collectionPosition: Int,
        val release: Int,
        val stampText: String)
{
    constructor(): this("","","","",0.0,0.0,
            0.0,0.0,"",0,0,"")
}
