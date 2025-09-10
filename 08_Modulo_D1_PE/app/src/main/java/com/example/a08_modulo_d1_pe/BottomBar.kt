package com.example.a08_modulo_d1_pe

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(nav: NavHostController) {

    val telaAtual = nav.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Book, contentDescription = "") },
            label = {Text("Cursos")},
            selected = if (telaAtual == "dashboard") true else false,
            enabled = if (telaAtual == "dashboard") false else true,
            onClick = {nav.navigate("dashboard")},
            modifier = Modifier.testTag("MenuCursos")
        )
        NavigationBarItem(
            icon = {Icon(Icons.Default.School, contentDescription = "")},
            label = {Text("Professores")},
            selected = if (telaAtual == "professor") true else false,
            enabled = if (telaAtual == "professor") false else true,
            onClick = {nav.navigate("professor")},
            modifier = Modifier.testTag("MenuProfessor")
        )
        NavigationBarItem(
            icon = {Icon(Icons.Default.List, contentDescription = "")},
            label = {Text("Relatórios")},
            selected = if (telaAtual == "relatorio") true else false,
            enabled = if (telaAtual == "relatorio") false else true,
            onClick = {nav.navigate("relatorio")},
            modifier = Modifier.testTag("MenuRelatorio")
        )
    }

}