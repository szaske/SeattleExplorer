package com.loc8r.seattleexplorer.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.loc8r.seattleexplorer.auth.interfaces.AuthInterface
import javax.inject.Inject

class ExplorerAuthImpl @Inject constructor(
        private val authService: FirebaseAuth
): AuthInterface {

    override fun signIn(email: String, password: String, onResult: (Task<AuthResult>) -> Unit) {
        authService.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it)
        }
    }

    override fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUser(): FirebaseUser? = authService.currentUser

    override fun signOut(onResult: () -> Unit) {
        authService.signOut()
        onResult()
    }
}