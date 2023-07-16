package main.ar.com.codoacodo.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    public static Connection create() throws SQLException {
        Connection connection;
        try {
            Driver driver = (Driver) Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(driver);
            final String url = "jdbc:mysql://localhost:3306/inmuebles?";
            final String user = "cac";
            final String password = "c0m1510N_#23061";
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
