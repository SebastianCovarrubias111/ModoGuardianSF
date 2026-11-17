package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdPrimarySoft
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.SeccionTitulo

@Composable
fun ClientesScreen(navController: NavHostController) {

    val clientes = listOf(
        "Ferretería Norte",
        "Retail Santiago",
        "Distribuidora Sur",
        "Constructora Andes",
        "Comercial Central"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SeccionTitulo("Cartera de Clientes", "Cuentas activas asignadas a tu equipo") }

        items(clientes) { cliente ->
            GuardianCard(Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(GdPrimarySoft),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(cliente.first().toString(), color = GdPrimary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(cliente, fontWeight = FontWeight.Bold, color = GdTextPrimary)
                        Text(
                            "Cliente activo",
                            style = MaterialTheme.typography.bodySmall,
                            color = GdTextMuted
                        )
                    }
                    EstadoChip("ACTIVO", GdSuccess)
                }
            }
        }
    }
}