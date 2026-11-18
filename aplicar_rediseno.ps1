# Aplica el rediseño de Modo Guardián. Ejecutar desde la RAÍZ del proyecto (donde está la carpeta "app").
$ErrorActionPreference = "Stop"
$raiz = (Get-Location).Path
if (-not (Test-Path (Join-Path $raiz "app/src/main/java/com/example/insertcode"))) { Write-Host "ERROR: ejecuta esto desde la raíz del proyecto (donde esta la carpeta app)" -ForegroundColor Red; exit 1 }
$utf8 = New-Object System.Text.UTF8Encoding($false)
function Escribir($ruta, $contenido) {
  $destino = Join-Path $raiz $ruta
  New-Item -ItemType Directory -Force -Path (Split-Path $destino) | Out-Null
  [System.IO.File]::WriteAllText($destino, $contenido, $utf8)
  Write-Host "OK  $ruta" -ForegroundColor Green
}

Write-Host "Paso 1/15: ui/theme/Color.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/Color.kt' @'
package com.example.insertcode.ui.theme

import androidx.compose.ui.graphics.Color

// Colores originales del proyecto
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Paleta Modo Guardián
val GuardianPrimary = Color(0xFF3454D1)
val GuardianPrimaryDark = Color(0xFF0F1B3D)
val GuardianPrimaryLight = Color(0xFF9DB2FF)
val GuardianAccent = Color(0xFF00BFA6)
val GuardianBackground = Color(0xFFF5F7FB)
val GuardianGradientTop = Color(0xFF0F1B3D)
val GuardianGradientBottom = Color(0xFF3454D1)

// Paleta oscura Modo Guardián (rediseño)
val GdBackground = Color(0xFF0A0F1F)
val GdBackgroundAlt = Color(0xFF0E1530)
val GdSurface = Color(0xFF111833)
val GdSurfaceHigh = Color(0xFF18213F)
val GdBorder = Color(0xFF263253)
val GdPrimary = Color(0xFF4C6FFF)
val GdPrimarySoft = Color(0xFF1B2754)
val GdViolet = Color(0xFF8B5CF6)
val GdTextPrimary = Color(0xFFF2F5FF)
val GdTextMuted = Color(0xFF8D99BD)
val GdSuccess = Color(0xFF2DD4A4)
val GdWarning = Color(0xFFFBBF24)
val GdInfo = Color(0xFF60A5FA)
val GdDanger = Color(0xFFF87171)
'@

Write-Host "Paso 2/15: ui/theme/Theme.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/Theme.kt' @'
package com.example.insertcode.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GuardianDarkScheme = darkColorScheme(
    primary = GdPrimary,
    onPrimary = Color.White,
    secondary = GdInfo,
    tertiary = GdSuccess,
    background = GdBackground,
    onBackground = GdTextPrimary,
    surface = GdSurface,
    onSurface = GdTextPrimary,
    surfaceVariant = GdSurfaceHigh,
    onSurfaceVariant = GdTextMuted,
    outline = GdBorder,
    outlineVariant = GdBorder,
    error = GdDanger
)

@Composable
fun InsertCodeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GuardianDarkScheme,
        typography = Typography,
        content = content
    )
}
'@

Write-Host "Paso 3/15: ui/theme/components/GuardianComponents.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/components/GuardianComponents.kt' @'
package com.example.insertcode.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insertcode.ui.theme.*

private val FormaTarjeta = RoundedCornerShape(20.dp)

fun colorDeEstado(estado: String): Color = when (estado.lowercase()) {
    "entregado" -> GdSuccess
    "en ruta" -> GdInfo
    else -> GdWarning
}

@Composable
fun BrandTopBar(rol: String, onLogout: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(GdBackground)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Brush.linearGradient(listOf(GdPrimary, GdViolet))),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Code, contentDescription = null, tint = Color.White)
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("InsertCode", fontWeight = FontWeight.Bold, color = GdTextPrimary)
            Text("Modo Guardián", style = MaterialTheme.typography.labelSmall, color = GdTextMuted)
        }
        if (rol.isNotBlank()) EstadoChip(rol.uppercase(), GdPrimary)
        IconButton(onClick = onLogout) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Cerrar sesión", tint = GdTextMuted)
        }
    }
}

