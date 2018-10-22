package com.loc8r.seattleexplorer.cache.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.loc8r.seattleexplorer.cache.cacheStatus.CacheStatusConstants


@Entity(tableName = CacheStatusConstants.TABLE_NAME)
data class CacheStatus(@PrimaryKey
                       @NonNull
                       var id: String,
                       var lastCacheTime: Long)
