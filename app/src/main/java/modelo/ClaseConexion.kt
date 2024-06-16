package modelo
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {
    fun cadenaConexion(): Connection? {
        try {
            val url = "jdbc:oracle:thin:@192.168.0.19:1521:xe"
            val usuario = "system"
            val contrasena = "Bimbimcito135@"

            val connection = DriverManager.getConnection(url, usuario, contrasena)
            return connection
        } catch (e: Exception) {
            println("Error: $e")
            return null
        }
    }
}