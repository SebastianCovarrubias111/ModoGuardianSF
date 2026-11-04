package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.navigation.Screen
import com.example.insertcode.viewmodel.LoginViewModel

@Composable
fun HomeAdminScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {

    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Dashboard Comercial",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Usuario: ${estado.email}")
        Text("Rol: ${estado.rol}")

        Spacer(modifier = Modifier.height(16.dp))

        IndicadorCard("Ventas Totales", "$12.500.000")
        IndicadorCard("Pedidos Pendientes", "15")
        IndicadorCard("Entregas Hoy", "8")

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton("Clientes") {
            navController.navigate(Screen.Clientes.route)
        }

        MenuButton("Pedidos") {
            navController.navigate(Screen.Pedidos.route)
        }

        MenuButton("Entregas") {
            navController.navigate(Screen.Entregas.route)
        }

        MenuButton("Indicadores") {
            navController.navigate(Screen.Indicadores.route)
        }

        MenuButton("Perfil") {
            navController.navigate(Screen.Perfil.route)
        }
    }
}

@Composable
fun HomeSupervisorScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {

    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Módulo Supervisor",
            style = MaterialTheme.typography.headlineLarge
        )

        Text("Usuario: ${estado.email}")

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton("Pedidos") {
            navController.navigate(Screen.Pedidos.route)
        }

        MenuButton("Entregas") {
            navController.navigate(Screen.Entregas.route)
        }

        MenuButton("Indicadores") {
            navController.navigate(Screen.Indicadores.route)
        }

        MenuButton("Perfil") {
            navController.navigate(Screen.Perfil.route)
        }
    }
}

@Composable
fun HomeOperatorScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {

    val estado by viewModel.estado.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Consola Operador",
            style = MaterialTheme.typography.headlineLarge
        )

        Text("Usuario: ${estado.email}")

        Spacer(modifier = Modifier.height(16.dp))

        MenuButton("Entregas") {
            navController.navigate(Screen.Entregas.route)
        }

        MenuButton("Perfil") {
            navController.navigate(Screen.Perfil.route)
        }
    }
}

@Composable
fun MenuButton(
    texto: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(texto)
    }
}