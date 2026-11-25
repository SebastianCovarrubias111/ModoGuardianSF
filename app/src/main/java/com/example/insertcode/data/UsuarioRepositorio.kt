package com.example.insertcode.data

data class Usuario(
    val nombre: String,
    val correo: String,
    val clave: String,
    val rol: String
)

object UsuarioRepositorio {

    // ===== USUARIOS POR DEFECTO PARA PROBAR EL SISTEMA =====
    val usuariosPorDefecto: List<Usuario> = listOf(
        Usuario("Administrador", "admin@insertcode.cl", "123456", "Admin"),
        Usuario("Supervisor", "supervisor@insertcode.cl", "123456", "Supervisor"),
        Usuario("Operador", "operador@insertcode.cl", "123456", "Operador"),
        Usuario("Operador Google", "operador.google@gmail.com", "123456", "Operador")
    )

    // Lista en memoria: incluye los usuarios por defecto y los que se registren en la app
    private val usuarios: MutableList<Usuario> = usuariosPorDefecto.toMutableList()

    fun buscarPorCorreo(correo: String): Usuario? =
        usuarios.firstOrNull { it.correo.equals(correo.trim(), ignoreCase = true) }

    // Devuelve el usuario solo si el correo existe y la clave coincide
    fun autenticar(correo: String, clave: String): Usuario? =
        buscarPorCorreo(correo)?.takeIf { it.clave == clave }

    // Registra un usuario nuevo con rol Operador. Devuelve false si el correo ya existe
    fun registrar(nombre: String, correo: String, clave: String): Boolean {
        if (buscarPorCorreo(correo) != null) return false
        usuarios.add(Usuario(nombre.trim(), correo.trim(), clave, "Operador"))
        return true
    }

    // Una cuenta de Google debe terminar en @gmail.com (o @googlemail.com)
    fun esCuentaGoogle(correo: String): Boolean {
        val c = correo.trim().lowercase()
        return c.endsWith("@gmail.com") || c.endsWith("@googlemail.com")
    }
}