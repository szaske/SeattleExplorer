package com.loc8r.seattleexplorer.presentation.login

import com.loc8r.seattleexplorer.BaseTestRobot
import com.loc8r.seattleexplorer.R.id.*

// trick to create a robot builder class
fun login(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }

class LoginRobot: BaseTestRobot() {
    fun setEmail(email: String) = fillEditText(login_et_email, email);

    fun setPassword(pass: String) = fillEditText(login_et_password, pass)

    fun clickLogin() = clickButton(login_btn_login)

    fun logOut() {
        openMenu()
        selectMenuItem("logout")
    }

    fun matchErrorText(err: String) = matchText(textView(android.R.id.message), err)
}