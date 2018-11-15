package com.loc8r.seattleexplorer.auth.interfaces

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthInterface {
    fun signIn(email: String, password: String, onResult: (Boolean,String) -> Unit)

    fun signOut(onResult: () -> Unit)

    fun deleteUser(user: FirebaseUser)

    fun register(email: String, password: String, name: String, onResult: (Boolean, String) -> Unit)

    fun getUser(): FirebaseUser?
}