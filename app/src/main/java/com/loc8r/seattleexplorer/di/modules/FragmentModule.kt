/**
 * This class tells Dagger2 what Fragments will provide an injector
 */

package com.loc8r.seattleexplorer.di.modules

import com.loc8r.seattleexplorer.presentation.poiList.PoiListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesPoiListFragment(): PoiListFragment
}