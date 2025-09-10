package com.example.a08_modulo_d1_pe.Views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a08_modulo_d1_pe.BottomBar
import com.example.a08_modulo_d1_pe.loadJson
import com.example.a08_modulo_d1_pe.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatorioScreen(nav: NavHostController) {

    val ctx = LocalContext.current
    var professores by remember { mutableStateOf(loadJson.Professor(ctx)) }
    var cursos by remember { mutableStateOf(loadJson.Curso(ctx)) }
    var modalProf by remember { mutableStateOf(false) }
    var modalCurso by remember { mutableStateOf(false) }
    var abrir by remember { mutableStateOf(false) }
    var mensagem by remember { mutableStateOf("") }
    Scaffold (
        bottomBar = { BottomBar(nav)},
        topBar = {
            TopAppBar(
                title = { Text("Relatórios") },
                navigationIcon = { IconButton(onClick = {nav.navigate("dashboard")}) {
                    Icon(Icons.Default.Home, contentDescription = "")
                } },
                actions = {

                }
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = {if (abrir) {
            nav.navigate("dashboard")
        } }, modifier = Modifier.alpha(if (abrir) 1f else 0f).testTag("BntFloat")) {
            Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.alpha(if (abrir) 0f else 1f))
        }}
    ) { paddingValues ->
        Column (Modifier.fillMaxSize().padding(paddingValues).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp,
            Alignment.CenterVertically)) {
            Text("Curso")

            Row (modifier = Modifier.background(Purple40).fillMaxWidth().horizontalScroll(
                rememberScrollState()
            ), horizontalArrangement = Arrangement.spacedBy(6.dp)){
                androidx.compose.material3.IconButton(onClick = {modalCurso = true}, modifier = Modifier.testTag("BntCurso")) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }

                cursos.forEach {
                    if(it.selecionado.value) {
                        Card (modifier = Modifier.wrapContentWidth().clickable{
                            it.selecionado.value = !it.selecionado.value
                        }){
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Text(it.nomeCompleto)
                                androidx.compose.material3.IconButton(onClick = {it.selecionado.value = !it.selecionado.value}) {
                                    Icon(Icons.Default.Close, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }

            Text("Professores")
            Row (modifier = Modifier.background(Purple40).fillMaxWidth().horizontalScroll(
                rememberScrollState()
            ), horizontalArrangement = Arrangement.spacedBy(6.dp)){
                androidx.compose.material3.IconButton(onClick = {modalProf = true}, modifier = Modifier.testTag("BntProf")) {
                    Icon(Icons.Default.Search, contentDescription = null)
                }

                professores.forEach {
                    if(it.selecionado.value) {
                        Card (modifier = Modifier.wrapContentWidth().clickable{
                            it.selecionado.value = !it.selecionado.value
                        }){
                            Row (verticalAlignment = Alignment.CenterVertically){
                                Text(it.nome)
                                androidx.compose.material3.IconButton(onClick = {it.selecionado.value = !it.selecionado.value}) {
                                    Icon(Icons.Default.Close, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }

            Button(onClick = {
                when{
                    professores.count{it.selecionado.value} <= 0 || professores.count{it.selecionado.value} <= 0 -> Toast.makeText(ctx, "selecionar pelo menos um curso ou professor.",
                        Toast.LENGTH_SHORT).show()
                    else -> {

                        mensagem += "\n"
                        mensagem += "Professor"
                        mensagem += "\n"
                        professores.forEach {
                            if (it.selecionado.value) {
                                mensagem += it.toString()
                            }
                        }
                        mensagem += "\n"
                        mensagem += "Cursos"
                        mensagem += "\n"
                        cursos.forEach {
                            if (it.selecionado.value) {
                                mensagem += it.toString()
                            }
                        }
                        abrir = true
                    }
                }

            }, modifier = Modifier.testTag("BntGerar")) {
                Text("Gerar Relatório")
            }

            Box( modifier = Modifier.size(300.dp).background(Color.LightGray)
            ) {
                Column () {
                    Text(mensagem)
                }
            }
        }
    }

    if (modalCurso) {
        AlertDialog(
            confirmButton = { androidx.compose.material3.TextButton(onClick = {modalCurso = false}, modifier = Modifier.testTag("BntFecharModalCurso")) {
                androidx.compose.material3.Text("Salvar")
            } },
            onDismissRequest = {modalCurso = false },
            text = {
                Column () {
                    cursos.forEach { item ->
                        Row (Modifier.clickable{ item.selecionado.value = !item.selecionado.value
                        }.testTag(item.nomeCompleto)) {
                            androidx.compose.material3.Text(item.nomeCompleto)
                            androidx.compose.material3.Icon(
                                if (item.selecionado.value) Icons.Default.Remove else Icons.Default.Add,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        )
    }
    if (modalProf) {
        AlertDialog(
            confirmButton = { androidx.compose.material3.TextButton(onClick = {modalProf = false}, modifier = Modifier.testTag("BntFecharModalProf")) {
                androidx.compose.material3.Text("Salvar")
            } },
            onDismissRequest = {modalProf = false },
            text = {
                Column () {
                    professores.forEach { item ->
                        Row (Modifier.clickable{ item.selecionado.value = !item.selecionado.value
                        }.testTag(item.nome)) {
                            androidx.compose.material3.Text(item.nome)
                            androidx.compose.material3.Icon(
                                if (item.selecionado.value) Icons.Default.Remove else Icons.Default.Add,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        )
    }
}