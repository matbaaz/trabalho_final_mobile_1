package com.example.finapp // This package declaration should match your directory structure

data class Transacao(
    val tipo: String,
    val valor: Double,
    val descricao: String
) {
    companion object {
        fun fromString(line: String): Transacao? {
            val parts = line.split(',')
            return if (parts.size == 3) {
                val tipo = parts[0].trim()
                val valor = parts[1].trim().toDoubleOrNull()
                val descricao = parts[2].trim()
                if (valor != null && (tipo == "Credito" || tipo == "Debito")) { // Add validation
                    Transacao(tipo, valor, descricao)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }
}