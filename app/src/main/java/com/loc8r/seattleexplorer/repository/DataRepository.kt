package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import com.loc8r.seattleexplorer.repository.interfaces.RepoDataStoreBroker
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

open class DataRepository @Inject constructor(
        private val cacheBroker: CacheDataStoreBroker,
        private val remoteBroker: RemoteDataStoreBroker,
        private val mapper: PoiRepoMapper): DomainRepository{

    override fun getPois(): Single<List<PoiDomain>> {

        /** Because we're going to be getting pois data from our data store,
         *  we first need to get some information about the cacheBroker.  The ZIP method
         *  takes two observables, casts a function on them and returns a new
         *  observable.  In this case the BiFunction is taking our two
         *  boolean observables and turning them into a Pair Observable.
         **/

        val cacheStatus = Observable.zip(cacheBroker.arePoisCached().toObservable(),
                cacheBroker.isPoisCacheExpired().toObservable(),
                BiFunction<Boolean,Boolean, Pair<Boolean,Boolean>> {
                    areCached, isExpired -> Pair(areCached,isExpired)
                })

        /**
         * Then we determine which store to use and cast the getPois
         * method from the given store...which returns
         * an Observable list of Pois.  Then we flatMap that give us the ability to
         * save the Pois to the cacheBroker.  We cast andThen to convert the results back into
         * an observable. Finally we convert the List to a Domain_Poi model type using a map method.
         *
         **/
        val pois = cacheStatus.flatMap {
            getDataStore(it.first,it.second)
                    .getPois().toObservable()

        }
        .flatMap { pois ->
            // this saves the data to the cacheBroker each time
            cacheBroker.savePois(pois)
                .andThen(Observable.just(pois))}
                .map { eachList ->
                    eachList.map {
                        // converting each poi
                        mapper.mapToDomain(it)
                    }
                }


        // return a Single
        return Single.fromObservable(pois)

    }

    /**
     * Returns the proper data store depending on the status of the cacheBroker
     */
    open fun getDataStore(poisCached: Boolean,
                          cacheExpired: Boolean): RepoDataStoreBroker {
        return if (poisCached && !cacheExpired) {
            cacheBroker
        } else {
            remoteBroker
        }
    }

}