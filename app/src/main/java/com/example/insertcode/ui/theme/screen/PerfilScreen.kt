package com.example.insertcode.ui.theme.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.insertcode.ui.ImagenInteligente
import com.example.insertcode.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel,
    navController: NavHostController,
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Perfil de Usuario",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text("← Volver")
        }

        Spacer(modifier = Modifier.height(32.dp))

        ImagenInteligente(
            imagenUri = imagenUri
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                launcherGaleria.launch("image/*")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar desde Galería")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                val uriSeguro = viewModel.crearUriTemporal()
                launcherCamara.launch(uriSeguro)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar Foto con Cámara")
        }
    }
}