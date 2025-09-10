package com.example.a08_modulo_d1_pe.Views

import android.content.Context
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a08_modulo_d1_pe.BottomBar
import me.saket.telephoto.zoomable.rememberZoomableState
import me.saket.telephoto.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroProf(nav: NavHostController) {

    val ctx = LocalContext.current
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val toast: (String) -> Unit = {
        Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
    }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if (it !=null ) {
            val value = MediaStore.Images.Media.getBitmap(ctx.contentResolver, it)
            if (value !=null) {
                imageBitmap = value.asImageBitmap()
            }
        }
    }

    Scaffold (
        bottomBar = { BottomBar(nav)},
        topBar = {
            TopAppBar(
                title = { Text("Professores - Novo") },
                navigationIcon = { IconButton(onClick = {nav.navigate("dashboard")}) {
                    Icon(Icons.Default.Home, contentDescription = "")
                } },
                actions = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {nav.navigate("cadastroProf")}) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->
        Column (Modifier.fillMaxSize().padding(paddingValues).padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp,
            Alignment.CenterVertically)) {
            OutlinedTextField(
                modifier = Modifier.testTag("OutNome"),
                value = nome,
                onValueChange = {if (it.length <= 60) {
                    nome = it
                    }},
                label = {Text("Nome")},
                placeholder = {Text("Nome")},

            )
            OutlinedTextField(
                modifier = Modifier.testTag("OutEmail"),
                value = email,
                onValueChange = {email = it},
                label = {Text("Email")},
                placeholder = {Text("Email")},
            )

            Button(
                onClick = {
                    when {
                        nome.isBlank() -> toast("Nome é obrigatório")
                        !email.contains("@") || !email.contains(".") -> {
                            toast("Formato Inválido")
                        }
                        else -> {
                            toast("Professor salvo com sucesso")
                            nav.navigate("professor")
                        }

                    }
                }, modifier = Modifier.testTag("BntSalvar")
            ) {
                Text("Salvar")
            }

            Button(
                onClick = {nav.navigate("dashboard")}
            ) {
                Text("Cancelar")
            }

            Box( modifier = Modifier.size(300.dp).background(Color.LightGray).clickable{
                galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            ) {
                imageBitmap?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().zoomable(rememberZoomableState())
                    )
                } ?: run {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.fillMaxSize())
                }
            }

        }
    }

}
