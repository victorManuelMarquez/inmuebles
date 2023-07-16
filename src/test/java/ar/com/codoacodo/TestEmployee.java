package ar.com.codoacodo;

import main.ar.com.codoacodo.controller.EmployeeDAO;
import main.ar.com.codoacodo.controller.EmployeeDAOImpl;
import main.ar.com.codoacodo.controller.UserDAO;
import main.ar.com.codoacodo.controller.UserDAOImpl;
import main.ar.com.codoacodo.model.Employee;
import main.ar.com.codoacodo.model.House;
import main.ar.com.codoacodo.model.User;

import java.sql.Timestamp;

public final class TestEmployee {

    public static void main(String[] args) {
        String userName = "juan_perez@hotmail.com";
        String pass = "juan123";
        UserDAO userDAO = new UserDAOImpl();
        EmployeeDAO dao = new EmployeeDAOImpl();
        try {
            User user = userDAO.verify(userName, pass);
            Employee employee = dao.getFromUser(user);
            System.out.println(employee);
            House house = new House();
            house.setDescr(
            """
            Excelente PH reciclado a nuevo en el corazón de Palermo Hollywood. Presenta en planta baja:
             Gran living comedor con parte del techo en vidrio, moderna cocina semi integrada con horno
             a gas profesional, 2 habitaciones grandes ideales para oficina con entrada independiente,
              toilette de recepción.
            """);
            house.setAddress("Bonpland 1400, Palermo, Capital Federal");
            house.setAmb(6);
            house.setBath(3);
            house.setPrize(23500000.00);
            house.setM2(176f);
            house.setHab(5);
            house.setPublishDate(new Timestamp(System.currentTimeMillis()));
            house.setActive(true);
            dao.publishNewHouse(house, employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
