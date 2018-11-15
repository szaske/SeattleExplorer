package com.loc8r.seattleexplorer.presentation.register

import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.app.AppCompatActivity
import com.loc8r.seattleexplorer.BaseTestRobot
import com.loc8r.seattleexplorer.MainActivity
import com.loc8r.seattleexplorer.R
import com.loc8r.seattleexplorer.R.id.*

// trick to create a robot builder class
fun register(func: RegisterRobot.() -> Unit) = RegisterRobot().apply { func() }

class RegisterRobot: BaseTestRobot() {

    fun setName(name: String) = fillEditText(register_et_name, name)

    fun setEmail(email: String) = fillEditText(register_et_email, email)

    fun setPassword(pass: String) = fillEditText(register_et_password, pass)

    fun clickRegister() = clickButton(register_btn_register)

    fun startInRegister(activity: AppCompatActivity) {
        clickButton(login_btn_create_account)
        matchToolbarTitle(activity.getString(R.string.register_fragment_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun deleteUser(activity: MainActivity){
        activity.deleteCurrentUser()
    }
}