package com.example.finapp.model

enum class TransacaoTipo {
    CREDITO,
    DEBITO;

    companion object {
        fun fromString(str: String): TransacaoTipo {
            return when (str.trim().lowercase()) {
                "credito" -> CREDITO
                "debito" -> DEBITO
                else -> throw IllegalArgumentException("Tipo desconhecido: $str")
            }
        }
    }
}