import java.sql.*;
public class conector {
    static final String URL = "jdbc:mysql://127.0.0.1:3306/minimarket";
    static final String USER= "root";
    static final String PASSWORD= "Cris0112*";
    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            // Registro del driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error en la conexión a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }
    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión.");
                e.printStackTrace();
            }
        }
    }
}
