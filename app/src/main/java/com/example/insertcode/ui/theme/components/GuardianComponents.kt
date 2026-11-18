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