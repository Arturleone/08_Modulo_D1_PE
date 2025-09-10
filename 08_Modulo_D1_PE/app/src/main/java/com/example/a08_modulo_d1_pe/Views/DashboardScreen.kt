package com.example.a08_modulo_d1_pe.Views

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a08_modulo_d1_pe.BottomBar
import com.example.a08_modulo_d1_pe.Curso
import com.example.a08_modulo_d1_pe.loadJson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(nav: NavHostController) {

    val ctx = LocalContext.current
    var isGrid by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    val cursos = loadJson.Curso(ctx)
    var onTap by remember { mutableStateOf<Curso?>(null) }
    var onLong by remember { mutableStateOf<Curso?>(null) }

    Scaffold (
        bottomBar = { BottomBar(nav)},
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = { IconButton(onClick = {nav.navigate("dashboard")}) {
                    Icon(Icons.Default.Home, contentDescription = "")
                } },
                actions = {
                   IconButton(onClick = {isGrid = true}, modifier = Modifier.testTag("Grid")) {
                        Icon(Icons.Default.GridOn, contentDescription = "")
                    }
                    IconButton(onClick = {isGrid = false}, modifier = Modifier.testTag("List")) {
                        Icon(Icons.Default.List, contentDescription = "")
                    }
                }
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = {
            nav.navigate("cadastroCurso")
        }, modifier = Modifier.testTag("BntCadastroCurso")) {
            Icon(Icons.Default.Add, contentDescription = "")
        }}
    ) { paddingValues ->
        Column (Modifier.fillMaxSize().padding(paddingValues).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp,
            Alignment.CenterVertically)) {
            val cursosFiltrados = cursos.filter { it.nomeCompleto.contains(search, true) || it.nomeBreve.contains(search, true) }
            OutlinedTextField(
                modifier = Modifier.testTag("OutSearch"),
                value = search,
                onValueChange = {search = it},
                label = {Text("Busca")},
                placeholder = {Text("Busca")},
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            )

            if (search == "@#$") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Caracteres inválidos não são aceitos")
                    Toast.makeText(ctx, "Caracteres inválidos não são aceitos", Toast.LENGTH_SHORT).show()
                }
            } else {
                    if (isGrid) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(if (search == "") cursos else cursosFiltrados) { item ->
                                Card (modifier = Modifier.fillMaxSize().padding(12.dp).pointerInput(Unit) {
                                    detectTapGestures (
                                        onTap = {onTap = item},
                                        onLongPress = {onLong = item}
                                    )
                                }.testTag("curso_${item.nomeBreve}")) {
                                    CircularProgressIndicator(progress = item.porcentagem)
                                    Text(item.porcentagem.toString())
                                    Text(item.nomeCompleto)
                                }
                            }
                        }
                    } else {
                        LazyColumn (
                            modifier = Modifier.fillMaxSize()
                        ){
                            items(if (search == "") cursos else cursosFiltrados) { item ->
                                Card (modifier = Modifier.fillMaxSize().padding(12.dp).pointerInput(Unit) {
                                    detectTapGestures (
                                        onTap = {onTap = item},
                                        onLongPress = {onLong = item}
                                    )
                                }.testTag("curso_${item.nomeBreve}")) {
                                    CircularProgressIndicator(progress = item.porcentagem)
                                    Text(item.porcentagem.toString())
                                    Text(item.nomeBreve)
                                }
                            }
                        }
                    }
            }
        }

        onTap?.let {
            AlertDialog(
                title = {Text("Editar Curso")},
                text = {Column {
                    Text(it.nomeBreve)
                    Text(it.nomeCompleto)
                }},
                onDismissRequest = {onTap = null},
                confirmButton = {
                    TextButton(onClick = {onTap = null}) {
                        Text("Salvar")
                    }
                }
            )
        }

        onLong?.let {
            AlertDialog(
                title = {Text(it.nomeCompleto)},
                text = {Column {
                    Text(it.nomeBreve)
                    Text(it.porcentagem.toString())
                    Text(it.dataFim)
                    Text(it.dataInicio)
                    Text(it.descricao)
                }},
                onDismissRequest = {onLong = null},
                confirmButton = {
                    TextButton(onClick = {onLong = null}, modifier = Modifier.testTag("fechar")) {
                        Text("Fechar")
                    }
                }
            )
        }
    }
}