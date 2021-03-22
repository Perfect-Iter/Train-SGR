@file:Suppress("DEPRECATION")

package com.example.train_srgticket

import com.example.train_srgticket.MainScreenActivity
import com.example.train_srgticket.signUpActivity
import org.json.JSONArray

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.example.train_srgticket.databinding.ActivityLoginBinding
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL


private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        var view = binding.root

        setContentView(view)

        binding.buttonLogIn.setOnClickListener {
            val login = MyAsyncTask(applicationContext)
           login.execute()

        }
        binding.textSignin.setOnClickListener{
            val toSignIn = Intent(applicationContext, signUpActivity::class.java)
            startActivity(toSignIn)
        }
    }

    companion object {
        class MyAsyncTask internal constructor(context: Context) : AsyncTask<String, String, String>() {
            lateinit var con:HttpURLConnection
            lateinit var resulta:String
            val builder = Uri.Builder()
            private val cont:Context=context
            override fun onPreExecute() {
                super.onPreExecute()

                val progressBar= ProgressBar(cont)
                progressBar.isIndeterminate=true
                progressBar.visibility= View.VISIBLE

                val phone: String = binding.inputPhone.text.toString()
                val pass: String = binding.inputPassword.text.toString()

                builder .appendQueryParameter("phone", phone)
                builder .appendQueryParameter("password", pass)
                builder .appendQueryParameter("key", "oooo")
            }

            override fun doInBackground(vararg params: String?):  String? {
                try {

                    var query = builder.build().encodedQuery
                    val url: String = "https://sgrticket96.000webhostapp.com/sgr/read.php"
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
                return null;
            }
           override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                var json_data = JSONObject(resulta)
                val code: Int = json_data.getInt("code")
                Log.e("data",code.toString())
                if (code == 1) {
                    //val com: JSONArray = json_data.getJSONArray("userdetails")
                    //val comObject = com[0] as JSONObject
                    //Log.e("data",""+comObject.optString("fname"))
                    val toMain = Intent(cont, MainScreenActivity::class.java)
                    toMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    cont.run {
                        startActivity(toMain)
                    }
                }
            }


        }

    }
}