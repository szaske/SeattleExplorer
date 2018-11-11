package com.loc8r.seattleexplorer.auth.models

data class UserSignIn(
        val email: String = "",
        val password: String = ""
) {

    fun isValid(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}