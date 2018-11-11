/**
 * This class tells Dagger2 what Fragments will provide an injector
 */

package com.loc8r.seattleexplorer.di.modules

import com.loc8r.seattleexplorer.presentation.collectionsList.CollectionsListFragment
import com.loc8r.seattleexplorer.presentation.home.HomeFragment
import com.loc8r.seattleexplorer.presentation.login.LoginFragment
import com.loc8r.seattleexplorer.presentation.poiList.PoiListFragment
import com.loc8r.seattleexplorer.presentation.register.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesPoiListFragment(): PoiListFragment

    @ContributesAndroidInjector
    abstract fun contributesHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributesColListFragment(): CollectionsListFragment

    @ContributesAndroidInjector
    abstract fun contributesLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributesRegisterFragment(): RegisterFragment

}