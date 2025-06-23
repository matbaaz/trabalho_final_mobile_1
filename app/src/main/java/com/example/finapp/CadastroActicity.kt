package com.example.finapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.finapp.model.Transacao
import com.example.finapp.model.TransacaoTipo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CadastroActicity : AppCompatActivity() {
    val filename = "transacoes.txt"

    lateinit var editValor: EditText
    lateinit var editComment: EditText
    lateinit var radioGroup: RadioGroup
    var operationType: TransacaoTipo = TransacaoTipo.CREDITO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro_acticity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editValor = findViewById(R.id.editTextValot)
        editComment = findViewById(R.id.editTextDescricao)
        radioGroup = findViewById(R.id.radioGroup)
        radioGroup.check(R.id.rdBtnCredito)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            operationType = if (radioButton.text.toString() == "Crédito") {
                TransacaoTipo.CREDITO
            } else
                TransacaoTipo.DEBITO
        }
    }

    fun onVoltar(view: View){
        this.finish()
    }

    fun onCadastrar(view: View) {
        if (editValor.text.toString() == "" || editComment.text.toString() == ""){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }
        var valor = editValor.text.toString().toDouble()
        if (operationType == TransacaoTipo.DEBITO){
            valor *= -1
        }
        val descricao = editComment.text.toString()
        val dado = Transacao(operationType, valor, descricao)
        openFileOutput(filename, MODE_APPEND).use {
            it.write("$dado\n".toByteArray())
        }

        Toast.makeText(this, "Cadastro Realizado com sucesso", Toast.LENGTH_LONG).show()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(Toast.LENGTH_LONG * 1000L)
            finish()
        }
    }
}