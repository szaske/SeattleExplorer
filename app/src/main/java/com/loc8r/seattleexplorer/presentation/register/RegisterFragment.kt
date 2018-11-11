package com.loc8r.seattleexplorer.presentation.register

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.R.id.*
import com.loc8r.seattleexplorer.auth.models.UserRegistration
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.register_fragment.*
import timber.log.Timber
import javax.inject.Inject

class RegisterFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

    lateinit var navController: NavController

    private var interactionListener: OnFragmentInteractionListener? = null


    companion object {
        fun newInstance() = RegisterFragment()
    }

    override fun onAttach(context: Context) {
        // Here's I'm kicking off the injection process for the Fragment
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            // this sets the listener to the activity
            interactionListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init navController
        navController = Navigation.findNavController(view)

        // Set click listeners
        register_btn_register?.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // assigning the viewModel according to the map in the viewModelFactory
        // sharedViewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(SharedViewModel::class.java)
        registerViewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(RegisterViewModel::class.java)
    }

    override fun onStart() {
        /**
         * This method is the right place to bring initialized, but inactive and isolated
         * “created” Activity to life.  Examples of stuff to add here:
         *
         * * Registration of View click listeners
         * * Subscription to observables (general observables, not necessarily Rx)
         * * Reflect the current state into UI (UI update)
         * * Functional flows
         * * Initialization of asynchronous functional flows
         * * Resources allocations
         *
         */
        super.onStart()
    }

    override fun onClick(view: View?) {
        view.let {
            when (it) {
                register_btn_register -> {
                    interactionListener?.hideKeyboard()
                    interactionListener?.setGreyOut(View.VISIBLE)
                    interactionListener?.setProgressBar(View.VISIBLE)
                    validateRegisterForm(register_et_email.text.toString(),
                            register_et_password.text.toString(),
                            register_et_name.text.toString())
                }
                else -> {
                    // TODO what needs to be here?
                    Timber.i("Nothing happened")
                }
            }
        }
    }

    private fun validateRegisterForm(email: String, password: String, name: String){
        // Validate the form
        registerViewModel.validateRegistrationRequest(email,password, name) {
            isValid,request,errorMessage ->
            if(isValid){
                registerWithEmail(request)
            } else {
                interactionListener?.onRegistrationFailure(errorMessage)
            }
        }
    }

    private fun registerWithEmail(userRegistration: UserRegistration) {
        registerViewModel.register(userRegistration.email, userRegistration.password, userRegistration.name) {isRegistered, errorMessage ->
            if(isRegistered){
                // user created in viewmodel

                // now login in user since it doesn't happen automatically
                registerViewModel.signInWithEmail(userRegistration.email,userRegistration.password) { OnSignInSuccess ->
                    if(OnSignInSuccess){
                        interactionListener?.onSignInUserSuccess()
                        navController
                                .navigate(R.id.action_registerFragment_to_homeFragment,
                                        null,
                                        NavOptions.Builder()
                                                .setPopUpTo(registerFragment,
                                                        true)
                                                        .build()
                                )
                    } else {
                        navController.navigate(action_registerFragment_to_loginFragment)
                    }
                }


            } else {
                // there was a problem
                interactionListener?.showSnackbar(errorMessage)
            }
            interactionListener?.setGreyOut(View.GONE)
            interactionListener?.setProgressBar(View.GONE)
        }
    }
}
