package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Entrega(
    val id: String,
    val destino: String,
    val estado: String
)

@Composable
fun EntregasScreen() {

    val entregas = listOf(
        Entrega("001", "Santiago Centro", "En Ruta"),
        Entrega("002", "Maipú", "Pendiente"),
        Entrega("003", "Providencia", "Entregado")
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

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {

            items(entregas) { entrega ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("Entrega #${entrega.id}")
                        Text("Destino: ${entrega.destino}")
                        Text("Estado: ${entrega.estado}")
                    }
                }
            }
        }
    }
}