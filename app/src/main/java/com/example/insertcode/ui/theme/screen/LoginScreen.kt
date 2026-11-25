package com.example.insertcode.ui.theme.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import androidx.compose.ui.unit.sp
import com.example.insertcode.R
import com.example.insertcode.data.UsuarioRepositorio
import com.example.insertcode.model.LoginUiState
import com.example.insertcode.navigation.Screen
import com.example.insertcode.ui.theme.GdBackground
import com.example.insertcode.ui.theme.GdBackgroundAlt
import com.example.insertcode.ui.theme.GdBorder
import com.example.insertcode.ui.theme.GdPrimary
import com.example.insertcode.ui.theme.GdSuccess
import com.example.insertcode.ui.theme.GdSurface
import com.example.insertcode.ui.theme.GdSurfaceHigh
import com.example.insertcode.ui.theme.GdTextMuted
import com.example.insertcode.ui.theme.GdTextPrimary
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
            .background(Brush.verticalGradient(listOf(GdBackground, GdBackgroundAlt)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = margenHorizontal, vertical = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(Modifier.widthIn(max = 480.dp).fillMaxWidth()) {

                // Chip de acceso seguro
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(GdSuccess.copy(alpha = 0.14f))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Lock, null, tint = GdSuccess, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        "Acceso seguro",
                        style = MaterialTheme.typography.labelMedium,
                        color = GdSuccess,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Bienvenido de nuevo",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = GdTextPrimary
                )
                Text(
                    text = "Accede a tu turno de supervisión",
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
                    Column(
                        modifier = Modifier.padding(20.dp),
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
}

@Composable
fun LoginFormContent(
    estado: LoginUiState,
    viewModel: LoginViewModel,
    isLoginEnabled: Boolean
) {
    var verPassword by remember { mutableStateOf(false) }
    var mostrarGoogle by remember { mutableStateOf(false) }
    var mostrarUsuarios by remember { mutableStateOf(false) }

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
        colors = coloresCampo(),
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
        colors = coloresCampo(),
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
        colors = coloresCampo(),
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
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
        ) {
            Text("Iniciar Sesión", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.width(8.dp))
            Icon(Icons.AutoMirrored.Filled.ArrowForward, null, modifier = Modifier.size(18.dp))
        }
    }

    // ========== DIVISOR ==========
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = GdBorder)
        Text(
            text = "  O CONTINÚA CON  ",
            style = MaterialTheme.typography.labelSmall,
            color = GdTextMuted,
            letterSpacing = 1.sp
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = GdBorder)
    }
    Spacer(modifier = Modifier.height(16.dp))

    // ========== GOOGLE (abre la ventana de cuenta con validación) ==========
    BotonSocial(
        texto = "Google",
        icono = R.drawable.ic_google,
        colorIcono = Color.Unspecified,
        habilitado = isLoginEnabled,
        modifier = Modifier.fillMaxWidth(),
        onClick = { mostrarGoogle = true }
    )

    // ========== CREAR CUENTA ==========
    Spacer(modifier = Modifier.height(12.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "¿No tienes cuenta?",
            style = MaterialTheme.typography.bodyMedium,
            color = GdTextMuted
        )
        TextButton(onClick = { viewModel.navigateTo(Screen.CrearCuenta.route) }) {
            Text("Crear cuenta", fontWeight = FontWeight.Bold)
        }
    }

    // ========== USUARIOS DE PRUEBA ==========
    TextButton(onClick = { mostrarUsuarios = true }) {
        Text(
            "Ver usuarios de prueba",
            style = MaterialTheme.typography.labelMedium,
            color = GdTextMuted
        )
    }

    Text(
        text = "Tus permisos se cargarán según tu rol",
        style = MaterialTheme.typography.labelSmall,
        color = GdTextMuted
    )

    // ========== VENTANA DE GOOGLE ==========
    if (mostrarGoogle) {
        DialogoCuentaGoogle(
            onCancelar = { mostrarGoogle = false },
            validarCorreo = viewModel::errorCorreoGoogle,
            validarClave = viewModel::errorClaveGoogle,
            onContinuar = { correo ->
                mostrarGoogle = false
                viewModel.loginSocial(correo)
            }
        )
    }

    // ========== VENTANA DE USUARIOS DE PRUEBA ==========
    if (mostrarUsuarios) {
        AlertDialog(
            onDismissRequest = { mostrarUsuarios = false },
            title = { Text("Usuarios de prueba") },
            text = {
                Column {
                    UsuarioRepositorio.usuariosPorDefecto.forEach { u ->
                        Text(u.rol, fontWeight = FontWeight.Bold)
                        Text("Correo: ${u.correo}", style = MaterialTheme.typography.bodySmall)
                        Text("Contraseña: ${u.clave}", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(10.dp))
                    }
                    Text(
                        "En \"Confirmar contraseña\" escribe la misma clave. La cuenta @gmail.com sirve para el botón de Google.",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { mostrarUsuarios = false }) { Text("Cerrar") }
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = habilitado,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, GdBorder),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = GdSurfaceHigh,
            contentColor = GdTextPrimary
        ),
        modifier = modifier.height(52.dp)
    ) {
        Icon(
            painter = painterResource(id = icono),
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(texto, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = GdSurfaceHigh,
    unfocusedContainerColor = GdSurfaceHigh,
    focusedBorderColor = GdPrimary,
    unfocusedBorderColor = GdBorder,
    focusedLeadingIconColor = GdPrimary,
    unfocusedLeadingIconColor = GdTextMuted
)

// Ventana emergente de Google: solo deja entrar a cuentas @gmail.com registradas
@Composable
private fun DialogoCuentaGoogle(
    onCancelar: () -> Unit,
    validarCorreo: (String) -> String?,
    validarClave: (String, String) -> String?,
    onContinuar: (String) -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var errorCorreo by remember { mutableStateOf<String?>(null) }
    var errorClave by remember { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onCancelar,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Iniciar sesión con Google",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column {
                Text(
                    text = "Ingresa tu cuenta de Google registrada para continuar en Modo Guardián.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        errorCorreo = null
                    },
                    label = { Text("Correo de Google") },
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
                        else -> validarCorreo(correo.trim())
                    }
                    errorClave = when {
                        clave.isBlank() -> "Ingresa tu contraseña"
                        clave.length < 6 -> "Debe tener al menos 6 caracteres"
                        errorCorreo == null -> validarClave(correo.trim(), clave)
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