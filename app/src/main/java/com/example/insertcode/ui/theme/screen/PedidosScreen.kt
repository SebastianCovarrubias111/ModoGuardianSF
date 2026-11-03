package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class Pedido(
    val codigo: String,
    val cliente: String,
    val estado: String
)

@Composable
fun PedidosScreen() {

    val pedidos = listOf(
        Pedido("001", "Ferretería Norte", "Pendiente"),
        Pedido("002", "Retail Santiago", "En Ruta"),
        Pedido("003", "Constructora Andes", "Entregado")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Pedidos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(pedidos) { pedido ->

                val colorEstado = when (pedido.estado) {
                    "Entregado" -> Color(0xFF4CAF50)
                    "En Ruta" -> Color(0xFFFF9800)
                    else -> Color.Red
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("Pedido #${pedido.codigo}")

                        Text("Cliente: ${pedido.cliente}")

                        Text(
                            "Estado: ${pedido.estado}",
                            color = colorEstado
                        )
                    }
                }
            }
        }
    }
}