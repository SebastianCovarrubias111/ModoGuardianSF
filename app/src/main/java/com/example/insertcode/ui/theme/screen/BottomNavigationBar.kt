package com.example.insertcode.ui.theme.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.insertcode.navigation.Screen
import com.example.insertcode.ui.theme.*

private data class ItemNav(val ruta: String, val etiqueta: String, val icono: ImageVector)

@Composable
fun BottomBar(navController: NavHostController, homeRoute: String) {
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    val items = listOf(
        ItemNav(homeRoute, "Panel", Icons.Default.Dashboard),
        ItemNav(Screen.Pedidos.route, "Pedidos", Icons.Default.Inventory),
        ItemNav(Screen.Catalogo.route, "Catálogo", Icons.Default.Storefront),
        ItemNav(Screen.Entregas.route, "Ruta", Icons.Default.Route),
        ItemNav(Screen.Perfil.route, "Perfil", Icons.Default.Person)
    )

    NavigationBar(containerColor = GdSurface, tonalElevation = 0.dp) {
        items.forEach { item ->
            NavigationBarItem(
                selected = rutaActual == item.ruta,
                onClick = {
                    if (rutaActual != item.ruta) {
                        navController.navigate(item.ruta) {
                            popUpTo(homeRoute) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                label = { Text(item.etiqueta, style = MaterialTheme.typography.labelSmall) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = GdPrimary,
                    indicatorColor = GdPrimary,
                    unselectedIconColor = GdTextMuted,
                    unselectedTextColor = GdTextMuted
                )
            )
        }
    }
}