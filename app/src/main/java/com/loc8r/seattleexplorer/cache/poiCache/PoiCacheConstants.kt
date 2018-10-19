package com.loc8r.seattleexplorer.cache.poiCache


object PoiCacheConstants {
    const val TABLE_NAME = "pois"
    const val COLUMN_POI_ID = "poi_id"
    const val QUERY_POIS = "SELECT * FROM $TABLE_NAME"
    const val DELETE_POIS = "DELETE FROM $TABLE_NAME"
    const val EXISTS = "SELECT case when count($COLUMN_POI_ID) > 0 then 1 else 0 end FROM $TABLE_NAME"
}