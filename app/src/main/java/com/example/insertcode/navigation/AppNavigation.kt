package com.example.insertcode.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.insertcode.ui.theme.screen.ClientesScreen
import com.example.insertcode.ui.theme.screen.EntregasScreen
import com.example.insertcode.ui.theme.screen.HomeAdminScreen
import com.example.insertcode.ui.theme.screen.HomeOperatorScreen
import com.example.insertcode.ui.theme.screen.HomeSupervisorScreen
import com.example.insertcode.ui.theme.screen.IndicadoresScreen
import com.example.insertcode.ui.theme.screen.LoginScreen
import com.example.insertcode.ui.theme.screen.PantallaPrincipal
import com.example.insertcode.ui.theme.screen.PerfilScreen
import com.example.insertcode.ui.theme.screen.PedidosScreen
import com.example.insertcode.viewmodel.EstadoViewModel
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.PerfilViewModel

sealed class Screen(val route: String) {

    data object Login : Screen("login")

    data object HomeAdmin : Screen("home_admin")
    data object HomeSupervisor : Screen("home_supervisor")
    data object HomeOperator : Screen("home_operator")

    data object AnimacionesEstados : Screen("animaciones_estados")
    data object Perfil : Screen("perfil")

    data object Clientes : Screen("clientes")
    data object Pedidos : Screen("pedidos")
    data object Entregas : Screen("entregas")
    data object Indicadores : Screen("indicadores")

}

sealed class NavigationEvent {
    data class NavigateTo(val route: String) : NavigationEvent()
    data object NavigateBack : NavigationEvent()
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    estadoViewModel: EstadoViewModel,
    perfilViewModel: PerfilViewModel,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route,
        modifier = modifier
    ) {

        // LOGIN
        composable(Screen.Login.route) {
            LoginScreen(viewModel = loginViewModel)
        }

        // ADMIN
        composable(Screen.HomeAdmin.route) {
            HomeAdminScreen(
                viewModel = loginViewModel,
                navController = navController
            )
        }

        // SUPERVISOR
        composable(Screen.HomeSupervisor.route) {
            HomeSupervisorScreen(
                viewModel = loginViewModel,
                navController = navController
            )
        }

        // OPERADOR
        composable(Screen.HomeOperator.route) {
            HomeOperatorScreen(
                viewModel = loginViewModel,
                navController = navController
            )
        }

        // GUÍA 12
        composable(Screen.AnimacionesEstados.route) {
            PantallaPrincipal(
                viewModel = estadoViewModel
            )
        }

        // PERFIL
        composable(Screen.Perfil.route) {
            PerfilScreen(
                viewModel = perfilViewModel
            )
        }

        // NUEVAS PANTALLAS

        composable(Screen.Clientes.route) {
            ClientesScreen()
        }

        composable(Screen.Pedidos.route) {
            PedidosScreen()
        }

        composable(Screen.Entregas.route) {
            EntregasScreen()
        }

        composable(Screen.Indicadores.route) {
            IndicadoresScreen()
        }
    }
}