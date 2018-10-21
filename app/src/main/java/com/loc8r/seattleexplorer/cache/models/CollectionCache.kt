package com.loc8r.seattleexplorer.cache.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.loc8r.seattleexplorer.cache.CollectionsCache.CollectionCacheConstants
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheConstants

@Entity(tableName = CollectionCacheConstants.TABLE_NAME)
data class CollectionCache (
    @PrimaryKey
    @ColumnInfo(name = CollectionCacheConstants.COLUMN_COL_ID)
    var id: String,
    var name: String,
    var description: String,
    var date: String,
    var img_url: String,
    val color: String,
    val textColor: String
)