@Composable
fun SeccionTitulo(titulo: String, subtitulo: String) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = GdTextPrimary
        )
        Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = GdTextMuted)
    }
}

@Composable
fun GuardianCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(FormaTarjeta)
            .background(GdSurface)
            .border(1.dp, GdBorder, FormaTarjeta)
            .padding(16.dp),
        content = content
    )
}

@Composable
fun EstadoChip(texto: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = texto,
        color = color,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.14f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

@Composable
fun KpiCard(
    etiqueta: String,
    valor: String,
    detalle: String,
    colorValor: Color,
    modifier: Modifier = Modifier
) {
    GuardianCard(modifier = modifier) {
        Box(
            Modifier
                .width(26.dp)
                .height(3.dp)
                .clip(RoundedCornerShape(50))
                .background(colorValor)
        )
        Spacer(Modifier.height(10.dp))
        Text(
            etiqueta.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = GdTextMuted,
            letterSpacing = 0.8.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            valor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.ExtraBold,
            color = colorValor
        )
        Text(detalle, style = MaterialTheme.typography.labelSmall, color = GdTextMuted)
    }
}

@Composable
fun BarraEstados(pendientes: Int, enRuta: Int, entregados: Int) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(50))
    ) {
        if (pendientes > 0) Box(Modifier.weight(pendientes.toFloat()).fillMaxHeight().background(GdWarning))
        if (enRuta > 0) Box(Modifier.weight(enRuta.toFloat()).fillMaxHeight().background(GdInfo))
        if (entregados > 0) Box(Modifier.weight(entregados.toFloat()).fillMaxHeight().background(GdSuccess))
    }
}

@Composable
fun LeyendaPunto(texto: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(6.dp))
        Text(texto, style = MaterialTheme.typography.labelSmall, color = GdTextMuted)
    }
}
'@

Write-Host "Paso 4/15: ui/theme/screen/BottomNavigationBar.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/BottomNavigationBar.kt' @'
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
'@

Write-Host "Paso 5/15: ui/theme/screen/HomeScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/HomeScreen.kt' @'
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
'@

Write-Host "Paso 6/15: ui/theme/screen/PedidosScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/PedidosScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.theme.GdBorder
import com.example.insertcode.ui.theme.GdDanger
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.GdWarning
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.SeccionTitulo
import com.example.insertcode.ui.theme.components.colorDeEstado

data class Pedido(
    val codigo: String,
    val cliente: String,
    val estado: String,
    val prioridad: String,
    val fecha: String
)

