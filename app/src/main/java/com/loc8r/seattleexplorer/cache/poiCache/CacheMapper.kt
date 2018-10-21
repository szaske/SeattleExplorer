package com.loc8r.seattleexplorer.cache.poiCache

import com.loc8r.seattleexplorer.cache.models.CollectionCache
import com.loc8r.seattleexplorer.cache.models.PoiCache
import com.loc8r.seattleexplorer.repository.models.CollectionRepository
import com.loc8r.seattleexplorer.repository.models.PoiRepository
import javax.inject.Inject

open class CacheMapper @Inject constructor(){
    open fun mapPoiToRepo(poiCacheModel: PoiCache): PoiRepository {
        return PoiRepository(poiCacheModel.id,
                poiCacheModel.name,
                poiCacheModel.description,
                poiCacheModel.img_url,
                poiCacheModel.latitude,
                poiCacheModel.longitude,
                poiCacheModel.imgFocalpointX,
                poiCacheModel.imgFocalpointY,
                poiCacheModel.collection,
                poiCacheModel.collectionPosition,
                poiCacheModel.release,
                poiCacheModel.stampText)
    }

    open fun mapPoiToCache(poiRepository: PoiRepository): PoiCache {
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

    open fun mapColToRepo(colCacheModel: CollectionCache): CollectionRepository {
        return CollectionRepository(colCacheModel.id,
                colCacheModel.name,
                colCacheModel.description,
                colCacheModel.date,
                colCacheModel.img_url,
                colCacheModel.color,
                colCacheModel.textColor)
    }

    open fun mapColToCache(colRepository: CollectionRepository): CollectionCache {
        return CollectionCache(colRepository.id,
                colRepository.name,
                colRepository.description,
                colRepository.date,
                colRepository.img_url,
                colRepository.color,
                colRepository.textColor)
    }
}