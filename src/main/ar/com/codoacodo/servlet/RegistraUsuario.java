package main.ar.com.codoacodo.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.ar.com.codoacodo.controller.UserDAO;
import main.ar.com.codoacodo.controller.UserDAOImpl;
import main.ar.com.codoacodo.model.User;

import java.io.IOException;
import java.io.PrintWriter;

public class RegistraUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("userEmail");
        String password = req.getParameter("userPassword");
        UserDAO dao = new UserDAOImpl();
        try {
            User user = dao.register(email, password);
            if (user != null) {
                req.setAttribute("usuario", user);
                RequestDispatcher dispatcher;
                dispatcher = req.getRequestDispatcher("resume-registration.jsp");
                dispatcher.forward(req, resp);
            } else {
                RequestDispatcher dispatcher;
                dispatcher = req.getRequestDispatcher("register.jsp");
                dispatcher.include(req, resp);
            }
        } catch (Exception e) {
            PrintWriter out = resp.getWriter();
            out.print(e.getMessage());
        }
    }

}
