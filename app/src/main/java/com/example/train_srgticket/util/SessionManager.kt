package com.example.train_srgticket.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.train_srgticket.LoginActivity
import com.example.train_srgticket.MainPage


class SessionManager{

    lateinit var pref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var con: Context
    var PRIVATE_MODE: Int = 0

    constructor(con: Context){
        this.con = con
        pref = con.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object{
        val PREF_NAME: String = "Tickets"
        val IS_LOGIN: String = "isLoggedIn"
        val KEY_PHONE:String = "phone"
        val KEY_PASSWORD: String="password"
    }

    fun createLoginSession(phone: String, password: String){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString(KEY_PHONE,phone)
        editor.putString(KEY_PASSWORD, password)
        editor.commit()
    }
    fun checkLogin(){
        if(!this.isLoggedIn()){
            var i: Intent = Intent(con, MainPage::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            con.startActivity(i)
        }
    }
    fun getUserDetails(): HashMap<String, String>{
        var user: Map<String, String> = HashMap<String,String>()
        (user as HashMap).put(KEY_PHONE, pref.getString(KEY_PHONE, null).toString())
        (user as HashMap).put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null).toString())
        return user
    }

    fun logoutUser(){
        editor.clear()
        editor.commit()

        var i: Intent = Intent(con, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        con.startActivity(i)
    }

    fun isLoggedIn(): Boolean{
        return pref.getBoolean((IS_LOGIN), false)
    }

}