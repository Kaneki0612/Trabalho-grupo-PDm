package com.example.saudemais

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailL = findViewById<TextInputLayout>(R.id.email)
        val email = emailL.editText
        val passwordL = findViewById<TextInputLayout>(R.id.password)
        val password = passwordL.editText
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)

    /*    if (email?.text.toString().isEmpty() || password?.text.toString().isEmpty()){
            showEmptyAlert() //substituir pois quando abre pela primeira vez aparece logo(maybe Toast)
        }*/
        button.setOnClickListener() {
            if(email?.text.toString().isEmpty() || password?.text.toString().isEmpty() ){
                emailL.error = "vazio"
                passwordL.error = "vazio"
            }else{
                if (true/*checkEmail(email?.text.toString()) && checkPassword(password?.text.toString())*/) {
                    val intent: Intent = Intent(this, Sintomas::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"cria conta",Toast.LENGTH_SHORT).show()
                }
            }
        }

        button2.setOnClickListener() {
            val intent: Intent = Intent(this, CriarConta::class.java)
            startActivity(intent)
        }
    }
    private fun checkPassword(pass: String): Boolean {
        TODO("Not yet implemented")
    }

    private fun checkEmail (email:String): Boolean {
        TODO("Not yet implemented")
    }

    private fun showEmptyAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Campos vazios")
        builder.setMessage("Por favor, preencha todos os campos.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val AlertDialog = builder.create()
        AlertDialog.show()
    }

}