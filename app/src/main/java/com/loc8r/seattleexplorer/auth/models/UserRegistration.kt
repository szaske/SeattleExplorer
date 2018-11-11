package com.loc8r.seattleexplorer.auth.models

data class UserRegistration(
        val email: String = "",
        val password: String = "",
        val name: String = ""
) {

    fun isValid(): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()
    }
}