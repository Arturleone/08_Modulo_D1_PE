package com.example.a08_modulo_d1_pe.Views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.TextButton
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroCurso(nav: NavHostController) {

    val ctx = LocalContext.current
    var nomeCompleto by remember { mutableStateOf("") }
    var nomeBreve by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    var expandedCatego by remember { mutableStateOf(false) }
    var CategoText by remember { mutableStateOf("") }
    val categorias = loadJson.Categoria(ctx)

    var dataInicioText by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf<Long?>(null) }
    var dataInicioOpen by remember { mutableStateOf(false) }

    var dataFimText by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf<Long?>(null) }
    var dataFimOpen by remember { mutableStateOf(false) }

    val hoje = System.currentTimeMillis()

    var profModal by remember { mutableStateOf(false) }
    var professores by remember { mutableStateOf(loadJson.Professor(ctx)) }

    val toast: (String) -> Unit = {
        Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cursos - Novo") },
                navigationIcon = {
                    IconButton(onClick = { nav.navigate("dashboard") }) {
                        Icon(Icons.Default.Home, contentDescription = "")
                    }
                },
                actions = {

                }
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                Alignment.CenterVertically
            )
        ) {

            OutlinedTextField(
                label = { Text("Nome Completo") },
                value = nomeCompleto,
                onValueChange = {
                    if (it.length <= 50) {
                        nomeCompleto = it
                    }
                },
                modifier = Modifier.testTag("OutNomeCompleto")
            )
            OutlinedTextField(
                label = { Text("Nome Breve") },
                value = nomeBreve,
                onValueChange = {
                    if (it.length <= 15) {
                        nomeBreve = it
                    }
                },
                modifier = Modifier.testTag("OutNomeBreve")
            )

            ExposedDropdownMenuBox(
                expanded = expandedCatego,
                onExpandedChange = { expandedCatego = !expandedCatego }
            ) {
                OutlinedTextField(
                    value = CategoText,
                    onValueChange = {},
                    modifier = Modifier.menuAnchor().testTag("DropCatego"),
                    readOnly = true,
                    label = { Text("Categoria") }
                )

                ExposedDropdownMenu(
                    expanded = expandedCatego,
                    onDismissRequest = { expandedCatego = false }
                ) {
                    categorias.forEach {
                        DropdownMenuItem(
                            onClick = { CategoText = it.nome; expandedCatego = false }
                        ) {
                            Text(it.nome)
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OutlinedTextField(
                    value = dataInicioText,
                    onValueChange = {
                        dataInicioText = it
                        try {
                            dataInicio = SimpleDateFormat("dd/MM/yyyy").parse(it).time
                        } catch (e: Exception) {
                            dataInicio = null
                        }
                    },
                    trailingIcon = { androidx.compose.material.IconButton(onClick = {dataInicioOpen = true}, modifier = Modifier.testTag("BntCalendInicio")) {
                        Icon(Icons
                            .Default.CalendarMonth, contentDescription = null)
                    } },
                    modifier = Modifier.weight(1f).testTag("OutInicio"),
                    label = { Text("Data Início") }
                )

                OutlinedTextField(
                    value = dataFimText,
                    onValueChange = {
                        dataFimText = it
                        try {
                            dataFim = SimpleDateFormat("dd/MM/yyyy").parse(it).time
                        } catch (e: Exception) {
                            dataFim = null
                        }
                    },
                    trailingIcon = { androidx.compose.material.IconButton(onClick = {dataFimOpen = true}, modifier = Modifier.testTag("BntCalendFim")) {
                        Icon(Icons
                            .Default.CalendarMonth, contentDescription = null)
                    } },
                    modifier = Modifier.weight(1f).testTag("OutFim"),
                    label = { Text("Data Fim") }
                )

                if (dataInicioOpen) {
                    val state = rememberDatePickerState()
                    DatePickerDialog(
                        onDismissRequest = {},
                        confirmButton = { TextButton(onClick = {
                            dataInicio = state.selectedDateMillis
                            dataInicioText = SimpleDateFormat("dd/MM/yyyy").format(Date(dataInicio!!))
                            dataInicioOpen = false
                        }) {
                            Text("Salvar")
                        } },
                        dismissButton = { TextButton(onClick = {dataInicioOpen = false}, modifier = Modifier.testTag("BntFecharInicio")) {
                            Text("Fechar")
                        } }
                    ) {
                        DatePicker(state)
                    }
                }
                if (dataFimOpen) {
                    val state = rememberDatePickerState()
                    DatePickerDialog(
                        onDismissRequest = {},
                        confirmButton = { TextButton(onClick = {
                            dataFim = state.selectedDateMillis
                            dataFimText = SimpleDateFormat("dd/MM/yyyy").format(Date(dataFim!!))
                            dataFimOpen = false
                        }) {
                            Text("Salvar")
                        } },
                        dismissButton = { TextButton(onClick = {dataFimOpen = false}, modifier = Modifier.testTag("BntFecharFim")) {
                            Text("Fechar")
                        } }
                    ) {
                        DatePicker(state)
                    }
                }
            }

            Row (Modifier.clickable{profModal = true}.testTag("RowProf")){
                Text("Professores")
                Icon(Icons.Default.Add, contentDescription = "")
            }

            LazyColumn () {
                items(professores) {
                   if (it.selecionado.value) {
                       Card (modifier = Modifier.fillMaxWidth()) {
                           Row (Modifier.clickable{it.selecionado.value = !it.selecionado.value}){
                               Text(it.nome)
                               Icon(Icons.Default.Remove, contentDescription = null)
                           }
                       }
                   }
                }
            }

            Row {
                Button(onClick = {
                    when{
                        nomeCompleto.isBlank() -> toast("Nome completo é obrigatório")
                        nomeCompleto.length < 10 -> toast("Nome completo maior que 10 caracteres")
                        !nomeCompleto.trim().contains("") -> toast("Nome Completo deve conter Nome e Sobrenome")
                        nomeBreve.isBlank() -> toast("Nome breve é obrigatório")
                        CategoText.isBlank() -> toast("Categoria é obrigatório")
                        dataInicio == null -> toast("Data início é obrigatória")
                        dataInicio!! < hoje -> toast("Data início não pode ser anterior ao dia atual")
                        dataInicio!! > dataFim!! -> toast("Data fim não pode ser anterior a data de início")
                        professores.count{it.selecionado.value} <= 0 -> toast("Selecione ao menos um professor")
                        else -> {
                            toast("Curso salvo com sucesso")
                            nav.navigate("dashboard")
                        }
                    }

                }, modifier = Modifier.testTag("BntSalvar")) {
                    Text("Salvar")
                }
                Button(onClick = {nav.navigate("dashboard")}) {
                    Text("Cancelar")
                }
            }
        }
    }

    if (profModal) {
        AlertDialog(
            confirmButton = { androidx.compose.material3.TextButton(onClick = {profModal = false}, modifier = Modifier.testTag("BntFecharModal")) {
                Text("Salvar")
            } },
            onDismissRequest = {profModal = false },
            text = {
                Column () {
                    professores.forEach { item ->
                        Row (Modifier.clickable{ if (professores.count{it.selecionado.value} < 5 || item.selecionado.value) {
                            item.selecionado.value = !item.selecionado.value
                        } else {
                            toast("Só pode no máximo 5 prof")
                        }
                        }.testTag(item.nome)){
                            Text(item.nome)
                            Icon(if (item.selecionado.value) Icons.Default.Remove else Icons.Default.Add, contentDescription = "")
                        }
                    }
                }
            }
        )
    }
}