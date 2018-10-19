package com.loc8r.seattleexplorer.cache.poiCache

import com.loc8r.seattleexplorer.cache.models.PoiCache
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import javax.inject.Inject

open class PoiCacheMapper @Inject constructor(){
    open fun mapToRepo(cacheModel: PoiCache): PoiRepository {
        return PoiRepository(cacheModel.id,
                cacheModel.name,
                cacheModel.description,
                cacheModel.img_url,
                cacheModel.latitude,
                cacheModel.longitude,
                cacheModel.imgFocalpointX,
                cacheModel.imgFocalpointY,
                cacheModel.collection,
                cacheModel.collectionPosition,
                cacheModel.release,
                cacheModel.stampText)
    }

    open fun mapToCache(poiRepository: PoiRepository): PoiCache {
        return PoiCache(poiRepository.id,
                poiRepository.name,
                poiRepository.description,
                poiRepository.img_url,
                poiRepository.latitude,
                poiRepository.longitude,
                poiRepository.imgFocalpointX,
                poiRepository.imgFocalpointY,
                poiRepository.collection,
                poiRepository.collectionPosition,
                poiRepository.release,
                poiRepository.stampText)
    }
}