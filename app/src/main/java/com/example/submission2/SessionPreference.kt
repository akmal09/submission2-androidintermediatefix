package com.example.submission2

import android.content.Context
import com.example.submission2.model.LoginSession

internal class SessionPreference(context: Context) {
    companion object{
        private const val PREFS_NAME ="login_sess"
        private const val NAME ="name"
        private const val TOKEN ="token"
        private const val USERID ="user_id"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setSession(loginSession: LoginSession) {
        val prefEdit = preference.edit()
        prefEdit.putString(NAME, loginSession.name)
        prefEdit.putString(TOKEN, loginSession.token)
        prefEdit.putString(USERID, loginSession.userId)
        prefEdit.apply()
    }

    fun getSession(): LoginSession {
        val loginSession = LoginSession()
        loginSession.name = preference.getString(NAME, "")
        loginSession.token = preference.getString(TOKEN, "")
        loginSession.userId = preference.getString(USERID, "")


        return loginSession
    }

    fun deleteSession(){
        val prefEdit = preference.edit()
        prefEdit.clear().apply()
    }
}