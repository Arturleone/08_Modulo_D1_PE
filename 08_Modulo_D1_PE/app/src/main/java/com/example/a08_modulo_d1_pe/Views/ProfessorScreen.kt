package com.example.a08_modulo_d1_pe.Views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RestoreFromTrash
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a08_modulo_d1_pe.BottomBar
import com.example.a08_modulo_d1_pe.loadJson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorScreen(nav: NavHostController) {

    val ctx = LocalContext.current
    val professores = loadJson.Professor(ctx)
    var search by remember { mutableStateOf("") }
    var abrirLixeira by remember { mutableStateOf(false) }

    Scaffold (
        bottomBar = { BottomBar(nav)},
        topBar = {
            TopAppBar(
                title = { Text("Professores") },
                navigationIcon = { IconButton(onClick = {nav.navigate("dashboard")}) {
                    Icon(Icons.Default.Home, contentDescription = "")
                } },
                actions = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {nav.navigate("cadastroProf")}, modifier = Modifier.testTag("BntCadastro")) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column (Modifier.fillMaxSize().padding(paddingValues).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp,
            Alignment.CenterVertically)) {
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

            professores.forEach {
                Card (modifier = Modifier.fillMaxWidth().clickable{abrirLixeira = !abrirLixeira}) {
                    Column {
                        Text(it.nome)
                        Text(it.descricao)
                        if (abrirLixeira) {
                            Icon(Icons.Default.RestoreFromTrash, contentDescription = null)
                        }
                    }
                }
            }
        }
    }
}