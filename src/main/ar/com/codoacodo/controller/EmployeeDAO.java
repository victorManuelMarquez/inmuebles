package main.ar.com.codoacodo.controller;

import main.ar.com.codoacodo.model.Employee;
import main.ar.com.codoacodo.model.House;
import main.ar.com.codoacodo.model.User;

import java.util.List;

public interface EmployeeDAO extends GenericDAO<Employee> {

    List<Employee> listAll() throws Exception;

    Employee getFromUser(User user) throws Exception;

    boolean isManager(Employee employee) throws Exception;

    int publishNewHouse(House building, Employee employee) throws Exception;

    int modifyHouse(House building, Employee employee) throws Exception;

    int deleteHouse(House building, Employee employee) throws Exception;

    int addNewPersonal(Employee newOne, Employee employee) throws Exception;

    int modifyPersonal(Employee toModify, Employee employee) throws Exception;

    int dropPersonal(Employee drop, Employee employee) throws Exception;

}
