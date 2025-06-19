package com.example.finapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CadastroActicity : AppCompatActivity() {
    val filename = "transacoes.txt"

    lateinit var editValor: EditText
    lateinit var editComment: EditText
    lateinit var radioGroup: RadioGroup
    var selectedText: Int = -1

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

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = findViewById(checkedId)
            selectedText = if (radioButton.text.toString() == "Crédito") {
                1
            } else
                0
        }
    }

    fun onCadastrar(view: View) {
        val valor = editValor.text.toString()
        val descricao = editComment.text.toString()
        val dado = Transacao(selectedText, valor.toDouble(), descricao)
        val fileOutputStream = openFileOutput(filename, MODE_PRIVATE)
        fileOutputStream.write(dado.toString().toByteArray())
        fileOutputStream.close()
        finish()
    }
}