package ar.com.codoacodo;

import main.ar.com.codoacodo.controller.EmployeeDAO;
import main.ar.com.codoacodo.controller.EmployeeDAOImpl;
import main.ar.com.codoacodo.controller.UserDAO;
import main.ar.com.codoacodo.controller.UserDAOImpl;
import main.ar.com.codoacodo.model.Employee;
import main.ar.com.codoacodo.model.User;

public class TestHouse {

    public static void main(String[] args) {
        String userName = "juan_perez@hotmail.com";
        String pass = "juan123";
        UserDAO userDAO = new UserDAOImpl();
        EmployeeDAO dao = new EmployeeDAOImpl();
        try {
            User user = userDAO.verify(userName, pass);
            Employee employee = dao.getFromUser(user);
            System.out.println(employee);
            System.out.println(employee.getType().equalsIgnoreCase("gerente") ? "ES GERENTE" : "NO ES GERENTE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
