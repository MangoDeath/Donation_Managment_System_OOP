package config;

import java.sql.*;

public class DB {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/pupupu";
        String user = "postgres";
        String pass = "1234";
        return DriverManager.getConnection(url, user, pass);
    }
}

