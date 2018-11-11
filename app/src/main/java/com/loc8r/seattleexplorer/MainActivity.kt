package com.loc8r.seattleexplorer


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.loc8r.seattleexplorer.R.anim.slide_in_left
import com.loc8r.seattleexplorer.R.id.homeFragment
import com.loc8r.seattleexplorer.R.id.loginFragment
import com.loc8r.seattleexplorer.di.ViewModelFactory
import com.loc8r.seattleexplorer.presentation.SharedViewModel
import com.loc8r.seattleexplorer.presentation.interfaces.OnFragmentInteractionListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.main_activity.*
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), OnFragmentInteractionListener{

    // Here I'm injecting the viewModelFactory and NOT the viewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var sharedViewModel: SharedViewModel

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

        setupActionBar()
    }

    // Which fragments should show an up arrow?
    private fun showUpArrow(id: Int): Boolean {
        return id != R.id.homeFragment &&
                id != R.id.loginFragment

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
        // the Login onComplete listener is automatically removed
    }

    /**
     * you should use the support library's Toolbar class to implement your activities' app bars.
     * Using the support library's toolbar helps ensure that your app will have consistent behavior
     * across the widest range of devices
     */
    private fun setupActionBar(){
        val toolbar = findViewById<Toolbar>(R.id.main_tbar_toolbar)
        setSupportActionBar(toolbar)
        Navigation.findNavController(this,R.id.main_fr_nav_host_fragment).addOnNavigatedListener { _, destination ->
            val showUpArrow = showUpArrow(destination.id)
            supportActionBar?.setDisplayHomeAsUpEnabled(showUpArrow)
            supportActionBar?.setHomeButtonEnabled(showUpArrow)
            supportActionBar?.title = destination.label
        }
    }


    // This enables the up arrow on the toolbar when navigating to a child fragment
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_fr_nav_host_fragment).navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        // show or hide logout menu item
        menu.findItem(R.id.menu_logout).isVisible = sharedViewModel.isUserAuthenticated()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                Timber.d("Logout item selected")
                signOutUser()
            }
            R.id.menu_settings -> {
                Timber.d("Settings item selected")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSignInUserSuccess(){
        invalidateOptionsMenu() // shows logout option
        setProgressBar(View.GONE)
        setGreyOut(View.GONE)
        showSnackbar("Welcome back, ${sharedViewModel.getUserName()}")
        Timber.i("User: ${sharedViewModel.getUserName()} logged in.")
    }

    override fun onRegistrationSuccess(){
        invalidateOptionsMenu() // shows logout option
        setProgressBar(View.GONE)
        setGreyOut(View.GONE)
        showSnackbar("Welcome ${sharedViewModel.getUserName()}")
        Timber.i("User: ${sharedViewModel.getUserName()} registered.")
    }

    override fun onSignInUserFailure(error: String){
        setProgressBar(View.GONE)
        setGreyOut(View.GONE)
        showSnackbar(error)
    }

    override fun onRegistrationFailure(error: String){
        setProgressBar(View.GONE)
        setGreyOut(View.GONE)
        showSnackbar(error)
    }

    private fun signOutUser(){
        sharedViewModel.signOut {
            invalidateOptionsMenu() // So login is not visible at sign in page

        // clearing the back stack on navigation
        findNavController(this,R.id.main_fr_nav_host_fragment)
                .navigate(loginFragment,
                        null,
                        NavOptions.Builder()
                                .setEnterAnim(slide_in_left)
                                .setPopUpTo(homeFragment,
                                        true)
                                .build()
                )
        }
    }

    override fun resetMenu() = invalidateOptionsMenu()

    override fun setProgressBar(viewState: Int){
            main_pb_login.visibility = viewState
    }

    override fun setGreyOut(viewState: Int){
        main_vw_greyOut.visibility = viewState
    }

    override fun hideKeyboard(){
        val view = this.currentFocus
        if (view != null) {
            val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showSnackbar(s: String) {
        val view = findViewById<View>(R.id.main_ll_container)
        val snack = Snackbar.make(view, s, Snackbar.LENGTH_LONG)
        val sbview = snack.view

        val lp = sbview.layoutParams as FrameLayout.LayoutParams
        lp.setMargins(lp.leftMargin+10,lp.topMargin,lp.bottomMargin+10,lp.bottomMargin+10)
        sbview.layoutParams = lp
        snack.show()
    }
}