@Composable
fun PedidosScreen(
    navController: NavHostController
) {

    val pedidos = listOf(
        Pedido("001", "Ferretería Norte", "Pendiente", "Alta", "15/11/2026"),
        Pedido("002", "Retail Santiago", "En Ruta", "Media", "16/11/2026"),
        Pedido("003", "Constructora Andes", "Entregado", "Baja", "17/11/2026")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SeccionTitulo("Gestión de Pedidos", "Seguimiento de órdenes y prioridad de despacho") }

        items(pedidos) { pedido ->
            val colorPrioridad = when (pedido.prioridad) {
                "Alta" -> GdDanger
                "Media" -> GdWarning
                else -> GdSuccess
            }
            GuardianCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "PED-${pedido.codigo}",
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelSmall,
                        fontFamily = FontFamily.Monospace,
                        color = GdTextMuted
                    )
                    EstadoChip(pedido.estado, colorDeEstado(pedido.estado))
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    pedido.cliente,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GdTextPrimary
                )
                Text(
                    "Emitido el ${pedido.fecha}",
                    style = MaterialTheme.typography.bodySmall,
                    color = GdTextMuted
                )
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = GdBorder)
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Prioridad", modifier = Modifier.weight(1f), color = GdTextMuted)
                    Text(pedido.prioridad, color = colorPrioridad, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
'@

Write-Host "Paso 7/15: ui/theme/screen/EntregasScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/EntregasScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.theme.GdBackground
import com.example.insertcode.ui.theme.GdBorder
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdPrimarySoft
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.LeyendaPunto
import com.example.insertcode.ui.theme.components.SeccionTitulo
import com.example.insertcode.ui.theme.components.colorDeEstado

data class Entrega(
    val id: String,
    val destino: String,
    val estado: String,
    val repartidor: String,
    val fecha: String
)

@Composable
fun EntregasScreen(
    navController: NavHostController
) {

    val entregas = listOf(
        Entrega("001", "Santiago Centro", "En Ruta", "Juan Pérez", "15/11/2026"),
        Entrega("002", "Maipú", "Pendiente", "María González", "16/11/2026"),
        Entrega("003", "Providencia", "Entregado", "Carlos Soto", "17/11/2026")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            SeccionTitulo("Ruta de Despacho", "Seguimiento de entregas asignadas (coordenadas simuladas)")
        }

        item {
            GuardianCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Mapa de ruta",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.Bold,
                        color = GdTextPrimary
                    )
                    LeyendaPunto("GPS activo", GdSuccess)
                }
                Spacer(Modifier.height(12.dp))
                MapaRuta(entregas.map { it.estado })
            }
        }

        item {
            Text(
                "Secuencia de entregas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = GdTextPrimary
            )
        }

        itemsIndexed(entregas) { indice, entrega ->
            GuardianCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(GdPrimarySoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("${indice + 1}", color = GdPrimary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            entrega.destino,
                            fontWeight = FontWeight.Bold,
                            color = GdTextPrimary
                        )
                        Text(
                            "${entrega.repartidor} · ${entrega.fecha}",
                            style = MaterialTheme.typography.bodySmall,
                            color = GdTextMuted
                        )
                    }
                    EstadoChip(entrega.estado, colorDeEstado(entrega.estado))
                }
            }
        }
    }
}

@Composable
private fun MapaRuta(estados: List<String>) {
    // Posiciones relativas (0..1): base + una parada por entrega
    val puntos = listOf(
        Offset(0.10f, 0.78f),
        Offset(0.34f, 0.42f),
        Offset(0.62f, 0.60f),
        Offset(0.88f, 0.22f)
    )
    val colores = listOf(GdSuccess) + estados.map { colorDeEstado(it) }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(GdBackground)
    ) {
        // Cuadrícula
        val paso = 32.dp.toPx()
        var x = 0f
        while (x < size.width) {
            drawLine(GdBorder.copy(alpha = 0.35f), Offset(x, 0f), Offset(x, size.height))
            x += paso
        }
        var y = 0f
        while (y < size.height) {
            drawLine(GdBorder.copy(alpha = 0.35f), Offset(0f, y), Offset(size.width, y))
            y += paso
        }

        val pts = puntos.map { Offset(it.x * size.width, it.y * size.height) }

        // Ruta punteada
        for (i in 0 until pts.size - 1) {
            drawLine(
                color = GdPrimary,
                start = pts[i],
                end = pts[i + 1],
                strokeWidth = 3.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(18f, 14f))
            )
        }

        // Nodos con halo
        pts.forEachIndexed { i, p ->
            val c = colores.getOrElse(i) { GdPrimary }
            drawCircle(c.copy(alpha = 0.25f), radius = 16.dp.toPx(), center = p)
            drawCircle(c, radius = 8.dp.toPx(), center = p)
        }
    }
}
'@

Write-Host "Paso 8/15: ui/theme/screen/CatalogoScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/CatalogoScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.insertcode.ui.theme.GdBorder
import com.example.insertcode.ui.theme.GdInfo
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.GdViolet
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.SeccionTitulo

private data class Producto(
    val codigo: String,
    val categoria: String,
    val nombre: String,
    val descripcion: String,
    val precio: String
)

