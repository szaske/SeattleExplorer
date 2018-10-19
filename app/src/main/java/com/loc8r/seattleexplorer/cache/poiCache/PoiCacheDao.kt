package com.loc8r.seattleexplorer.cache.poiCache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.loc8r.seattleexplorer.cache.models.PoiCache
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheConstants.DELETE_POIS
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheConstants.EXISTS
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheConstants.QUERY_POIS
import io.reactivex.*

@Dao
abstract class PoiCacheDao {

    @Query(QUERY_POIS)
    abstract fun getPois(): Maybe<List<PoiCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun savePois(pois: List<PoiCache>)

//    @Query(DELETE_POIS)
//    abstract fun deletePois()

    @Query(EXISTS)
    abstract fun arePoisCached(): Single<Boolean>
}