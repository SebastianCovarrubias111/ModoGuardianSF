package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.insertcode.ui.theme.GdBackground
import com.example.insertcode.ui.theme.GdBackgroundAlt
import com.example.insertcode.ui.theme.GdBorder
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSurface
import com.example.insertcode.ui.theme.GdSurfaceHigh
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
import com.example.insertcode.viewmodel.RegistroCuentaViewModel

@Composable
fun CrearCuentaScreen(
    onVolver: () -> Unit,
    viewModel: RegistroCuentaViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()
    val forma = RoundedCornerShape(14.dp)
    val colores = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = GdSurfaceHigh,
        unfocusedContainerColor = GdSurfaceHigh,
        focusedBorderColor = GdPrimary,
        unfocusedBorderColor = GdBorder,
        focusedLeadingIconColor = GdPrimary,
        unfocusedLeadingIconColor = GdTextMuted
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(GdBackground, GdBackgroundAlt)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.widthIn(max = 480.dp).fillMaxWidth()) {

                TextButton(onClick = onVolver) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("Volver al inicio de sesión")
                }

                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Crea tu cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = GdTextPrimary
                )
                Text(
                    text = "Regístrate para acceder a Modo Guardián. Si usas un correo @gmail.com también podrás entrar con el botón de Google.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = GdTextMuted
                )
                Spacer(Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = GdSurface),
                    border = BorderStroke(1.dp, GdBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        OutlinedTextField(
                            value = estado.nombre,
                            onValueChange = viewModel::onNombreChange,
                            label = { Text("Nombre completo") },
                            leadingIcon = { Icon(Icons.Default.Person, null) },
                            singleLine = true,
                            shape = forma,
                            colors = colores,
                            isError = estado.errorNombre != null,
                            supportingText = { estado.errorNombre?.let { Text(it) } },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = estado.correo,
                            onValueChange = viewModel::onCorreoChange,
                            label = { Text("Correo electrónico") },
                            leadingIcon = { Icon(Icons.Default.Email, null) },
                            singleLine = true,
                            shape = forma,
                            colors = colores,
                            isError = estado.errorCorreo != null,
                            supportingText = { estado.errorCorreo?.let { Text(it) } },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = estado.clave,
                            onValueChange = viewModel::onClaveChange,
                            label = { Text("Contraseña") },
                            leadingIcon = { Icon(Icons.Default.Lock, null) },
                            singleLine = true,
                            shape = forma,
                            colors = colores,
                            visualTransformation = PasswordVisualTransformation(),
                            isError = estado.errorClave != null,
                            supportingText = { estado.errorClave?.let { Text(it) } },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Next
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(8.dp))

                        OutlinedTextField(
                            value = estado.confirmarClave,
                            onValueChange = viewModel::onConfirmarChange,
                            label = { Text("Confirmar contraseña") },
                            leadingIcon = { Icon(Icons.Default.Lock, null) },
                            singleLine = true,
                            shape = forma,
                            colors = colores,
                            visualTransformation = PasswordVisualTransformation(),
                            isError = estado.errorConfirmar != null,
                            supportingText = { estado.errorConfirmar?.let { Text(it) } },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(Modifier.height(20.dp))

                        Button(
                            onClick = { viewModel.crearCuenta() },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                        ) {
                            Text("Crear cuenta", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }

    if (estado.cuentaCreada) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Cuenta creada") },
            text = {
                Text("Ya puedes iniciar sesión con ${estado.correo.trim()}. Tu rol es Operador.")
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.limpiar()
                    onVolver()
                }) {
                    Text("Ir a iniciar sesión")
                }
            }
        )
    }
}