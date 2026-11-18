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