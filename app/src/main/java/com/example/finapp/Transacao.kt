package com.example.finapp

data class Transacao(
    val tipo: Int, //se for credito é 1 e se for debito é 0, sei la
    val valor: Double,
    val descricao: String
) {
    fun toTransacao(): Transacao {
        return Transacao(tipo, valor, descricao)
    }
}
