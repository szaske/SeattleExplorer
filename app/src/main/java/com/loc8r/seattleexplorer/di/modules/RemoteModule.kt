package com.loc8r.seattleexplorer.di.modules

import com.google.firebase.firestore.FirebaseFirestore
import com.loc8r.seattleexplorer.remote.ExplorerRemoteImpl
import com.loc8r.seattleexplorer.repository.interfaces.ExplorerRemote
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun firebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    }

    @Binds
    abstract fun bindRemote(explorerRemote: ExplorerRemoteImpl): ExplorerRemote
}