package com.example.train_srgticket

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class AllTickets : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tickets)
        MyAsyncTask(applicationContext).execute()
    }
    class MyAsyncTask internal constructor(context: Context) : AsyncTask<String, String, String>() {
        lateinit var con: HttpURLConnection
        lateinit var resulta:String
        val builder = Uri.Builder()
        private val cont: Context =context
        override fun onPreExecute() {
            super.onPreExecute()

            progressBar.isIndeterminate=true
            progressBar.visibility= View.VISIBLE

        }

        override fun doInBackground(vararg params: String?):  String? {
            try {

                var query = builder.build().encodedQuery
                val url: String = "https://sgrticket96.000webhostapp.com/sgr/alltickets.php"
                val obj = URL(url)
                con = obj.openConnection() as HttpURLConnection
                con.setRequestMethod("GET")
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
            return "";
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.visibility = View.GONE
            var json_data = JSONArray(resulta)

            Log.e("data", json_data.toString())
        }

    }
}