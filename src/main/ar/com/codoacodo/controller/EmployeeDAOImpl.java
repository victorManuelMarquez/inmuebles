package main.ar.com.codoacodo.controller;

import main.ar.com.codoacodo.db.DBConnection;
import main.ar.com.codoacodo.model.Employee;
import main.ar.com.codoacodo.model.House;
import main.ar.com.codoacodo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> listAll() throws SQLException {
        List<Employee> employees = new ArrayList<>(Collections.emptyList());
        String query = "SELECT * FROM empleados";
        try (
                Connection connection = DBConnection.create();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            while (resultSet.next()) {
                employees.add(toEmployee(resultSet));
            }
        }
        return employees;
    }

    @Override
    public Employee getFromUser(User user) throws SQLException {
        String query = "SELECT * FROM empleados WHERE idUsuario = " + user.getId() + ";";
        Employee employee = null;
        try (
                Connection connection = DBConnection.create();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)
        ) {
            if (resultSet.next())
                employee = toEmployee(resultSet);
        }
        return employee;
    }

    @Override
    public boolean isManager(Employee employee) {
        return employee.getType().equalsIgnoreCase("gerente");
    }

    @Override
    public int publishNewHouse(House building, Employee employee) throws SQLException {
        if (!isManager(employee))
            throw new RuntimeException(employee.getFullName() + " no está calificado para agregar.");
        if (employee.isActive()) {
            int houseId = new HouseDAOImpl().create(building);
            if (houseId > 0)
                building.setId(houseId);
            else
                throw new RuntimeException("El id de la casa es " + houseId + ".");
            int affectedRows;
            String query = "INSERT INTO historial(idEmpleado, idHabitable) VALUES (?,?);";
            try (
                    Connection connection = DBConnection.create();
                    PreparedStatement statement = connection.prepareStatement(query)
            ) {
                statement.setInt(1, employee.getId());
                statement.setInt(2, building.getId());
                boolean ignored = statement.execute();
                affectedRows = 1;
            }
            return affectedRows;
        } else throw new RuntimeException(employee.getFullName() + " está incapacitado.");
    }

    @Override
    public int modifyHouse(House building, Employee employee) throws SQLException {
        if (!isManager(employee))
            throw new RuntimeException(employee.getFullName() + " no está calificado para modificar.");
        if (employee.isActive())
            return new HouseDAOImpl().update(building);
        else throw new RuntimeException(employee.getFullName() + " está incapacitado.");
    }

    @Override
    public int deleteHouse(House building, Employee employee) throws SQLException {
        if (!isManager(employee))
            throw new RuntimeException(employee.getFullName() + " no está calificado para borrar.");
        if (employee.isActive())
            return new HouseDAOImpl().delete(building);
        else throw new RuntimeException(employee.getFullName() + " está incapacitado.");
    }

    @Override
    public int addNewPersonal(Employee newOne, Employee employee) throws SQLException {
        if (isManager(employee) && employee.isActive())
            return create(newOne);
        else throw new RuntimeException(employee.getFullName() + " no está calificado para agregar un empleado.");
    }

    @Override
    public int modifyPersonal(Employee toModify, Employee employee) throws SQLException {
        if (isManager(employee) && employee.isActive())
            return update(toModify);
        else throw new RuntimeException(employee.getFullName() + " no está calificado para modificar un empleado.");
    }

    @Override
    public int dropPersonal(Employee drop, Employee employee) throws SQLException {
        if (isManager(employee) && employee.isActive())
            return delete(drop);
        else throw new RuntimeException(employee.getFullName() + " no está calificado para borrar un empleado.");
    }

    @Override
    public Employee getById(Integer id) throws SQLException {
        Employee employee = null;
        String query = "SELECT * FROM empleados WHERE id = ?;";
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next())
                    employee = toEmployee(resultSet);
            }
        }
        return employee;
    }

    @Override
    public int create(Employee element) throws SQLException {
        String query =
                """
                INSERT INTO empleados(
                    dni, nombreCompleto, domicilio, contratadoEl, esUn, idUsuario
                ) VALUES(?, ?, ?, ?, ?, ?);
                """;
        int id = 0;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, element.getPersonId());
            statement.setString(2, element.getFullName());
            statement.setString(3, element.getAddress());
            statement.setDate(4, element.getContractDate());
            statement.setString(5, element.getType());
            statement.setInt(6, element.getUserId());
            boolean ignored = statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next())
                    id = resultSet.getInt(1);
            }
        }
        return id;
    }

    @Override
    public int update(Employee element) throws SQLException {
        String query =
        """
        UPDATE empleados
        SET dni=?, nombreCompleto=?, domicilio=?, contratadoEl=?, esUn=?, idUsuario=?
        WHERE id = ?;
        """;
        int rowsAffected;
        try (
                Connection connection = DBConnection.create();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, element.getPersonId());
            statement.setString(2, element.getFullName());
            statement.setString(3, element.getAddress());
            statement.setDate(4, element.getContractDate());
            statement.setString(5, element.getType());
            statement.setInt(6, element.getUserId());
            statement.setInt(7, element.getId());
            rowsAffected = statement.executeUpdate();
        }
        return rowsAffected;
    }

    @Override
    public int delete(Employee element) throws SQLException {
        String query = "UPDATE empleados SET activo = ? WHERE id = ?;";
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

    private Employee toEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("id"));
        employee.setPersonId(resultSet.getString("dni"));
        employee.setFullName(resultSet.getString("nombreCompleto"));
        employee.setAddress(resultSet.getString("domicilio"));
        employee.setContractDate(resultSet.getDate("contratadoEl"));
        employee.setType(resultSet.getString("esUn"));
        employee.setActive(resultSet.getBoolean("activo"));
        employee.setUserId(resultSet.getInt("idUsuario"));
        return employee;
    }

}
