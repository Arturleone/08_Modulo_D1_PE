package com.example.a08_modulo_d1_pe

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a08_modulo_d1_pe.Views.CadastroCurso
import com.example.a08_modulo_d1_pe.Views.CadastroProf
import com.example.a08_modulo_d1_pe.Views.DashboardScreen
import com.example.a08_modulo_d1_pe.Views.ProfessorScreen
import com.example.a08_modulo_d1_pe.Views.RelatorioScreen
import com.example.a08_modulo_d1_pe.ui.theme._08_Modulo_D1_PETheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
            Main()
        }
    }

    @Composable
    fun Main() {
        _08_Modulo_D1_PETheme {
            MyApp()
        }
    }
}

val conexao = MutableStateFlow(false)
@Composable
fun MyApp() {
    val nav = rememberNavController()
    val ctx = LocalContext.current
    LaunchedEffect(Unit) {
        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                conexao.value = true
                Toast.makeText(ctx, "Conectado com Internet", Toast.LENGTH_SHORT).show()
            }

            override fun onUnavailable() {
                super.onUnavailable()
                conexao.value = false
                Toast.makeText(ctx, "Uso do aplicativo em modo off-line e dados podem não estar totalmente atualizados por conta do uso sem internet", Toast.LENGTH_SHORT).show()

            }


            override fun onLost(network: Network) {
                super.onLost(network)
                conexao.value = false
                Toast.makeText(ctx, "Uso do aplicativo em modo off-line e dados podem não estar totalmente atualizados por conta do uso sem internet", Toast.LENGTH_SHORT).show()

            }

        }

        val conectivity = connectivityManager.activeNetwork != null
        if (!conectivity) {
            conexao.value = false
        }
         connectivityManager.registerDefaultNetworkCallback(callback)

    }

    Column () {
        if (!conexao.collectAsState().value) {
            Box(modifier = Modifier.fillMaxWidth().background(Color.Red).padding(12.dp).height(70.dp), contentAlignment = Alignment.Center) {
                Text("uso do aplicativo em modo off-line e dados podem não estar totalmente atualizados por conta do uso sem internet")
            }
        }
        NavApp(nav)
    }

}
@Composable
fun NavApp(nav: NavHostController) {
    NavHost(nav, "dashboard") {
        composable ("dashboard"){
            DashboardScreen(nav)
        }
        composable ("relatorio"){
            RelatorioScreen(nav)
        }
        composable ("professor"){
            ProfessorScreen(nav)
        }
        composable ("cadastroCurso"){
            CadastroCurso(nav)
        }
        composable ("cadastroProf"){
            CadastroProf(nav)
        }
    }
}