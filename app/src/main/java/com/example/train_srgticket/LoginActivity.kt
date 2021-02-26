package com.example.train_srgticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.train_srgticket.databinding.ActivityLoginBinding


private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        var view = binding.root

        setContentView(view)

        binding.buttonLogIn.setOnClickListener {
            val toMain = Intent(applicationContext, MainScreenActivity::class.java)

            val user = binding.inputUser.text.toString()
            val password = binding.inputPassword.text.toString()

            if(user !="" && password!=""){
                if(user == "12345" && password == "admin"){
                    startActivity(toMain)
                } else{
                    Toast.makeText(applicationContext, "Invalid credentials", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(applicationContext, "Please Enter credentials", Toast.LENGTH_SHORT).show()
            }

        }
        binding.textSignin.setOnClickListener{
            val toSignIn = Intent(applicationContext, signUpActivity::class.java)
            startActivity(toSignIn)
        }
    }
}