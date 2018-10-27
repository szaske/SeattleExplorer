package com.loc8r.seattleexplorer.repository

import com.loc8r.seattleexplorer.domain.interfaces.DomainRepository
import com.loc8r.seattleexplorer.domain.models.CollectionDomain
import com.loc8r.seattleexplorer.domain.models.PoiDomain
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class DataRepository @Inject constructor(
        private val cacheBroker: CacheDataStoreBroker,
        private val remoteBroker: RemoteDataStoreBroker,
        private val mapper: RepositoryMapper): DomainRepository{

    override fun getPois(): Observable<List<PoiDomain>> {

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

            // val datastore = getDataStore(it.first,it.second)

            if (it.first && !it.second) {
                // Cache is fresh and the appropriate data source
                // Cache returns a Maybe and is here converted to an Observable
                cacheBroker.getPois().toObservable()
            } else {
                // Cache is old, let's get data from the remote source
                // RemoteBroker returns a Single, so we convert to Observable
                remoteBroker.getPois().toObservable()
                        .flatMap { pois ->
                            // this saves the data to the cacheBroker each time
                            cacheBroker.savePois(pois)
                                .andThen(Observable.just(pois))}
            }
        }
        .map { eachList ->
            eachList.map {
                // converting each POI to Domain model
                mapper.mapPoiToDomain(it)
            }
        }
        return pois
    }

    override fun getCollections(): Observable<List<CollectionDomain>> {

        /** Because we're going to be getting pois data from our data store,
         *  we first need to get some information about the cacheBroker.  The ZIP method
         *  takes two observables, casts a function on them and returns a new
         *  observable.  In this case the BiFunction is taking our two
         *  boolean observables and turning them into a Pair Observable.
         **/

        val cacheStatus = Observable.zip(cacheBroker.areCollectionsCached().toObservable(),
                cacheBroker.isCollectionsCacheExpired().toObservable(),
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
        val collections = cacheStatus.flatMap {

            // val datastore = getDataStore(it.first,it.second)

            if (it.first && !it.second) {
                // Cache is fresh and the appropriate data source
                // Cache returns a Maybe and is here converted to an Observable
                cacheBroker.getCollections().toObservable()
            } else {
                // Cache is old, let's get data from the remote source
                // RemoteBroker returns a Single, so we convert to Observable
                remoteBroker.getCollections().toObservable()
                        .flatMap { cols ->
                            // this saves the data to the cacheBroker each time
                            cacheBroker.saveCollections(collections = cols)
                                    .andThen(Observable.just(cols))}
            }
        }
                .map { eachList ->
                    eachList.map {
                        // converting each POI to Domain model
                        mapper.mapColToDomain(it)
                    }
                }
        return collections
    }

    override fun refreshAll(): Completable {
        return getCollections().ignoreElements()
                    .andThen(getPois().ignoreElements()
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.io()))
    }
}