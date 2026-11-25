package com.example.insertcode.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.insertcode.data.UsuarioRepositorio
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CuentaUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val confirmarClave: String = "",
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorClave: String? = null,
    val errorConfirmar: String? = null,
    val cuentaCreada: Boolean = false
)

class RegistroCuentaViewModel : ViewModel() {

    private val _estado = MutableStateFlow(CuentaUiState())
    val estado: StateFlow<CuentaUiState> = _estado.asStateFlow()

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errorNombre = null) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errorCorreo = null) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errorClave = null, errorConfirmar = null) }
    }

    fun onConfirmarChange(valor: String) {
        _estado.update { it.copy(confirmarClave = valor, errorConfirmar = null) }
    }

    fun crearCuenta() {
        val e = _estado.value

        val errorNombre = if (e.nombre.isBlank()) "El nombre es obligatorio" else null

        val errorCorreo = when {
            e.correo.isBlank() -> "El correo es obligatorio"
            !Patterns.EMAIL_ADDRESS.matcher(e.correo.trim()).matches() -> "Formato de correo inválido"
            UsuarioRepositorio.buscarPorCorreo(e.correo) != null -> "Este correo ya está registrado"
            else -> null
        }

        val errorClave = when {
            e.clave.isBlank() -> "La contraseña es obligatoria"
            e.clave.length < 6 -> "Debe tener al menos 6 caracteres"
            else -> null
        }

        val errorConfirmar = when {
            e.confirmarClave.isBlank() -> "Debes confirmar la contraseña"
            e.confirmarClave != e.clave -> "Las contraseñas no coinciden"
            else -> null
        }

        if (errorNombre != null || errorCorreo != null || errorClave != null || errorConfirmar != null) {
            _estado.update {
                it.copy(
                    errorNombre = errorNombre,
                    errorCorreo = errorCorreo,
                    errorClave = errorClave,
                    errorConfirmar = errorConfirmar
                )
            }
            return
        }

        UsuarioRepositorio.registrar(e.nombre, e.correo, e.clave)
        _estado.update { it.copy(cuentaCreada = true) }
    }

    fun limpiar() {
        _estado.value = CuentaUiState()
    }
}