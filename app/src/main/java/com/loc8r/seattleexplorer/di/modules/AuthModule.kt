package com.loc8r.seattleexplorer.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.loc8r.seattleexplorer.auth.ExplorerAuthImpl
import com.loc8r.seattleexplorer.auth.interfaces.AuthInterface
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AuthModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    }

    @Binds
    abstract fun bindAuthService(authService: ExplorerAuthImpl): AuthInterface
}