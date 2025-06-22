package com.example.finapp

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
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
    var transacoes = mutableListOf<Transacao>()
    lateinit var saldo: TextView

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
        saldo = findViewById(R.id.textSaldo)
        saldo.text = "R$ 0,00"

        val file = File(filename)
        radioGroup = findViewById(R.id.radioGroup2)
        radioGroup.check(R.id.rdBtnTotal)
        if (!file.exists()) {
            recyclerView.adapter = TransacaoAdapter(transacoes)
            return
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            val selectedText = radioButton.text.toString()
            transacoes.clear()

            if (!file.exists()) {
                recyclerView.adapter = TransacaoAdapter(transacoes)
                return@setOnCheckedChangeListener
            }

            try {
                file.bufferedReader().useLines { lines ->
                    lines.forEach { line ->
                        when (selectedText) {
                            "Total" -> {
                                transacoes.add(Transacao.fromString(line) ?: return@forEach)
                            }
                            "Crédito" -> {
                                if (line.contains("Credito")) {
                                    transacoes.add(Transacao.fromString(line) ?: return@forEach)
                                }
                            }
                            else -> {
                                if (line.contains("Debito")) {
                                    transacoes.add(Transacao.fromString(line) ?: return@forEach)
                                }
                            }
                        }
                    }
                }
            } catch (e: java.io.IOException) {
                e.printStackTrace() // Log the error
            }

            recyclerView.adapter = TransacaoAdapter(transacoes)
        }

        val fileInputStream = openFileInput(filename)
        val text = fileInputStream.bufferedReader().use { it.readText() }
        fileInputStream.close()

    }

    fun voltar(view: View) {
        finish()
    }

}