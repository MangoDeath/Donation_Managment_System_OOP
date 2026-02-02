package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DB {
    private static final DB INSTANCE = new DB();
    private static final String URL = "jdbc:postgresql://localhost:5432/dbname";
    private static final String USER = "username";
    private static final String PASS = "password";

    private DB() {
    }
    public static DB getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

