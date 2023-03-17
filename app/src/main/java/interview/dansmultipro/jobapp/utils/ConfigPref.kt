package interview.dansmultipro.jobapp.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import interview.dansmultipro.core.constant.ModuleConst.IS_LOGGED_IN
import interview.dansmultipro.core.constant.ModuleConst.USER_DATA
import interview.dansmultipro.core.model.User
import javax.inject.Inject

class ConfigPref @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {
    fun setStatusLogin(boolean: Boolean){
        editor.putBoolean(IS_LOGGED_IN,boolean).apply()
    }

    fun getStatusLogin() : Boolean{
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    fun saveUser(user: User?) {
        val gson = Gson().toJson(user).toString()
        editor.putString(USER_DATA,gson).apply()
    }

    fun getUser(): User? {
        val stringData = sharedPreferences.getString(USER_DATA, null)
        return Gson().fromJson(stringData, User::class.java)
    }

}