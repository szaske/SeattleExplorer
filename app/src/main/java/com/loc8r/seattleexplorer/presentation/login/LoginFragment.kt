package com.loc8r.seattleexplorer.presentation.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.login_fragment.*
import timber.log.Timber
import javax.inject.Inject

class LoginFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    lateinit var navController: NavController

    private var interactionListener: OnFragmentInteractionListener? = null


    companion object {
        fun newInstance() = LoginFragment()
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
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init navController
        navController = Navigation.findNavController(view)

        // Set click listeners
        login_btn_login?.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // assigning the viewModel according to the map in the viewModelFactory
        sharedViewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(SharedViewModel::class.java)
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
                login_btn_login -> {
                    interactionListener?.signInWithEmail(login_et_email.text.toString(), login_et_password.text.toString())
                }
                else -> {
                    // TODO what needs to be here?
                    Timber.i("Nothing happened")
                }
            }
        }
    }
}
