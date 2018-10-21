package com.loc8r.seattleexplorer.cache.CollectionsCache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.loc8r.seattleexplorer.cache.CollectionsCache.CollectionCacheConstants.EXISTS
import com.loc8r.seattleexplorer.cache.CollectionsCache.CollectionCacheConstants.QUERY_COLS
import com.loc8r.seattleexplorer.cache.models.CollectionCache
import io.reactivex.*

@Dao
abstract class CollectionCacheDao {

    @Query(QUERY_COLS)
    abstract fun getCollections(): Maybe<List<CollectionCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveCollections(collections: List<CollectionCache>)

//    @Query(DELETE_COLLECTION)
//    abstract fun deleteCollections()

    @Query(EXISTS)
    abstract fun areCollectionsCached(): Single<Boolean>
}