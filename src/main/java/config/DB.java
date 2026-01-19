package config;

import java.sql.*;

public class DB {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String pass = "nako9986"; //
        return DriverManager.getConnection(url, user, pass);
    }
}

