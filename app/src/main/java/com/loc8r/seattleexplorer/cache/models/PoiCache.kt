package com.loc8r.seattleexplorer.cache.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheConstants

@Entity(tableName = PoiCacheConstants.TABLE_NAME)
data class PoiCache (
    @PrimaryKey
    @ColumnInfo(name = PoiCacheConstants.COLUMN_POI_ID)
    var id: String,
    var name: String,
    var description: String,
    var img_url: String,
    val latitude: Double,
    val longitude: Double,
    val imgFocalpointX: Double,
    val imgFocalpointY: Double,
    val collection: String,
    val collectionPosition: Int,
    val release: Int,
    val stampText: String
)