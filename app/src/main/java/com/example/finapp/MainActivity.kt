package com.example.finapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun sairApp(view: View) {
        this.finishAffinity()
    }

    fun acessaExtrato(view: View){
        val intent = Intent(this, ExtratoActivity::class.java)
        startActivity(intent)
    }

    fun acessaCadastro(view: View){
        val intent = Intent(this, CadastroActicity::class.java)
        startActivity(intent)
    }
}