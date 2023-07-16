package main.ar.com.codoacodo.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.ar.com.codoacodo.controller.EmployeeDAO;
import main.ar.com.codoacodo.controller.EmployeeDAOImpl;
import main.ar.com.codoacodo.model.Employee;

import java.io.IOException;
import java.io.PrintWriter;

public class UpdateEmpleado extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dni = req.getParameter("nuevoDni");
        String nombre = req.getParameter("nuevoNombre");
        String domicilio = req.getParameter("nuevoDomicilio");
        String estado = req.getParameter("nuevoEstado");
        System.out.println(">>>>" + estado);
        EmployeeDAO dao = new EmployeeDAOImpl();
        try {
            Employee gerente = dao.getById(Integer.parseInt(req.getParameter("idGerente")));
            Employee editado = dao.getById(Integer.parseInt(req.getParameter("idEmpleado")));
            if (gerente != null && editado != null) {
                if (gerente.getType().equalsIgnoreCase("gerente") && !editado.getType().equalsIgnoreCase("gerente")) {
                    editado.setPersonId(dni);
                    editado.setFullName(nombre);
                    editado.setAddress(domicilio);
                    dao.modifyPersonal(editado, gerente);
                    resp.sendRedirect(req.getContextPath() + "/work-station.jsp?empleado=" + gerente.getId());
                } else {
                    PrintWriter writer = resp.getWriter();
                    writer.print(gerente + " -> No es gerente.");
                }
            } else {
                RequestDispatcher dispatcher;
                dispatcher = req.getRequestDispatcher("index.jsp");
                dispatcher.include(req, resp);
            }
        } catch (Exception e) {
            PrintWriter writer = resp.getWriter();
            writer.print(e.getMessage());
        }
    }

}
