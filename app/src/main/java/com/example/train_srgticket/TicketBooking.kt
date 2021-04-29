package com.example.train_srgticket

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.ProgressBar
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import com.example.train_srgticket.databinding.ActivityTicketBookingBinding
import com.example.train_srgticket.util.SessionManager
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.lang.Math.random
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

private lateinit var binding: ActivityTicketBookingBinding

@Suppress("DEPRECATION")
class TicketBooking : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTicketBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ticketSubmit.setOnClickListener {
            val book = BookTicketAsyncTask(applicationContext)
            book.execute()
        }

}

    companion object {

        class BookTicketAsyncTask internal constructor(context: Context) : AsyncTask<String, String, String>() {
            lateinit var session: SessionManager
            lateinit var con: HttpURLConnection
            lateinit var resulta:String
            val builder = Uri.Builder()
            val today = Calendar.getInstance()
            private val cont: Context =context
            override fun onPreExecute() {
                super.onPreExecute()
                session = SessionManager(cont)

                var tktNumber: Int = (10001..90000).random()
                var user: HashMap<String, String> = session.getUserDetails()
                var customerId: String = user.get(SessionManager.KEY_PHONE)!!
                val source: String = binding.textEnterCurrentLocation.text.toString()
                val destination: String = binding.textEnterDestination.text.toString()
                val tdate = binding.tdate

                tdate.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)){
                    view,year,month,day ->
                    val month = month +1
                    val date = (year/month/day)
                    builder .appendQueryParameter("tdatetime", date.toString())
                }

                val progressBar= ProgressBar(cont)
                progressBar.isIndeterminate=true
                progressBar.visibility= View.VISIBLE
                builder .appendQueryParameter("ticket_number", tktNumber.toString())
                builder .appendQueryParameter("source", source)
                builder .appendQueryParameter("customer_id", customerId)
                builder .appendQueryParameter("destination", destination)
            }
            override fun doInBackground(vararg params: String?):  String? {
                try {

                    var query = builder.build().encodedQuery
                    val url: String = "http://sgrticket96.000webhostapp.com/sgr/addticket.php"
                    val obj = URL(url)
                    con = obj.openConnection() as HttpURLConnection
                    con.setRequestMethod("POST")
                    con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)")
                    con.setRequestProperty("Accept-Language", "UTF-8")
                    con.setDoOutput(true)
                    val outputStreamWriter = OutputStreamWriter(con.getOutputStream())
                    outputStreamWriter.write(query)
                    outputStreamWriter.flush()
                    Log.e("pass 1", "connection success ")
                } catch (e: Exception) {
                    Log.e("Fail 1", e.toString())
                }
                try {
                    resulta = con.inputStream.bufferedReader().readText()
                    Log.e("data", resulta)
                } catch (e: java.lang.Exception) {
                    Log.e("Fail 2", e.toString())
                }
                return resulta;
            }
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)

                var json_data = JSONObject(resulta)
                val code: Int = json_data.getInt("code")
                Log.e("data",code.toString())
                if (code == 1) {
                    val toMain = Intent(cont, MainPage::class.java)
                    toMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    cont.startActivity(toMain)

                }
            }
        }

    }
}


