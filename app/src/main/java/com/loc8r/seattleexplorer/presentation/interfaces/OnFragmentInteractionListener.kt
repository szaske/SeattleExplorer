/**
 * This interface must be implemented by activities that contain this
 * fragment to allow an interaction in this fragment to be communicated
 * to the activity and potentially other fragments contained in that
 * activity.
 *
 *
 * See the Android Training lesson [Communicating with Other Fragments]
 * (http://developer.android.com/training/basics/fragments/communicating.html)
 * for more information.
 */

package com.loc8r.seattleexplorer.presentation.interfaces

interface OnFragmentInteractionListener {
    // fun signInWithEmail(email: String, password: String)

    fun setProgressBar(viewState: Int)

    fun setGreyOut(viewState: Int)

    fun hideKeyboard()

    fun showSnackbar(s: String)

    fun resetMenu()

    fun onSignInUserSuccess()

    fun onSignInUserFailure(error: String)

    fun onRegistrationSuccess()

    fun onRegistrationFailure(error: String)

}