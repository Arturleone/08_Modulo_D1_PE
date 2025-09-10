package com.example.a08_modulo_d1_pe

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.JsonParser

object loadJson {
    fun Professor(ctx: Context): List<Professor> {
        val arquivo = ctx.assets.open("dados.json").bufferedReader().use { it.readText() }
        val JsonParser = JsonParser.parseString(arquivo).asJsonObject["professores_id"].asJsonArray
        return JsonParser.mapNotNull {
            Gson().fromJson(it, Professor::class.java).apply {
                selecionado = mutableStateOf(false)
            }
        }
    }
    fun Curso(ctx: Context): List<Curso> {
        val arquivo = ctx.assets.open("dados.json").bufferedReader().use { it.readText() }
        val JsonParser = JsonParser.parseString(arquivo).asJsonObject["cursos"].asJsonArray
        return JsonParser.mapNotNull {
            Gson().fromJson(it, Curso::class.java).apply {
                selecionado = mutableStateOf(false)
            }
        }
    }
    fun Categoria(ctx: Context): List<Categoria> {
        val arquivo = ctx.assets.open("dados.json").bufferedReader().use { it.readText() }
        val JsonParser = JsonParser.parseString(arquivo).asJsonObject["categorias"].asJsonArray
        return JsonParser.mapNotNull {
            Gson().fromJson(it, Categoria::class.java)
        }
    }
}