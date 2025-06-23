package com.example.finapp.model

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

data class Transacao(
    val tipo: TransacaoTipo,
    val valor: Double,
    val descricao: String
) {
    companion object {
        val gson = Gson()

        fun fromString(line: String): Transacao? {
            return try {
                gson.fromJson(line, Transacao::class.java)
            } catch (ex: JsonSyntaxException){
                null
            }
        }
    }

    override fun toString(): String {
        return gson.toJson(this)
    }
}