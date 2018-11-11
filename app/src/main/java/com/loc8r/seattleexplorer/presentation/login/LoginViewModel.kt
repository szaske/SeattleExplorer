/**
 * ViewModels is the class that tie the model and the view together. It provides actions (that can
 * be called by the view) and provides simple states that the view can bind to. It also often, but
 * not always provides translation/transformation logic to prepare model data for presentation to
 * the user via the view.
 *
 * The viewmodel depends on the model, but not the view. That way the viewmodel can be tested in
 * isolation.  ViewModel's should not have references to any views.  Instead it should just affect
 * it's own variables, which then become the state of the view.
 *
 * So what goes in the Viewmodel?
 *
 * 1. Validation logic
 * 2. requests for "model" data
 * 3. conversion of model data for presentation purposes *
 *
 */

package com.loc8r.seattleexplorer.presentation.login

import android.arch.lifecycle.ViewModel
import com.loc8r.seattleexplorer.auth.interfaces.AuthInterface
import com.loc8r.seattleexplorer.auth.models.UserSignIn
import javax.inject.Inject


class LoginViewModel @Inject constructor(
        private val authService: AuthInterface
        ): ViewModel() {


    fun isUserAuthenticated(): Boolean {
        return authService.getUser() != null
    }

    fun getUserEmail(): String{
        return authService.getUser()?.email ?: ""
    }

    fun getUserName(): String{
        return authService.getUser()?.displayName ?: ""
    }

    fun validateSignInRequest(email: String, password: String , onResult: (Boolean, UserSignIn, String) -> Unit){

        val request = UserSignIn(email,password)

        if(request.isValid()){
            onResult(true, request, "")
        } else {
            // not a valid request show an error message
            onResult(false, request, "Invalid login Request")
        }
    }

    fun signInWithEmail(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        authService.signIn(email,password) {isSuccessful, serviceMessage ->
            if(isSuccessful){
                // user signed in
                onResult(true, "")
            } else {
                // user not signed in
                onResult(false, serviceMessage)
            }
        }
    }

}