@Composable
fun CatalogoScreen() {

    val productos = listOf(
        Producto(
            "PROD-01", "Software", "Licencia Panel de Ventas",
            "Sistema centralizado de ventas, inventario e indicadores.", "$450.000"
        ),
        Producto(
            "PROD-02", "Hardware", "Rastreador GPS IoT",
            "Módulo de seguimiento vehicular en tiempo real para flotas.", "$120.000"
        ),
        Producto(
            "PROD-03", "Hardware", "Terminal POS Android",
            "Dispositivo portátil táctil con lector de comprobantes.", "$180.000"
        )
    )
    var cotizados by remember { mutableStateOf(setOf<String>()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SeccionTitulo("Catálogo de Soluciones", "Productos y licencias disponibles para cotizar") }

        items(productos) { p ->
            val cotizado = p.codigo in cotizados
            GuardianCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    EstadoChip(p.categoria.uppercase(), if (p.categoria == "Software") GdInfo else GdViolet)
                    Spacer(Modifier.weight(1f))
                    Text(p.codigo, style = MaterialTheme.typography.labelSmall, color = GdTextMuted)
                }
                Spacer(Modifier.height(10.dp))
                Text(
                    p.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GdTextPrimary
                )
                Spacer(Modifier.height(4.dp))
                Text(p.descripcion, style = MaterialTheme.typography.bodySmall, color = GdTextMuted)
                Spacer(Modifier.height(14.dp))
                HorizontalDivider(color = GdBorder)
                Spacer(Modifier.height(14.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        p.precio,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = GdSuccess
                    )
                    Button(
                        onClick = {
                            cotizados = if (cotizado) cotizados - p.codigo else cotizados + p.codigo
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (cotizado) GdSuccess else GdPrimary,
                            contentColor = if (cotizado) Color(0xFF04281F) else Color.White
                        )
                    ) {
                        Icon(
                            if (cotizado) Icons.Default.Check else Icons.Default.ShoppingCart,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(if (cotizado) "Agregado" else "Cotizar")
                    }
                }
            }
        }
    }
}
'@

Write-Host "Paso 9/15: ui/theme/screen/PerfilScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/PerfilScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.ImagenInteligente
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.SeccionTitulo
import com.example.insertcode.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    navController: NavHostController,
    email: String,
    rol: String,
    modifier: Modifier = Modifier
) {

    val imagenUri by viewModel.imagenUri.collectAsState()

    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.actualizarImagen(uri)
    }

    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito) {
            viewModel.confirmarFotoCamara()
        }
    }

    val permisos = when (rol) {
        "Admin" -> listOf(
            "Gestión de clientes y catálogo",
            "Registro y cotización de pedidos",
            "Consulta de entregas y rutas",
            "Acceso a indicadores"
        )
        "Supervisor" -> listOf(
            "Seguimiento de pedidos",
            "Consulta de entregas y rutas",
            "Acceso a indicadores"
        )
        else -> listOf(
            "Consulta de entregas asignadas",
            "Actualización de estado de despacho"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SeccionTitulo("Mi perfil", "Datos de la cuenta, foto y permisos asignados")

        GuardianCard(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImagenInteligente(imagenUri = imagenUri)
                Spacer(Modifier.height(12.dp))
                Text(
                    email.ifBlank { "Sin correo" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GdTextPrimary
                )
                Spacer(Modifier.height(6.dp))
                EstadoChip(rol.uppercase().ifBlank { "SIN ROL" }, GdPrimary)
                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { launcherGaleria.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Galería")
                    }
                    OutlinedButton(
                        onClick = {
                            val uriSeguro = viewModel.crearUriTemporal()
                            launcherCamara.launch(uriSeguro)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PhotoCamera, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Cámara")
                    }
                }
            }
        }

        GuardianCard(Modifier.fillMaxWidth()) {
            Text(
                "Permisos del perfil ($rol)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = GdTextPrimary
            )
            permisos.forEach { permiso ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        permiso,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GdTextPrimary
                    )
                    EstadoChip("ACTIVO", GdSuccess)
                }
            }
        }
    }
}
'@

Write-Host "Paso 10/15: ui/theme/screen/ClientesScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/ClientesScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdPrimarySoft
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.SeccionTitulo

