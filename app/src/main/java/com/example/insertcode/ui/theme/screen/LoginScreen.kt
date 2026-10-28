package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.insertcode.R
import com.example.insertcode.model.LoginUiState
import com.example.insertcode.ui.theme.GuardianGradientBottom
import com.example.insertcode.ui.theme.GuardianGradientTop
import com.example.insertcode.ui.utils.WindowSizeClass
import com.example.insertcode.ui.utils.rememberWindowSizeClass
import com.example.insertcode.viewmodel.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {

    val windowSize = rememberWindowSizeClass()
    val estado by viewModel.estado.collectAsState()
    val isLoginEnabled = !estado.isLoading
    val margenHorizontal = if (windowSize == WindowSizeClass.COMPACT) 20.dp else 48.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(GuardianGradientTop, GuardianGradientBottom))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = margenHorizontal, vertical = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---------- Encabezado ----------
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Modo Guardián",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Inicia sesión para continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(28.dp))

            // ---------- Tarjeta con el formulario ----------
            Card(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginFormContent(
                        estado = estado,
                        viewModel = viewModel,
                        isLoginEnabled = isLoginEnabled
                    )
                }
            }
        }
    }
}

@Composable
fun LoginFormContent(
    estado: LoginUiState,
    viewModel: LoginViewModel,
    isLoginEnabled: Boolean
) {
    var verPassword by remember { mutableStateOf(false) }
    // null = sin ventana; "Google" o "Apple" = ventana de cuenta abierta
    var proveedorActivo by remember { mutableStateOf<String?>(null) }

    val transformacion =
        if (verPassword) VisualTransformation.None else PasswordVisualTransformation()
    val formaCampo = RoundedCornerShape(14.dp)

    // ========== CAMPO EMAIL ==========
    OutlinedTextField(
        value = estado.email,
        onValueChange = viewModel::onEmailChange,
        label = { Text("Correo electrónico") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        singleLine = true,
        shape = formaCampo,
        isError = estado.errores.email != null,
        supportingText = { estado.errores.email?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // ========== CAMPO CONTRASEÑA ==========
    OutlinedTextField(
        value = estado.password,
        onValueChange = viewModel::onPasswordChange,
        label = { Text("Contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            TextButton(onClick = { verPassword = !verPassword }) {
                Text(if (verPassword) "Ocultar" else "Ver")
            }
        },
        singleLine = true,
        shape = formaCampo,
        visualTransformation = transformacion,
        isError = estado.errores.password != null,
        supportingText = { estado.errores.password?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    // ========== CAMPO CONFIRMAR CONTRASEÑA ==========
    OutlinedTextField(
        value = estado.confirmPassword,
        onValueChange = viewModel::onConfirmPasswordChange,
        label = { Text("Confirmar contraseña") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        singleLine = true,
        shape = formaCampo,
        visualTransformation = transformacion,
        isError = estado.errores.confirmPassword != null,
        supportingText = { estado.errores.confirmPassword?.let { Text(it) } },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { viewModel.validarFormulario() }),
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Control de estado de carga requerido para simular conectividad
    if (estado.isLoading) {
        CircularProgressIndicator()
    } else {
        Button(
            onClick = { viewModel.validarFormulario() },
            enabled = isLoginEnabled,
            shape = formaCampo,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Iniciar Sesión", fontWeight = FontWeight.SemiBold)
        }
    }

    // ========== DIVISOR ==========
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))
        Text(
            text = "  o continúa con  ",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
    Spacer(modifier = Modifier.height(20.dp))

    // ========== BOTONES GOOGLE Y APPLE (abren la ventana de cuenta) ==========
    BotonSocial(
        texto = "Continuar con Google",
        icono = R.drawable.ic_google,
        colorIcono = Color.Unspecified,
        habilitado = isLoginEnabled,
        onClick = { proveedorActivo = "Google" }
    )

    Spacer(modifier = Modifier.height(10.dp))

    BotonSocial(
        texto = "Continuar con Apple",
        icono = R.drawable.ic_apple,
        colorIcono = MaterialTheme.colorScheme.onSurface,
        habilitado = isLoginEnabled,
        onClick = { proveedorActivo = "Apple" }
    )

    // ========== VENTANA DE INICIO DE SESIÓN CON CUENTA ==========
    proveedorActivo?.let { proveedor ->
        val esGoogle = proveedor == "Google"
        DialogoCuentaSocial(
            proveedor = proveedor,
            icono = if (esGoogle) R.drawable.ic_google else R.drawable.ic_apple,
            colorIcono = if (esGoogle) Color.Unspecified else MaterialTheme.colorScheme.onSurface,
            onCancelar = { proveedorActivo = null },
            onContinuar = { correo ->
                proveedorActivo = null
                viewModel.loginSocial(correo)
            }
        )
    }
}

// Botón reutilizable para los proveedores de inicio de sesión
@Composable
private fun BotonSocial(
    texto: String,
    icono: Int,
    colorIcono: Color,
    habilitado: Boolean,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = habilitado,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Icon(
            painter = painterResource(id = icono),
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(texto, fontWeight = FontWeight.Medium)
    }
}

// Ventana emergente para ingresar la cuenta del proveedor (simulada)
@Composable
private fun DialogoCuentaSocial(
    proveedor: String,
    icono: Int,
    colorIcono: Color,
    onCancelar: () -> Unit,
    onContinuar: (String) -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf<String?>(null) }
    var errorClave by remember { mutableStateOf<String?>(null) }

    val etiquetaCorreo = if (proveedor == "Apple") "Apple ID (correo)" else "Correo de Google"

    AlertDialog(
        onDismissRequest = onCancelar,
        icon = {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = null,
                tint = colorIcono,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Iniciar sesión con $proveedor",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column {
                Text(
                    text = "Ingresa tu cuenta de $proveedor para continuar en Modo Guardián.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        errorCorreo = null
                    },
                    label = { Text(etiquetaCorreo) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    isError = errorCorreo != null,
                    supportingText = { errorCorreo?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = clave,
                    onValueChange = {
                        clave = it
                        errorClave = null
                    },
                    label = { Text("Contraseña") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    isError = errorClave != null,
                    supportingText = { errorClave?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    errorCorreo = when {
                        correo.isBlank() -> "Ingresa tu correo"
                        !correo.contains("@") -> "Formato de correo inválido"
                        else -> null
                    }
                    errorClave = when {
                        clave.isBlank() -> "Ingresa tu contraseña"
                        clave.length < 6 -> "Debe tener al menos 6 caracteres"
                        else -> null
                    }
                    if (errorCorreo == null && errorClave == null) {
                        onContinuar(correo.trim())
                    }
                }
            ) {
                Text("Continuar")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text("Cancelar")
            }
        }
    )
}

