package com.loc8r.seattleexplorer.presentation.utils

import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.CollectionPresentation
import com.loc8r.seattleexplorer.presentation.models.PoiPresentation
import javax.inject.Inject

open class PresentationMapper @Inject constructor() {

    open fun mapPoiToPresentation(domainModel: PoiDomain): PoiPresentation {

        return PoiPresentation(domainModel.id,domainModel.name,domainModel.description,
                domainModel.img_url,domainModel.latitude,domainModel.longitude,domainModel.imgFocalpointX,
                domainModel.imgFocalpointY,domainModel.collection,domainModel.collectionPosition,
                domainModel.release,domainModel.stampText)
    }

    open fun mapColToPresentation(domainModel: CollectionDomain): CollectionPresentation{

        return CollectionPresentation(domainModel.id,
                domainModel.name,
                domainModel.description,
                domainModel.date,
                domainModel.img_url,
                domainModel.color,
                domainModel.textColor)
    }

}