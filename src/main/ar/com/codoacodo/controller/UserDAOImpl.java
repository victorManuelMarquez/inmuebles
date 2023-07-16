package main.ar.com.codoacodo.controller;

import main.ar.com.codoacodo.db.DBConnection;
import main.ar.com.codoacodo.model.User;

import java.sql.*;

public class UserDAOImpl implements UserDAO {

    @Override
    public User getById(Integer id) throws SQLException {
        User profile = null;
        String query = "SELECT * FROM usuarios WHERE id = ?;";
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    profile = toUser(resultSet);
            }
        }
        return profile;
    }

    @Override
    public int create(User element) throws SQLException {
        if (element.getEmail().isBlank() || element.getPassword().isBlank())
            throw new RuntimeException("Campo/s en blanco detectado/s.");
        else if (element.getDate().after(new Timestamp(System.currentTimeMillis())))
            throw new RuntimeException("No se permite agregar en futuro.");
        String query = "INSERT INTO usuarios(email, clave, alta) VALUES (?,?,?);";
        int id = 0;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, element.getEmail());
            statement.setString(2, element.getPassword());
            statement.setTimestamp(3, element.getDate());
            boolean ignored = statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
        }
        return id;
    }

    @Override
    public int update(User element) throws SQLException {
        if (element.getPassword().isBlank())
            throw new RuntimeException("Contraseña en blanco.");
        else if (element.getDate().after(new Timestamp(System.currentTimeMillis())))
            throw new RuntimeException("No se permite actualizar en futuro.");
        String query = "UPDATE usuarios SET clave = ?, alta = ?, activo = ? WHERE id = ?;";
        int rowsAffected;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, element.getPassword());
            statement.setTimestamp(2, element.getDate());
            statement.setBoolean(3, element.isActive());
            statement.setInt(4, element.getId());
            rowsAffected = statement.executeUpdate();
        }
        return rowsAffected;
    }

    @Override
    public int delete(User element) throws SQLException {
        if (element.isActive())
            throw new RuntimeException("El usuario no está deshabilitado correctamente.");
        String query = "UPDATE usuarios SET activo = ? WHERE id = ?;";
        int rowsAffected;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setBoolean(1, element.isActive());
            statement.setInt(2, element.getId());
            rowsAffected = statement.executeUpdate();
        }
        return rowsAffected;
    }

    @Override
    public User register(String email, String password) throws SQLException {
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setDate(new Timestamp(System.currentTimeMillis()));
        newUser.setActive(true);
        newUser.setId(create(newUser));
        return newUser;
    }

    @Override
    public User verify(String email, String password) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE email = ? AND clave = ?;";
        User user = null;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = toUser(resultSet);
                }
            }
        }
        if (user == null) {
            String anotherQuery = "SELECT * FROM usuarios WHERE email = '" + email + "';";
            try (
                    Connection connection = DBConnection.create();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(anotherQuery)
            ) {
                if (resultSet.next()) {
                    user = toUser(resultSet);
                    user.setPassword("");
                }
            }
        }
        return user;
    }

    @Override
    public int changePassword(Integer id, String newPassword) throws SQLException {
        if (newPassword.isBlank())
            throw new RuntimeException("Contraseña nueva en blanco.");
        String query = "UPDATE usuarios SET clave = ? WHERE id = ?;";
        int rowsAffected;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, newPassword);
            statement.setInt(2, id);
            rowsAffected = statement.executeUpdate();
        }
        return rowsAffected;
    }

    private User toUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("clave"));
        user.setDate(resultSet.getTimestamp("alta"));
        user.setActive(resultSet.getBoolean("activo"));
        return user;
    }

}
