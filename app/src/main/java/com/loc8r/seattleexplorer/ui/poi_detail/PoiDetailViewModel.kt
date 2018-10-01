package com.loc8r.seattleexplorer.ui.poi_detail

import android.arch.lifecycle.ViewModel
import com.loc8r.seattleexplorer.domain.GetPois
import javax.inject.Inject

open class PoiDetailViewModel @Inject constructor(
        private val getPois: GetPois
) : ViewModel() {

    fun getMessage(): String {
        return getPois.getPoiDetailMessage()
    }
}
