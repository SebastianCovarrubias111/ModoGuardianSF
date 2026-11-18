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