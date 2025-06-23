package com.example.finapp

import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finapp.model.Transacao
import com.example.finapp.model.TransacaoAdapter
import com.example.finapp.model.TransacaoTipo

class ExtratoActivity : AppCompatActivity() {
    val filename = "transacoes.txt"

    lateinit var radioGroup: RadioGroup
    lateinit var recyclerView: RecyclerView
    lateinit var saldo: TextView
    lateinit var transacoes: List<Transacao>
    var transacoesShow = mutableListOf<Transacao>()

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

        radioGroup = findViewById(R.id.radioGroup2)
        radioGroup.check(R.id.rdBtnTotal)


        transacoes = openFileInput(filename).bufferedReader().useLines { lines ->
            lines.mapNotNull {
                Transacao.fromString(it)
            }.toList()
        }

        if (transacoes.isNotEmpty()){
            val saldoDb = transacoes.sumOf { it.valor }
            saldo.text = String.format("R$ %.2f", saldoDb)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            updateExtrato(checkedId)
        }

        updateExtrato(0)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))

    }

    private fun updateExtrato(filter: Int){
        transacoesShow.clear()
        transacoesShow = when(filter){
            R.id.rdBttnDebito ->
                transacoes.filter { it.tipo == TransacaoTipo.DEBITO }.toMutableList()
            R.id.rdBttnCredito ->
                transacoes.filter { it.tipo == TransacaoTipo.CREDITO }.toMutableList()
            else ->
                transacoes.toMutableList()
        }
        recyclerView.adapter = TransacaoAdapter(transacoesShow)
    }

    fun voltar(view: View) {
        finish()
    }
}