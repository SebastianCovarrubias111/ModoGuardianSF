package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IndicadoresScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            "Indicadores",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        IndicadorCard("Ventas Totales", "$12.500.000")
        IndicadorCard("Pedidos", "120")
        IndicadorCard("Entregas Realizadas", "95")
        IndicadorCard("Pendientes", "25")
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