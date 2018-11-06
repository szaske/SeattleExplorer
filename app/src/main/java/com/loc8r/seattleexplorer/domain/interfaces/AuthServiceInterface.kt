package com.loc8r.seattleexplorer.domain.interfaces


interface AuthServiceInterface {

    fun login(email: String, password: String, onResult: (Boolean) -> Unit)

    fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit)

    fun getUserId(): String

    fun getUserName(): String

    fun logOut(onResult: () -> Unit)

}