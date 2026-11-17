package com.example.insertcode.ui.theme.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController

import com.example.insertcode.navigation.Screen

@Composable
fun BottomBar(
    navController: NavHostController
) {

    NavigationBar {

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.HomeAdmin.route)
            },
            icon = {
                Icon(
                    Icons.Default.Home,
                    contentDescription = "Dashboard"
                )
            },
            label = {
                Text("Inicio")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Pedidos.route)
            },
            icon = {
                Icon(
                    Icons.Default.Inventory,
                    contentDescription = "Pedidos"
                )
            },
            label = {
                Text("Pedidos")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Entregas.route)
            },
            icon = {
                Icon(
                    Icons.Default.LocalShipping,
                    contentDescription = "Entregas"
                )
            },
            label = {
                Text("Entregas")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Indicadores.route)
            },
            icon = {
                Icon(
                    Icons.Default.BarChart,
                    contentDescription = "Indicadores"
                )
            },
            label = {
                Text("KPIs")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(Screen.Perfil.route)
            },
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil"
                )
            },
            label = {
                Text("Perfil")
            }
        )
    }
}