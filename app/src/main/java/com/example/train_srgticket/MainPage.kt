package com.example.train_srgticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.train_srgticket.databinding.ActivityMainPageBinding
import com.example.train_srgticket.util.SessionManager

private lateinit var binding: ActivityMainPageBinding

class MainPage : AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)
        session.checkLogin()
        var user: HashMap<String, String> = session.getUserDetails()

        //var phone: String = user.get(SessionManager.KEY_PHONE)!!
        //var password: String = user.get(SessionManager.KEY_PASSWORD)!!


        binding.buttonMyTicket.setOnClickListener {
            val toAllTickets = Intent(applicationContext, AllTickets::class.java)
            startActivity(toAllTickets)
        }
        binding.buttonBookTicket.setOnClickListener {
            val toBookTickets = Intent(applicationContext, TicketBooking::class.java)
            startActivity(toBookTickets)
        }
        binding.buttonLogout.setOnClickListener {
            session.logoutUser()
        }
    }
}