package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import javax.inject.Inject

open class PoiRepoMapper @Inject constructor() {
    open fun mapToDomain(poi_Repository: PoiRepository): PoiDomain {
        return PoiDomain(poi_Repository.id,poi_Repository.name,poi_Repository.description,
                poi_Repository.img_url,poi_Repository.latitude,poi_Repository.longitude,
                poi_Repository.imgFocalpointX,poi_Repository.imgFocalpointY,
                poi_Repository.collection,poi_Repository.collectionPosition,
                poi_Repository.release,poi_Repository.stampText)
    }
}