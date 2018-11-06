package com.loc8r.seattleexplorer.auth.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthInterface {
    fun signIn(email: String, password: String, onResult: (Task<AuthResult>) -> Unit)

    fun signOut(onResult: () -> Unit)

    fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit)

    fun getUser(): FirebaseUser?
}