package com.loc8r.seattleexplorer.cache.cacheStatus

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.loc8r.seattleexplorer.cache.cacheStatus.CacheStatusConstants.QUERY_CACHESTATUS
import com.loc8r.seattleexplorer.cache.models.CacheStatus
import io.reactivex.Single

@Dao
abstract class CacheStatusDao {
    @Query(QUERY_CACHESTATUS)
    abstract fun getCacheStatus(): Single<CacheStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setCacheStatus(cacheStatus: CacheStatus)
}