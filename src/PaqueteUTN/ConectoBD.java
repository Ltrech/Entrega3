package PaqueteUTN;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectoBD {
	
	private static Connection conexion = null;
    private static String nombreDB = "bd_entrega_tp";
    private static String host = "localhost";
    private static String puerto = "3306";
    private static String usuarioDB = "root";
    private static String contraseniaDB = "password";

    public static Connection getConexion() throws SQLException {
        if(conexion == null) {
            conexion = DriverManager.getConnection("jdbc:mysql://"+host+":"+puerto+"/" + nombreDB, usuarioDB, contraseniaDB);
        }

        return conexion;
    }

    public static void cerrarConexion() throws SQLException {
        if( conexion != null && !conexion.isClosed()) {
            conexion.close();
            conexion = null;
        }
    }

}
