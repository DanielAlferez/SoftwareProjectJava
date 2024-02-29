package com.mycompany.poo.factories;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author danal
 */
public class ConnectionDatabase {
    private static final String JDBC_URL = "jdbc:h2:./H2DB/bdh2";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

 // Método para establecer la conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}
