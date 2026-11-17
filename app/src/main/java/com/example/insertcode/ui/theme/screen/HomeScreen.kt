package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.navigation.Screen
import com.example.insertcode.ui.theme.GdInfo
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdSurfaceHigh
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.GdWarning
import com.example.insertcode.ui.theme.components.BarraEstados
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.KpiCard
import com.example.insertcode.ui.theme.components.LeyendaPunto
import com.example.insertcode.ui.theme.components.SeccionTitulo
import com.example.insertcode.viewmodel.LoginViewModel

private data class AccionRapida(val texto: String, val icono: ImageVector, val ruta: String)

@Composable
fun HomeAdminScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val estado by viewModel.estado.collectAsState()
    PanelBase(
        titulo = "Panel Comercial",
        subtitulo = "Ventas y cumplimiento de entregas en terreno",
        email = estado.email,
        rol = estado.rol.orEmpty(),
        acciones = listOf(
            AccionRapida("Gestionar pedidos", Icons.Default.Inventory, Screen.Pedidos.route),
            AccionRapida("Catálogo de soluciones", Icons.Default.Storefront, Screen.Catalogo.route),
            AccionRapida("Ruta de despacho", Icons.Default.Route, Screen.Entregas.route),
            AccionRapida("Cartera de clientes", Icons.Default.People, Screen.Clientes.route),
            AccionRapida("Indicadores", Icons.Default.BarChart, Screen.Indicadores.route)
        ),
        navController = navController
    )
}

@Composable
fun HomeSupervisorScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val estado by viewModel.estado.collectAsState()
    PanelBase(
        titulo = "Módulo Supervisor",
        subtitulo = "Control de pedidos y entregas del equipo",
        email = estado.email,
        rol = estado.rol.orEmpty(),
        acciones = listOf(
            AccionRapida("Seguimiento de pedidos", Icons.Default.Inventory, Screen.Pedidos.route),
            AccionRapida("Ruta de despacho", Icons.Default.Route, Screen.Entregas.route),
            AccionRapida("Indicadores", Icons.Default.BarChart, Screen.Indicadores.route)
        ),
        navController = navController
    )
}

@Composable
fun HomeOperatorScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val estado by viewModel.estado.collectAsState()
    PanelBase(
        titulo = "Consola Operador",
        subtitulo = "Tus entregas asignadas del turno",
        email = estado.email,
        rol = estado.rol.orEmpty(),
        acciones = listOf(
            AccionRapida("Ruta de despacho", Icons.Default.Route, Screen.Entregas.route),
            AccionRapida("Mi perfil", Icons.Default.Person, Screen.Perfil.route)
        ),
        navController = navController,
        mostrarKpis = false
    )
}

@Composable
private fun PanelBase(
    titulo: String,
    subtitulo: String,
    email: String,
    rol: String,
    acciones: List<AccionRapida>,
    navController: NavHostController,
    mostrarKpis: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SeccionTitulo(titulo, subtitulo)

        Row(verticalAlignment = Alignment.CenterVertically) {
            EstadoChip(rol.uppercase().ifBlank { "SIN ROL" }, GdPrimary)
            Spacer(Modifier.width(8.dp))
            Text(email, style = MaterialTheme.typography.bodySmall, color = GdTextMuted)
        }

        if (mostrarKpis) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard("Ventas totales", "$12.500.000", "Acumulado del mes", GdSuccess, Modifier.weight(1f))
                KpiCard("Pendientes", "15", "Por despachar", GdWarning, Modifier.weight(1f))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                KpiCard("En ruta", "8", "Repartidores activos", GdInfo, Modifier.weight(1f))
                KpiCard("Entregados", "95", "Con confirmación", GdPrimary, Modifier.weight(1f))
            }

            GuardianCard(Modifier.fillMaxWidth()) {
                Text(
                    "Estado de pedidos en terreno",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = GdTextPrimary
                )
                Spacer(Modifier.height(14.dp))
                BarraEstados(pendientes = 15, enRuta = 8, entregados = 95)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    LeyendaPunto("Pendientes (15)", GdWarning)
                    LeyendaPunto("En ruta (8)", GdInfo)
                    LeyendaPunto("Entregados (95)", GdSuccess)
                }
            }
        }

        GuardianCard(Modifier.fillMaxWidth()) {
            Text(
                "Accesos rápidos",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = GdTextPrimary
            )
            acciones.forEach { accion ->
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(GdSurfaceHigh)
                        .clickable { navController.navigate(accion.ruta) }
                        .padding(horizontal = 14.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(accion.icono, null, tint = GdPrimary, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(
                        accion.texto,
                        modifier = Modifier.weight(1f),
                        color = GdTextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = GdTextMuted)
                }
            }
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