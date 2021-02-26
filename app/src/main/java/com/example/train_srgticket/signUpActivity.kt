package com.example.train_srgticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.train_srgticket.databinding.ActivitySignUpBinding

private lateinit var binding: ActivitySignUpBinding

class signUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        var view = binding.root

        setContentView(view)

        binding.buttonSignUp.setOnClickListener {
            val toMainScreen = Intent(applicationContext, MainScreenActivity::class.java)
            startActivity(toMainScreen)
        }

        binding.textLogin.setOnClickListener {
            val toLogin = Intent(applicationContext, LoginActivity::class.java)
            startActivity(toLogin)
        }

    }

    fun openLogin(){

    }
}