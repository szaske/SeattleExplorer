package com.loc8r.seattleexplorer.presentation.register

import android.arch.lifecycle.ViewModel
import com.loc8r.seattleexplorer.auth.interfaces.AuthInterface
import com.loc8r.seattleexplorer.auth.models.UserRegistration
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
        private val authService: AuthInterface
): ViewModel(){

    fun validateRegistrationRequest(email: String, password: String, name: String, onResult: (Boolean, UserRegistration, String) -> Unit){

        val request = UserRegistration(email,password, name)

        if(request.isValid()){
            onResult(true, request, "")
        } else {
            // not a valid request show an error message
            onResult(false, request, "Invalid registration Request")
        }
    }

    fun register(email: String, password: String, name: String, onResult: (Boolean, String) -> Unit) {
        return authService.register(email, password, name) {isSuccessfullyRegistered , errorMessage->
            if(isSuccessfullyRegistered){
                // TODO create a new user in firestore...here in the viewmodel

                onResult(true, "")
            } else {
                // TODO any other viewmodel work?
                onResult(false, errorMessage)
            }
        }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
        return authService.signIn(email,password) {isSuccessful ,_->
            if(isSuccessful){
                // user signed in
                onResult(true)
            } else {
                // user not signed in
                onResult(false)
            }
        }
    }

}