package com.example.a08_modulo_d1_pe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Curso(
    val id: Int,
    val nomeCompleto: String,
    val nomeBreve: String,
    val categoria_id: Int,
    val visivel: Boolean,
    val dataInicio: String,
    val dataFim: String,
    val descricao: String,
    val formato: String,
    val professores_id: List<Int>,
    val porcentagem: Float,
    var selecionado: MutableState<Boolean> = mutableStateOf(false)
)

data class Professor(
    val id: Int,
    val nome: String,
    val email: String,
    val telefone: String,
    val descricao: String,
    var selecionado: MutableState<Boolean> = mutableStateOf(false)
)

data class Categoria(
    val id: Int,
    val nome: String,
    )

