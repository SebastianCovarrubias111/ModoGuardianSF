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