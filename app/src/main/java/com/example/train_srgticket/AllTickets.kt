package com.example.train_srgticket

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.train_srgticket.addapters.AllTicketsAdaptors
import com.example.train_srgticket.databinding.ActivityAllTicketsBinding
import com.example.train_srgticket.models.Tickets
import com.example.train_srgticket.util.SessionManager
import org.json.JSONArray
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

private lateinit var binding: ActivityAllTicketsBinding
val mTicketDetails = ArrayList<Tickets>()
private lateinit var adapter: AllTicketsAdaptors

class AllTickets : AppCompatActivity()  {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAllTicketsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(applicationContext)

        val mRecyclerView: RecyclerView = binding.allTheTickets
        adapter = AllTicketsAdaptors(mTicketDetails)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        MyAsyncTask(applicationContext).execute()

    }
    class MyAsyncTask internal constructor(context: Context) : AsyncTask<String, String, String>() {
        lateinit var con: HttpURLConnection
        lateinit var resulta: String
        val builder = Uri.Builder()
        lateinit var session: SessionManager
        val mRecyclerView: RecyclerView = binding.allTheTickets

        private val cont: Context = context
        override fun onPreExecute() {
            super.onPreExecute()

            progressBar.isIndeterminate = true
            progressBar.visibility = View.VISIBLE

            session = SessionManager(cont)
            //var user: HashMap<String, String> = session.getUserDetails()
            //var phone: String = user.get(SessionManager.KEY_PHONE)!!

            //builder .appendQueryParameter("phone", phone)

        }

        override fun doInBackground(vararg params: String?): String? {
            try {

                var query = builder.build().encodedQuery
                val url: String = "https://sgrticket96.000webhostapp.com/sgr/alltickets.php"
                val obj = URL(url)
                con = obj.openConnection() as HttpURLConnection
                con.setRequestMethod("GET")
                con.setRequestProperty(
                    "User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"
                )
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
            return "";
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE
            var json_data = JSONArray(resulta)

            for (i in 0 until json_data.length()) {
                val jsonObject = json_data.getJSONObject(i)
                val Uname = jsonObject.optString("name")
                val tktNumber = jsonObject.optString("ticket_number")
                val source = jsonObject.optString("source")
                val destination = jsonObject.optString("destination")
                mTicketDetails.add(Tickets(Uname, tktNumber, source, destination))
            }
            mRecyclerView.adapter = AllTicketsAdaptors(mTicketDetails)
            adapter.notifyDataSetChanged()
            Log.e("data", json_data.toString())
        }

    }



}