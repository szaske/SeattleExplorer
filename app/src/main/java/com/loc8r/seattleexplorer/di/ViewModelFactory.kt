/**
 * This is a fancy replacement of the current ViewModel Factory.  We need to replace the default as
 * the default only works with empty constructors.  This code helps us map our viewModels to their
 * Dagger providers which will instantiate the multiple dependencies passed into their constuctor.
 *
 * for more details see: https://dev.to/brightdevs/injectable-android-viewModels--1d5l
 *
 */

package com.loc8r.seattleexplorer.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
open class ViewModelFactory @Inject constructor(
        providerMap: Map<Class<out ViewModel>,@JvmSuppressWildcards Provider<ViewModel>>)
    : ViewModelProvider.Factory {

    // A local copy of the passed in Map.
    private val vmProviderMap: Map<Class<out ViewModel>, Provider<ViewModel>> = providerMap

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass a ViewModel class whose instance is requested
     * @param <T>        The ViewModel requested. T for Type.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // Look in the Map for the provider according to the modelClass
        var vmProvider: Provider<out ViewModel>? = vmProviderMap[modelClass]

        // Checks to see if the ViewModel is a superclass of something in the map
        // and assumes that provider
        if(vmProvider==null){
            for((key,value) in vmProviderMap){
                if(modelClass.isAssignableFrom(key)){
                    vmProvider = value
                }
            }
        }
        // If it's still not in the map I've done something wrong
        if(vmProvider==null){
            throw IllegalStateException("Unknown model class: $modelClass")
        }

        // Provider is in the map, so I'll call the providers get method.
        try {
            return vmProvider.get() as T
        } catch (e: Exception){
            throw RuntimeException(e)
        }
    }

}