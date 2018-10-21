package com.loc8r.seattleexplorer.cache.CollectionsCache


object CollectionCacheConstants {
    const val TABLE_NAME = "collections"
    const val COLUMN_COL_ID = "col_id"
    const val QUERY_COLS = "SELECT * FROM $TABLE_NAME"
    const val DELETE_COLS = "DELETE FROM $TABLE_NAME"
    const val EXISTS = "SELECT case when count($COLUMN_COL_ID) > 0 then 1 else 0 end FROM $TABLE_NAME"
}