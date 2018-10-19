package com.loc8r.seattleexplorer.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.loc8r.seattleexplorer.cache.cacheStatus.CacheStatusDao
import com.loc8r.seattleexplorer.cache.models.CacheStatus
import com.loc8r.seattleexplorer.cache.models.PoiCache
import com.loc8r.seattleexplorer.cache.poiCache.PoiCacheDao

// If you alter the DB schema you need to clear the apps data in Android settings
// or you'll get a DB integrity error tied to the version number
@Database(entities = arrayOf(PoiCache::class,
        CacheStatus::class), version = 1)
abstract class ExplorerDatabase: RoomDatabase(){

    // This gives us a Dao, so out DB has a reference to Poi table
    abstract fun poiCacheDao(): PoiCacheDao

    // This gives us a Dao, so out DB has a reference to these abilities
    abstract fun cacheStatusDao(): CacheStatusDao

    // This is similar to static methods in Java
    companion object {

        //We make it a Singleton
        private var INSTANCE: ExplorerDatabase? = null

        // Any is the Kotlin super class.  I think this just makes a generic object...which as an
        // Any has equals and hashcode
        private val lock = Any()

        fun getInstance(context: Context): ExplorerDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {

                        // Can use .allowMainThreadQueries() for testing purposes
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                ExplorerDatabase::class.java, "seattleexplorer.db")
                                .build()
                    }
                    return INSTANCE as ExplorerDatabase
                }
            }
            return INSTANCE as ExplorerDatabase
        }
    }
}