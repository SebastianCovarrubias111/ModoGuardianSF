package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

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
        Pedido(
            "001",
            "Ferretería Norte",
            "Pendiente",
            "Alta",
            "15/11/2026"
        ),
        Pedido(
            "002",
            "Retail Santiago",
            "En Ruta",
            "Media",
            "16/11/2026"
        ),
        Pedido(
            "003",
            "Constructora Andes",
            "Entregado",
            "Baja",
            "17/11/2026"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Pedidos",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("← Volver")
        }

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
                        .padding(vertical = 6.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Pedido #${pedido.codigo}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("Cliente: ${pedido.cliente}")

                        Text("Fecha: ${pedido.fecha}")

                        Text("Prioridad: ${pedido.prioridad}")

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Estado: ${pedido.estado}",
                            color = colorEstado,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}