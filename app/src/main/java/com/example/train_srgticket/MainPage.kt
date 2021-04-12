package com.example.train_srgticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.train_srgticket.databinding.ActivityMainPageBinding

private lateinit var binding: ActivityMainPageBinding
class MainPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMyTicket.setOnClickListener {
            val toAllTickets = Intent(applicationContext, AllTickets::class.java)
            startActivity(toAllTickets)
        }
        binding.buttonBookTicket.setOnClickListener {
            val toBookTickets = Intent(applicationContext, TicketBooking::class.java)
            startActivity(toBookTickets)
        }
    }
}