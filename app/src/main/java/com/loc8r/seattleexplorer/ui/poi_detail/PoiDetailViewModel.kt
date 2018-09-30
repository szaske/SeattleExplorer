package com.loc8r.seattleexplorer.ui.poi_detail

import android.arch.lifecycle.ViewModel
import com.loc8r.seattleexplorer.domain.GetPois

class PoiDetailViewModel : ViewModel() {

    // Step #5 changes.  I'm instantiating this use case class in the domain layer and getting my info from that.
    private val getPois = GetPois()

    fun getMessage(): String {
        return getPois.getPoiDetailMessage()
    }
}
