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
        Entrega(
            "001",
            "Santiago Centro",
            "En Ruta",
            "Juan Pérez",
            "15/11/2026"
        ),
        Entrega(
            "002",
            "Maipú",
            "Pendiente",
            "María González",
            "16/11/2026"
        ),
        Entrega(
            "003",
            "Providencia",
            "Entregado",
            "Carlos Soto",
            "17/11/2026"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Entregas",
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

            items(entregas) { entrega ->

                val colorEstado = when (entrega.estado) {
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
                            text = "Entrega #${entrega.id}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text("Destino: ${entrega.destino}")

                        Text("Fecha: ${entrega.fecha}")

                        Text("Repartidor: ${entrega.repartidor}")

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Estado: ${entrega.estado}",
                            color = colorEstado,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}