// DatabaseConnection.java
package br.com.ecommerce.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ecommerce_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                //System.out.println("Conectado ao PostgreSQL com sucesso!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexao fechada!");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexao: " + e.getMessage());
            }
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}