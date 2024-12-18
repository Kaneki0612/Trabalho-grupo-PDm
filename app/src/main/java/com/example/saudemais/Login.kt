package com.example.saudemais

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tv = findViewById<TextView>(R.id.textView3)
        val emailL = findViewById<TextInputLayout>(R.id.email)
        val email = emailL.editText
        val passwordL = findViewById<TextInputLayout>(R.id.password)
        val password = passwordL.editText
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)

        button.setOnClickListener() {
            if(email?.text.toString().isEmpty() || password?.text.toString().isEmpty() ){
                emailL.error = "vazio"
                passwordL.error = "vazio"
            }else{
                val intent: Intent = Intent(this, MainActivity::class.java)
                login(email!!.text.toString(),password!!.text.toString(),intent)
            }
        }

        button2.setOnClickListener() {
            val intent: Intent = Intent(this, CriarConta::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String,intent: Intent) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val rsaEncryptionHelper = RSAEncryptionHelper(this@Login)
                val client = OkHttpClient()
                val a = rsaEncryptionHelper.encrypt(password)
                val json ="{\"nome\": \"$username\",\"pass\": \"$a\"}"
                //Log.d("tatata",json)
                val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
                val request = Request.Builder()
                    .addHeader("nome",username)
                    .addHeader("pass2",a)
                    .post(requestBody)
                    .url("http://nebula-env.com:8086/login")
                    .build()

                val response: Response = client.newCall(request).execute()
                val responseData = response.body?.string()

                if (response.isSuccessful && responseData != null) {
                    //Log.d("success", "Login successful: $responseData")
                    val jsonArray = JSONArray(responseData)
                    val nome = arrayListOf<String>()
                    for (i in 0 until 1) {
                        val jsonObject = jsonArray.getJSONObject(i)
                       // Log.d("taata",jsonObject.getString("password"))
                        nome.add(jsonObject.getString("id_nome"))
                        nome.add(jsonObject.getString("nome"))
                    }
                    //Log.d("das",nome.toString())
                    intent.putExtra("id",nome[0])
                    intent.putExtra("name",nome[1])
                    startActivity(intent)
                } else {
                    Log.d("error", "Login failed: ${response.message}")
                    runOnUiThread(){
                    Toast.makeText(this@Login,"Erro Login",Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("error", e.toString())
                runOnUiThread() {
                    Toast.makeText(this@Login, "Erro Login", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}