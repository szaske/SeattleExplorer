/**
 * This interface is used to insure that either a Cache data store or remote datastore can be used
 * within the Data Respository class
 */

package com.loc8r.seattleexplorer.repository.interfaces

import com.loc8r.seattleexplorer.repository.models.PoiRepository
import io.reactivex.Completable
import io.reactivex.Single

interface RepoDataStoreBroker {

    fun getPois(): Single<List<PoiRepository>>

    fun savePois(pois: List<PoiRepository>): Completable

}