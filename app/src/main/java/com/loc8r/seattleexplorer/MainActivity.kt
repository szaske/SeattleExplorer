package com.loc8r.seattleexplorer

import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {

    // Here I'm injecting the viewModelFactory and NOT the viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var graph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * What goes in Oncreate? is this logic related to initialization of this object? If the
         * answer is negative, I find another home for it.
         */
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // assigning the viewModel according to the map in the viewModelFactory
        sharedViewModel = ViewModelProviders.of(this,viewModelFactory).get(SharedViewModel::class.java)

        //refresh all data by calling getAllCollections, getAllPois
        sharedViewModel.refreshLocalCacheData()

        // Setup the toolbar
        setupActionBar()

        // Configure the navigation
        setupNavigation()

    }

    fun makeHomeStart(){
        // setup navigation
        graph.startDestination = R.id.homeFragment
    }

    private fun setupNavigation() {
        val navHost = nav_host_fragment as NavHostFragment
        graph = navHost.navController
                .navInflater.inflate(R.navigation.nav_graph)
        graph.startDestination = R.id.welcomeFragment

        // This seems to be a magical command. Not sure why it's needed :(
        navHost.navController.graph = graph

        NavigationUI.setupActionBarWithNavController(this, navHost.navController)
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

    override fun onStop() {
        /**
         * In this method you will unregister all observers and listeners and release all resources
         * that were allocated in onStart(). After onStop() method completes Activity transitions
         * from “started” state back to “created” state.
         *
         */
        super.onStop()

    }

    /**
     * you should use the support library's Toolbar class to implement your activities' app bars.
     * Using the support library's toolbar helps ensure that your app will have consistent behavior
     * across the widest range of devices
     */
    fun setupActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


    // This enables the up arrow on the toolbar when navigating to a child fragment
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_host_fragment).navigateUp()
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
