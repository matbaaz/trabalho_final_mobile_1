package com.example.finapp.model

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.finapp.R

class TransacaoAdapter(
    private val transacoes: List<Transacao>
) : Adapter<TransacaoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transacaoImage = itemView.findViewById<ImageView>(R.id.imgTransacao)
        val transacaoValor = itemView.findViewById<TextView>(R.id.textViewValor)
        val transacaoDescricao = itemView.findViewById<TextView>(R.id.textViewDescricao)

        fun bind(transacao: Transacao) {
            val imageResource = if (transacao.tipo == TransacaoTipo.CREDITO) {
                R.drawable.credito
            } else {
                R.drawable.debito
            }
            transacaoValor.text = transacao.valor.toString()
            transacaoDescricao.text = transacao.descricao
            transacaoImage.setImageResource(imageResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(transacoes[position])
    }

    override fun getItemCount(): Int {
        return transacoes.size
    }
}