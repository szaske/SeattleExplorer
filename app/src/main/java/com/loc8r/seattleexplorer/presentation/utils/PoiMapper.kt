package com.loc8r.seattleexplorer.presentation.utils

import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import javax.inject.Inject

open class PoiMapper @Inject constructor() {

    open fun mapToPresentation(domainModel: PoiDomain): PoiPresentation {

        return PoiPresentation(domainModel.id,domainModel.name,domainModel.description,
                domainModel.img_url,domainModel.latitude,domainModel.longitude,domainModel.imgFocalpointX,
                domainModel.imgFocalpointY,domainModel.collection,domainModel.collectionPosition,
                domainModel.release,domainModel.stampText)
    }
}