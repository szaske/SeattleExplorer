package com.loc8r.seattleexplorer.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.loc8r.seattleexplorer.auth.interfaces.AuthInterface
import timber.log.Timber
import javax.inject.Inject

class ExplorerAuthImpl @Inject constructor(
        private val authService: FirebaseAuth
): AuthInterface {

    /**
     * The authService class itself is tracking the user and if the user is authenticated.
     * As you can see below, no data is being passed, only success or failure and a possible
     * exception message
     */
    override fun signIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        authService.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful) {
                onResult(true, "success")
            } else {
                val error = if(task.exception != null){
                    task.exception!!.localizedMessage
                } else {
                    "Authentication Error"
                }
                onResult(false, error)
                Timber.w("signInWithEmail:failure ${task.exception}")
            }
        }
    }

    override fun register(email: String, password: String, name: String, onResult: (Boolean, String) -> Unit) {
        authService.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isComplete && task.isSuccessful) {
                authService.currentUser?.updateProfile(UserProfileChangeRequest
                        .Builder()
                        .setDisplayName(name)
                        .build())
                onResult(true, "")
            } else {
                val error = if(task.exception != null){
                    task.exception!!.localizedMessage
                } else {
                    "Registration Error"
                }
                onResult(false, error)
                Timber.w("Registration:failure ${task.exception}")
            }
        }
    }

    override fun getUser(): FirebaseUser? = authService.currentUser

    override fun deleteUser(user: FirebaseUser) {
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.d("User account deleted.")
            }
        }
    }

    override fun signOut(onResult: () -> Unit) {
        authService.signOut()
        onResult()
    }
}