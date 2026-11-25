package com.example.insertcode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.insertcode.navigation.AppNavigation
import com.example.insertcode.navigation.NavigationEvent
import com.example.insertcode.navigation.Screen
import com.example.insertcode.ui.theme.InsertCodeTheme
import com.example.insertcode.ui.theme.components.BrandTopBar
import com.example.insertcode.ui.theme.screen.BottomBar
import com.example.insertcode.viewmodel.LoginViewModel
import com.example.insertcode.viewmodel.EstadoViewModel
import com.example.insertcode.viewmodel.PerfilViewModel

class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val estadoViewModel: EstadoViewModel by viewModels()
    private val perfilViewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent {
            InsertCodeTheme {
                val navController = rememberNavController()
                val estadoLogin by loginViewModel.estado.collectAsState()
                val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

                val mostrarBarras = rutaActual != null &&
                        rutaActual != Screen.Login.route &&
                        rutaActual != Screen.CrearCuenta.route &&
                        rutaActual != Screen.AnimacionesEstados.route

                val homeRoute = when (estadoLogin.rol) {
                    "Admin" -> Screen.HomeAdmin.route
                    "Supervisor" -> Screen.HomeSupervisor.route
                    else -> Screen.HomeOperator.route
                }

                // LaunchedEffect reactivo requerido por la evaluación
                LaunchedEffect(key1 = true) {
                    loginViewModel.eventosNavegacion.collect { evento ->
                        when (evento) {
                            is NavigationEvent.NavigateTo -> {
                                navController.navigate(evento.route) {
                                    launchSingleTop = true
                                }
                            }
                            is NavigationEvent.NavigateBack -> {
                                navController.popBackStack()
                            }
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background,
                    topBar = {
                        if (mostrarBarras) {
                            BrandTopBar(
                                rol = estadoLogin.rol.orEmpty(),
                                onLogout = {
                                    loginViewModel.cerrarSesion()
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(0) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    },
                    bottomBar = {
                        if (mostrarBarras) BottomBar(navController, homeRoute)
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues = innerPadding)
                    ) {
                        AppNavigation(
                            navController = navController,
                            loginViewModel = loginViewModel,
                            estadoViewModel = estadoViewModel,
                            perfilViewModel = perfilViewModel
                        )
                    }
                }
            }
        }
    }
}