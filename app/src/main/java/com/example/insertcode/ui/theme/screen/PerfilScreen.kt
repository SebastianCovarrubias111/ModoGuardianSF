package com.example.insertcode.ui.theme.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.ImagenInteligente
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.ui.theme.components.EstadoChip
import com.example.insertcode.ui.theme.components.GuardianCard
import com.example.insertcode.ui.theme.components.SeccionTitulo
import com.example.insertcode.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    navController: NavHostController,
    email: String,
    rol: String,
    modifier: Modifier = Modifier
) {

    val imagenUri by viewModel.imagenUri.collectAsState()

    val launcherGaleria = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.actualizarImagen(uri)
    }

    val launcherCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito) {
            viewModel.confirmarFotoCamara()
        }
    }

    val permisos = when (rol) {
        "Admin" -> listOf(
            "Gestión de clientes y catálogo",
            "Registro y cotización de pedidos",
            "Consulta de entregas y rutas",
            "Acceso a indicadores"
        )
        "Supervisor" -> listOf(
            "Seguimiento de pedidos",
            "Consulta de entregas y rutas",
            "Acceso a indicadores"
        )
        else -> listOf(
            "Consulta de entregas asignadas",
            "Actualización de estado de despacho"
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        SeccionTitulo("Mi perfil", "Datos de la cuenta, foto y permisos asignados")

        GuardianCard(Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImagenInteligente(imagenUri = imagenUri)
                Spacer(Modifier.height(12.dp))
                Text(
                    email.ifBlank { "Sin correo" },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = GdTextPrimary
                )
                Spacer(Modifier.height(6.dp))
                EstadoChip(rol.uppercase().ifBlank { "SIN ROL" }, GdPrimary)
                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(
                        onClick = { launcherGaleria.launch("image/*") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PhotoLibrary, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Galería")
                    }
                    OutlinedButton(
                        onClick = {
                            val uriSeguro = viewModel.crearUriTemporal()
                            launcherCamara.launch(uriSeguro)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.PhotoCamera, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Cámara")
                    }
                }
            }
        }

        GuardianCard(Modifier.fillMaxWidth()) {
            Text(
                "Permisos del perfil ($rol)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = GdTextPrimary
            )
            permisos.forEach { permiso ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        permiso,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium,
                        color = GdTextPrimary
                    )
                    EstadoChip("ACTIVO", GdSuccess)
                }
            }
        }
    }
}