@Composable
fun ClientesScreen(navController: NavHostController) {

    val clientes = listOf(
        "Ferretería Norte",
        "Retail Santiago",
        "Distribuidora Sur",
        "Constructora Andes",
        "Comercial Central"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SeccionTitulo("Cartera de Clientes", "Cuentas activas asignadas a tu equipo") }

        items(clientes) { cliente ->
            GuardianCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(GdPrimarySoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(cliente.first().toString(), color = GdPrimary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(cliente, fontWeight = FontWeight.Bold, color = GdTextPrimary)
                        Text(
                            "Cliente activo",
                            style = MaterialTheme.typography.bodySmall,
                            color = GdTextMuted
                        )
                    }
                    EstadoChip("ACTIVO", GdSuccess)
                }
            }
        }
    }
}
'@

Write-Host "Paso 11/15: ui/theme/screen/IndicadoresScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/IndicadoresScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.theme.GdInfo
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdWarning
import com.example.insertcode.ui.theme.components.KpiCard
import com.example.insertcode.ui.theme.components.SeccionTitulo

@Composable
fun IndicadoresScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SeccionTitulo("Indicadores", "Resumen de desempeño comercial")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiCard("Ventas totales", "$12.500.000", "Acumulado del mes", GdSuccess, Modifier.weight(1f))
            KpiCard("Pedidos", "120", "Registrados", GdInfo, Modifier.weight(1f))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            KpiCard("Entregas", "95", "Realizadas", GdPrimary, Modifier.weight(1f))
            KpiCard("Pendientes", "25", "Por despachar", GdWarning, Modifier.weight(1f))
        }
    }
}

