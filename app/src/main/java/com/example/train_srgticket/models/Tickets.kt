package com.example.train_srgticket.models

class Tickets {
    var Uname: String = ""
    var source: String=""
    var destination: String=""
    var ticket_number:String=""

    constructor(Uname: String, source:String,ticket_number:String, destination:String){
        this.Uname = Uname
        this.source = source
        this.destination = destination
        this.ticket_number = ticket_number
    }
}