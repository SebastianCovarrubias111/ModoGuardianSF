package com.example.insertcode.model


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val rol: String? = null,
    val errores: LoginErrores = LoginErrores()
)

data class LoginErrores(
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null
)

