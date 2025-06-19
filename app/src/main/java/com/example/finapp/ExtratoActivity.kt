package com.example.finapp

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ExtratoActivity : AppCompatActivity() {
    val filename = "transacoes.txt"

    lateinit var radioGroup: RadioGroup
    lateinit var recyclerView: RecyclerView
    lateinit var transacoes = mutableListOf<Transacao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_extrato)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.reciclerViewTransacao)

        val file = File(filename)
        radioGroup = findViewById(R.id.radioGroup2)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            val selectedText = radioButton.text.toString()
            if (selectedText == "Total") {
                transacoes.clear()
                file.bufferedReader().useLines { transacoes.add(it.toString().toTransacao()) }
                recyclerView.adapter = TransacaoAdapter(transacoes)
            } else if (selectedText == "Crédito") {
                transacoes.clear()
                file.bufferedReader().useLines { if (it.toString().contains("1"){
                    transacoes.add(it.toString().toTransacao())
                    }
                }
                recyclerView.adapter = TransacaoAdapter(transacoes)
            } else {
                transacoes.clear()
                file.bufferedReader().useLines { if (it.toString().contains("0"){
                    transacoes.add(it.toString().toTransacao())
                    }
                }
                recyclerView.adapter = TransacaoAdapter(transacoes)
            }
        }

        val fileInputStream = openFileInput(filename)
        val text = fileInputStream.bufferedReader().use { it.readText() }
        fileInputStream.close()

    }

    fun voltar(view: View) {
        finish()
    }

}