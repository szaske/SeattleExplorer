package com.loc8r.seattleexplorer.cache.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.loc8r.seattleexplorer.cache.cacheStatus.CacheStatusConstants

@Entity(tableName = CacheStatusConstants.TABLE_NAME)
data class CacheStatus(@PrimaryKey(autoGenerate = true)
                       var id: Int = -1,
                       var lastCacheTime: Long)
