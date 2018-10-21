package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.presentation.models.CollectionPresentation
import com.loc8r.seattleexplorer.repository.models.CollectionRepository
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import javax.inject.Inject

open class RepositoryMapper @Inject constructor() {

    open fun mapPoiToDomain(poiRepository: PoiRepository): PoiDomain {
        return PoiDomain(poiRepository.id,poiRepository.name,poiRepository.description,
                poiRepository.img_url,poiRepository.latitude,poiRepository.longitude,
                poiRepository.imgFocalpointX,poiRepository.imgFocalpointY,
                poiRepository.collection,poiRepository.collectionPosition,
                poiRepository.release,poiRepository.stampText)
    }

    open fun mapColToDomain(colRepository: CollectionRepository): CollectionDomain {

        return CollectionDomain(colRepository.id,
                colRepository.name,
                colRepository.description,
                colRepository.date,
                colRepository.img_url,
                colRepository.color,
                colRepository.textColor)
    }
}