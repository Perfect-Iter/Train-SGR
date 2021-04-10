package com.example.train_srgticket.addapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.train_srgticket.R
import com.example.train_srgticket.models.Tickets

class AllTicketsAdaptors(val tickets: ArrayList<Tickets>) : RecyclerView.Adapter<AllTicketsAdaptors.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtPassengerName: TextView = view.findViewById(R.id.txtUserName)
        val txtSourceFrom: TextView = view.findViewById(R.id.txtSource)
        val txtDestinationTo: TextView = view.findViewById(R.id.txtDestination)
        val txtTkTNumber: TextView = view.findViewById(R.id.txtTicketNumber)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ticket_row, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = tickets[position]
        holder.txtTkTNumber.text = currentItem.ticket_number
        holder.txtPassengerName.text = currentItem.Uname
        holder.txtSourceFrom.text = currentItem.source
        holder.txtDestinationTo.text = currentItem.destination

    }

    override fun getItemCount() = tickets.size
}