@Composable
fun IndicadorCard(
    titulo: String,
    valor: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(titulo)

            Text(
                valor,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}
'@

Write-Host "Paso 12/15: ui/theme/screen/LoginScreen.kt"
Escribir 'app/src/main/java/com/example/insertcode/ui/theme/screen/LoginScreen.kt' @'
package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.insertcode.ui.theme.GdBackground
import com.example.insertcode.ui.theme.GdBackgroundAlt
import com.example.insertcode.ui.theme.GdBorder
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdSurface
import com.example.insertcode.ui.theme.GdSurfaceHigh
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.R
import com.example.insertcode.model.LoginUiState
import com.example.insertcode.ui.theme.GuardianGradientBottom
import com.example.insertcode.ui.theme.GuardianGradientTop
import com.example.insertcode.ui.utils.WindowSizeClass
import com.example.insertcode.ui.utils.rememberWindowSizeClass
import com.example.insertcode.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val windowSize = rememberWindowSizeClass()
    val estado by viewModel.estado.collectAsState()
    val isLoginEnabled = !estado.isLoading
    val margenHorizontal = if (windowSize == WindowSizeClass.COMPACT) 20.dp else 48.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GdBackground, GdBackgroundAlt)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = margenHorizontal, vertical = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.widthIn(max = 480.dp).fillMaxWidth()) {

                // Chip de acceso seguro
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(GdSuccess.copy(alpha = 0.14f))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Lock, null, tint = GdSuccess, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "Acceso seguro",
                        style = MaterialTheme.typography.labelMedium,
                        color = GdSuccess,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Bienvenido de nuevo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = GdTextPrimary
                )
                Text(
                    text = "Accede a tu turno de supervisión",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GdTextMuted
                )
                Spacer(Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = GdSurface),
                    border = BorderStroke(1.dp, GdBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoginFormContent(
                            estado = estado,
                            viewModel = viewModel,
                            isLoginEnabled = isLoginEnabled
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginFormContent(
    estado: LoginUiState,
    viewModel: LoginViewModel,
    isLoginEnabled: Boolean
) {
    var verPassword by remember { mutableStateOf(false) }
    // null = sin ventana; "Google" o "Apple" = ventana de cuenta abierta
    var proveedorActivo by remember { mutableStateOf<String?>(null) }

    val transformacion =
        if (verPassword) VisualTransformation.None else PasswordVisualTransformation()
    val formaCampo = RoundedCornerShape(14.dp)

    // ========== CAMPO EMAIL ==========
    OutlinedTextField(
        value = estado.email,
        onValueChange = viewModel::onEmailChange,
        label = { Text("Correo electrónico") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        singleLine = true,
        shape = formaCampo,
        colors = coloresCampo(),
        isError = estado.errores.email != null,
        supportingText = { estado.errores.email?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // ========== CAMPO CONTRASEÑA ==========
    OutlinedTextField(
        value = estado.password,
        onValueChange = viewModel::onPasswordChange,
        label = { Text("Contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            TextButton(onClick = { verPassword = !verPassword }) {
                Text(if (verPassword) "Ocultar" else "Ver")
            }
        },
        singleLine = true,
        shape = formaCampo,
        colors = coloresCampo(),
        visualTransformation = transformacion,
        isError = estado.errores.password != null,
        supportingText = { estado.errores.password?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // ========== CAMPO CONFIRMAR CONTRASEÑA ==========
    OutlinedTextField(
        value = estado.confirmPassword,
        onValueChange = viewModel::onConfirmPasswordChange,
        label = { Text("Confirmar contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        singleLine = true,
        shape = formaCampo,
        colors = coloresCampo(),
        visualTransformation = transformacion,
        isError = estado.errores.confirmPassword != null,
        supportingText = { estado.errores.confirmPassword?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { viewModel.validarFormulario() }),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Control de estado de carga requerido para simular conectividad
    if (estado.isLoading) {
        CircularProgressIndicator()
    } else {
        Button(
            onClick = { viewModel.validarFormulario() },
            enabled = isLoginEnabled,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("Iniciar Sesión", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(18.dp))
        }
    }

    // ========== DIVISOR ==========
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = GdBorder)
        Text(
            text = "  O CONTINÚA CON  ",
            style = MaterialTheme.typography.labelSmall,
            color = GdTextMuted,
            letterSpacing = 1.sp
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = GdBorder)
    }
    Spacer(modifier = Modifier.height(16.dp))

    // ========== GOOGLE Y APPLE (abren la ventana de cuenta) ==========
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        BotonSocial(
            texto = "Google",
            icono = R.drawable.ic_google,
            colorIcono = Color.Unspecified,
            habilitado = isLoginEnabled,
            modifier = Modifier.weight(1f),
            onClick = { proveedorActivo = "Google" }
        )
        BotonSocial(
            texto = "Apple",
            icono = R.drawable.ic_apple,
            colorIcono = MaterialTheme.colorScheme.onSurface,
            habilitado = isLoginEnabled,
            modifier = Modifier.weight(1f),
            onClick = { proveedorActivo = "Apple" }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Tus permisos se cargarán según tu rol",
        style = MaterialTheme.typography.labelSmall,
        color = GdTextMuted
    )

    // ========== VENTANA DE INICIO DE SESIÓN CON CUENTA ==========
    proveedorActivo?.let { proveedor ->
        val esGoogle = proveedor == "Google"
        DialogoCuentaSocial(
            proveedor = proveedor,
            icono = if (esGoogle) R.drawable.ic_google else R.drawable.ic_apple,
            colorIcono = if (esGoogle) Color.Unspecified else MaterialTheme.colorScheme.onSurface,
            onCancelar = { proveedorActivo = null },
            onContinuar = { correo ->
                proveedorActivo = null
                viewModel.loginSocial(correo)
            }
        )
    }
}

// Botón reutilizable para los proveedores de inicio de sesión
@Composable
private fun BotonSocial(
    texto: String,
    icono: Int,
    colorIcono: Color,
    habilitado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = habilitado,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, GdBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = GdSurfaceHigh,
            contentColor = GdTextPrimary
        ),
        modifier = modifier.height(52.dp)
    ) {
        Icon(
            painter = painterResource(id = icono),
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = GdSurfaceHigh,
    unfocusedContainerColor = GdSurfaceHigh,
    focusedBorderColor = GdPrimary,
    unfocusedBorderColor = GdBorder,
    focusedLeadingIconColor = GdPrimary,
    unfocusedLeadingIconColor = GdTextMuted
)

// Ventana emergente para ingresar la cuenta del proveedor (simulada)
@Composable
private fun DialogoCuentaSocial(
    proveedor: String,
    icono: Int,
    colorIcono: Color,
    onCancelar: () -> Unit,
    onContinuar: (String) -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf<String?>(null) }
    var errorClave by remember { mutableStateOf<String?>(null) }

    val etiquetaCorreo = if (proveedor == "Apple") "Apple ID (correo)" else "Correo de Google"

    AlertDialog(
        onDismissRequest = onCancelar,
        icon = {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = null,
                tint = colorIcono,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Iniciar sesión con $proveedor",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column {
                Text(
                    text = "Ingresa tu cuenta de $proveedor para continuar en Modo Guardián.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        errorCorreo = null
                    },
                    label = { Text(etiquetaCorreo) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    isError = errorCorreo != null,
                    supportingText = { errorCorreo?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = clave,
                    onValueChange = {
                        clave = it
                        errorClave = null
                    },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = errorClave != null,
                    supportingText = { errorClave?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    errorCorreo = when {
                        correo.isBlank() -> "Ingresa tu correo"
                        !correo.contains("@") -> "Formato de correo inválido"
                        else -> null
                    }
                    errorClave = when {
                        clave.isBlank() -> "Ingresa tu contraseña"
                        clave.length < 6 -> "Debe tener al menos 6 caracteres"
                        else -> null
                    }
                    if (errorCorreo == null && errorClave == null) {
                        onContinuar(correo.trim())
                    }
                }
            ) {
                Text("Continuar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}

'@

Write-Host "Paso 13/15: navigation/AppNavigation.kt"
Escribir 'app/src/main/java/com/example/insertcode/navigation/AppNavigation.kt' @'
package com.example.insertcode.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.insertcode.ui.theme.screen.CatalogoScreen
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
    data object Catalogo : Screen("catalogo")

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
            val estadoLogin by loginViewModel.estado.collectAsState()
            PerfilScreen(
                viewModel = perfilViewModel,
                navController = navController,
                email = estadoLogin.email,
                rol = estadoLogin.rol.orEmpty()
            )
        }

        // CATÁLOGO
        composable(Screen.Catalogo.route) {
            CatalogoScreen()
        }

        // NUEVAS PANTALLAS

        composable(Screen.Clientes.route) {
            ClientesScreen(navController)
        }

        composable(Screen.Pedidos.route) {
            PedidosScreen(navController)
        }

        composable(Screen.Entregas.route) {
            EntregasScreen(navController)
        }

        composable(Screen.Indicadores.route) {
            IndicadoresScreen(navController)
        }
    }
}
'@

Write-Host "Paso 14/15: viewmodel/LoginViewModel.kt"
Escribir 'app/src/main/java/com/example/insertcode/viewmodel/LoginViewModel.kt' @'
package com.example.insertcode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insertcode.model.LoginErrores
import com.example.insertcode.model.LoginUiState
import com.example.insertcode.navigation.NavigationEvent
import com.example.insertcode.navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    // 1. Estado de la UI utilizando LoginUiState
    private val _estado = MutableStateFlow(LoginUiState())
    val estado: StateFlow<LoginUiState> = _estado.asStateFlow()

    // 2. Canal de eventos de navegación reactiva requerido por la evaluación
    private val _eventosNavegacion = MutableSharedFlow<NavigationEvent>()
    val eventosNavegacion: SharedFlow<NavigationEvent> = _eventosNavegacion.asSharedFlow()

    // Actualiza el campo de correo electrónico y limpia su error
    fun onEmailChange(valor: String) {
        _estado.update {
            it.copy(email = valor, errores = it.errores.copy(email = null))
        }
    }

    // Actualiza el campo de contraseña y limpia sus errores (incluido el de confirmación)
    fun onPasswordChange(valor: String) {
        _estado.update {
            it.copy(
                password = valor,
                errores = it.errores.copy(password = null, confirmPassword = null)
            )
        }
    }

    // Actualiza el campo de confirmación de contraseña y limpia su error
    fun onConfirmPasswordChange(valor: String) {
        _estado.update {
            it.copy(confirmPassword = valor, errores = it.errores.copy(confirmPassword = null))
        }
    }

    // Funciones de navegación requeridas por el enunciado
    fun navigateTo(route: String) {
        viewModelScope.launch {
            _eventosNavegacion.emit(NavigationEvent.NavigateTo(route))
        }
    }

    // Al cerrar sesión se vuelve al login y se limpian correo y contraseñas
    fun navigateBack() {
        viewModelScope.launch {
            _eventosNavegacion.emit(NavigationEvent.NavigateBack)
            _estado.value = LoginUiState()
        }
    }

    // 3. Validación y Autenticación con usuarios de InsertCode
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value

        // Validaciones de formato iniciales
        val emailError = if (estadoActual.email.isBlank()) {
            "El correo es obligatorio"
        } else if (!estadoActual.email.contains("@")) {
            "Formato de correo inválido"
        } else null

        val passwordError = if (estadoActual.password.isBlank()) {
            "La contraseña es obligatoria"
        } else if (estadoActual.password.length < 6) {
            "Debe tener al menos 6 caracteres"
        } else null

        // Confirmación: no puede estar vacía y debe ser igual a la contraseña
        val confirmError = if (estadoActual.confirmPassword.isBlank()) {
            "Debes confirmar la contraseña"
        } else if (estadoActual.confirmPassword != estadoActual.password) {
            "Las contraseñas no coinciden"
        } else null

        if (emailError != null || passwordError != null || confirmError != null) {
            _estado.update {
                it.copy(
                    errores = LoginErrores(
                        email = emailError,
                        password = passwordError,
                        confirmPassword = confirmError
                    )
                )
            }
            return false
        }

        // Simulación de carga (isLoading) ante errores de datos o conectividad
        _estado.update { it.copy(isLoading = true) }

        // Credenciales de prueba para el MVP de InsertCode
        val emailInput = estadoActual.email.trim()
        val passwordInput = estadoActual.password

        when {
            emailInput == "admin@insertcode.cl" && passwordInput == "123456" -> {
                _estado.update { it.copy(isLoading = false, rol = "Admin") }
                navigateTo(Screen.HomeAdmin.route)
            }
            emailInput == "supervisor@insertcode.cl" && passwordInput == "123456" -> {
                _estado.update { it.copy(isLoading = false, rol = "Supervisor") }
                navigateTo(Screen.HomeSupervisor.route)
            }
            emailInput == "operador@insertcode.cl" && passwordInput == "123456" -> {
                _estado.update { it.copy(isLoading = false, rol = "Operador") }
                navigateTo(Screen.HomeOperator.route)
            }
            else -> {
                _estado.update {
                    it.copy(
                        isLoading = false,
                        errores = LoginErrores(
                            email = "Credenciales incorrectas o usuario no registrado",
                            password = "Verifique su contraseña"
                        )
                    )
                }
                return false
            }
        }
        return true
    }
    // Inicio de sesión social (simulado, igual que el login con usuarios de prueba)

    // Inicio de sesión social (simulado): recibe el correo ingresado en la ventana
    fun loginSocial(correo: String) {
        viewModelScope.launch {
            _estado.update { it.copy(isLoading = true, errores = LoginErrores()) }
            delay(800) // simula la conexión con el proveedor
            _estado.update { it.copy(isLoading = false, email = correo, rol = "Operador") }
            _eventosNavegacion.emit(NavigationEvent.NavigateTo(Screen.HomeOperator.route))
        }
    }

    // Cierra sesión: limpia correo y contraseñas
    fun cerrarSesion() {
        _estado.value = LoginUiState()
    }

    fun irAPantallaEspecial() {
        viewModelScope.launch {
            _eventosNavegacion.emit(NavigationEvent.NavigateTo(Screen.AnimacionesEstados.route))
        }
    }
}
'@

Write-Host "Paso 15/15: MainActivity.kt"
Escribir 'app/src/main/java/com/example/insertcode/MainActivity.kt' @'
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
'@

Write-Host ""
Write-Host "Listo. Ahora compila con: .\gradlew assembleDebug" -ForegroundColor Cyan