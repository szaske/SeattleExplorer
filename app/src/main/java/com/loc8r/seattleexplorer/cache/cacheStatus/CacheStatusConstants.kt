package com.loc8r.seattleexplorer.cache.cacheStatus

object CacheStatusConstants {
    const val TABLE_NAME = "cachestatus"
    const val ID_COLUMN = "id"
    const val QUERY_CACHESTATUS_POIS = "SELECT * FROM $TABLE_NAME WHERE $ID_COLUMN='POIS'"
    const val QUERY_CACHESTATUS_COLS = "SELECT * FROM $TABLE_NAME WHERE $ID_COLUMN='COLLECTIONS'"
}