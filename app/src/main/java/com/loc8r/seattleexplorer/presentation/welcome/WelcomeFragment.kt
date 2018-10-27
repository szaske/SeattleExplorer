package com.loc8r.seattleexplorer.presentation.welcome

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
import com.loc8r.seattleexplorer.MainActivity
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.welcome_fragment.*
import javax.inject.Inject


class WelcomeFragment : Fragment() {

    lateinit var navController: NavController

    // Here I'm injecting the viewModelFactory and NOT the viewModel
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    override fun onAttach(context: Context) {
        // Here's I'm kicking off the injection process for the Fragment
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init navController
        navController = Navigation.findNavController(view)

        // This navigates according to the id's within the nav_graph, not a view in a layout
        homeButton?.setOnClickListener {
            (activity as MainActivity).makeHomeStart()
            val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.welcomeFragment, true)
                    .build()
            navController.navigate(R.id.action_welcomeFragment_to_homeFragment,null,navOptions)
        }

        // This navigates according to the id's within the nav_graph, not a view in a layout
        loginButton?.setOnClickListener {
            navController.navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // assigning the viewModel according to the map in the viewModelFactory
        sharedViewModel = ViewModelProviders.of(activity!!,viewModelFactory).get(SharedViewModel::class.java)

        // Check for user authentication
        if(sharedViewModel.isUserAuthenticated()) {

             (activity as MainActivity).makeHomeStart()
            val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.welcomeFragment, true)
                    .build()
            navController.navigate(R.id.action_welcomeFragment_to_homeFragment,null,navOptions)

        } else {
            navController.navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

    }

}
