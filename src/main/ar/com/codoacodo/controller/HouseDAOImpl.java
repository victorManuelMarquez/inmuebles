package main.ar.com.codoacodo.controller;

import main.ar.com.codoacodo.db.DBConnection;
import main.ar.com.codoacodo.model.House;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HouseDAOImpl implements HouseDAO {

    @Override
    public House getById(Integer id) throws SQLException {
        House house = null;
        String query = "SELECT * FROM habitables WHERE id = ?;";
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    house = toHouse(resultSet);
            }
        }
        return house;
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public int create(House element) throws SQLException {
        String query =
        """
        INSERT INTO habitables(
            direccion, descripcion, precio, metros, habitaciones, banios, ambientes, fecha
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?);
        """;
        int id = 0;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, element.getAddress());
            statement.setString(2, element.getDescr());
            statement.setDouble(3, element.getPrize());
            statement.setFloat(4, element.getM2());
            statement.setInt(5, element.getHab());
            statement.setInt(6, element.getBath());
            statement.setInt(7, element.getAmb());
            statement.setTimestamp(8, element.getPublishDate());
            boolean ignored = statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
        }
        return id;
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public int update(House element) throws SQLException {
        String query =
        """
        UPDATE habitables
        SET direccion=?, descripcion=?, precio=?, metros=?,
            habitaciones=?, banios=?, ambientes=?
        WHERE id = ?;
        """;
        int rowsAffected;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, element.getAddress());
            statement.setString(2, element.getDescr());
            statement.setDouble(3, element.getPrize());
            statement.setFloat(4, element.getM2());
            statement.setInt(5, element.getHab());
            statement.setInt(6, element.getBath());
            statement.setInt(7, element.getAmb());
            statement.setInt(8, element.getId());
            rowsAffected = statement.executeUpdate();
        }
        return rowsAffected;
    }

    @Override
    public int delete(House element) throws SQLException {
        if (element.isActive())
            throw new RuntimeException("El habitable no fué deshabilitado correctamente.");
        String query = "UPDATE habitables SET activo = ? WHERE id = ?;";
        int rowsAffected;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setBoolean(1, element.isActive());
            statement.setInt(1, element.getId());
            rowsAffected = statement.executeUpdate();
        }
        return rowsAffected;
    }

    @Override
    public List<House> findAll() throws SQLException {
        List<House> houses = new ArrayList<>(Collections.emptyList());
        String query = "SELECT * FROM habitables ORDER BY fecha DESC;";
        try (
                Connection connection = DBConnection.create();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                houses.add(toHouse(resultSet));
            }
        }
        return houses;
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public List<House> search(String value) throws SQLException {
        List<House> houses = new ArrayList<>(Collections.emptyList());
        String query =
        """
        SELECT * FROM habitables
        WHERE descr LIKE %?% OR direccion LIKE %?%
        """;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, value);
            statement.setString(2, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    houses.add(toHouse(resultSet));
                }
            }
        }
        return houses;
    }

    @SuppressWarnings("SpellCheckingInspection")
    private House toHouse(ResultSet resultSet) throws SQLException {
        House house = new House();
        house.setId(resultSet.getInt("id"));
        house.setAddress(resultSet.getString("direccion"));
        house.setDescr(resultSet.getString("descripcion"));
        house.setPrize(resultSet.getDouble("precio"));
        house.setM2(resultSet.getFloat("metros"));
        house.setHab(resultSet.getInt("habitaciones"));
        house.setBath(resultSet.getInt("banios"));
        house.setAmb(resultSet.getInt("ambientes"));
        house.setPublishDate(resultSet.getTimestamp("fecha"));
        house.setActive(resultSet.getBoolean("activo"));
        return house;
    }

}
