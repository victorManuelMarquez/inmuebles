package main.ar.com.codoacodo.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.ar.com.codoacodo.controller.EmployeeDAO;
import main.ar.com.codoacodo.controller.EmployeeDAOImpl;
import main.ar.com.codoacodo.controller.UserDAO;
import main.ar.com.codoacodo.controller.UserDAOImpl;
import main.ar.com.codoacodo.model.Employee;
import main.ar.com.codoacodo.model.User;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("userEmail");
        String password = req.getParameter("userPassword");
        UserDAO dao = new UserDAOImpl();
        try {
            User user = dao.verify(email, password);
            if (user != null && user.getPassword().equals(password)) {
                req.setAttribute("usuario", user);
                EmployeeDAO employeeDAO;
                employeeDAO = new EmployeeDAOImpl();
                Employee employee = employeeDAO.getFromUser(user);
                if (employee != null) {
                    /*req.setAttribute("empleado", employee);
                    RequestDispatcher dispatcher;
                    dispatcher = req.getRequestDispatcher("work-station.jsp");
                    dispatcher.forward(req, resp);*/
                    resp.sendRedirect(req.getContextPath() + "/work-station.jsp?empleado=" + employee.getId());
                } else {
                    RequestDispatcher dispatcher;
                    dispatcher = req.getRequestDispatcher("welcome.jsp");
                    dispatcher.forward(req, resp);
                }
            } else {
                RequestDispatcher dispatcher;
                dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.include(req, resp);
            }
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.print(e.getMessage());
        }
    }

}
