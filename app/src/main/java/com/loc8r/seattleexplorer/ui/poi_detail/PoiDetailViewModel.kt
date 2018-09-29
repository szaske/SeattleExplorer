package com.loc8r.seattleexplorer.ui.poi_detail

import android.arch.lifecycle.ViewModel

class PoiDetailViewModel : ViewModel() {

    val detailMessage: String = "Steve was here"

    fun getMessage(): String {
        return detailMessage
    }
}
