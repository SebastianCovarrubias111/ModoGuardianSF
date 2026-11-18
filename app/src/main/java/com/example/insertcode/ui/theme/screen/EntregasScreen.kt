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