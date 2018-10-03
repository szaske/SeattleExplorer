package com.loc8r.seattleexplorer.presentation.utils

import com.loc8r.seattleexplorer.domain.models.Poi_Domain
import com.loc8r.seattleexplorer.presentation.models.Poi_Presentation
import javax.inject.Inject

class PoiMapper @Inject constructor() {
    fun mapToPresentation(domainModel: Poi_Domain): Poi_Presentation {
        return Poi_Presentation(domainModel.id,domainModel.name,domainModel.description,
                domainModel.img_url,domainModel.latitude,domainModel.longitude,domainModel.imgFocalpointX,
                domainModel.imgFocalpointY,domainModel.collection,domainModel.collectionPosition,
                domainModel.release,domainModel.stampText)
    }
}