package com.loc8r.seattleexplorer.presentation.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : Fragment() {

    // Here I'm injecting the viewModelFactory and NOT the viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onAttach(context: Context) {
        // Here's I'm kicking off the injection process for the Fragment
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        // assigning the viewModel according to the map in the viewModelFactory
        sharedViewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(SharedViewModel::class.java)

    }

}
