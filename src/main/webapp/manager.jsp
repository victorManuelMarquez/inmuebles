<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="java.util.List,java.util.ArrayList,main.ar.com.codoacodo.model.Employee,main.ar.com.codoacodo.controller.*" %>
<div class="overflow-auto">
    <h3>Empleados</h3>
    <%String gerente = request.getParameter("empleado");%>
    <table class="table table-striped">
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">DNI</th>
                <th scope="col">Nombre Completo</th>
                <th scope="col">Domicilio</th>
                <th scope="col">Empleado</th>
                <th scope="col">Contratado el</th>
                <th scope="col">Activo</th>
                <th scope="col">Editar</th>
            </tr>
        </thead>
        <tbody>
            <%
            EmployeeDAO employeeDAO;
            employeeDAO = new EmployeeDAOImpl();
            List<Employee> employees = new ArrayList<>();
            try {
                employees.addAll(employeeDAO.listAll());
            } catch(Exception e) {%>
                <h1><% e.getMessage(); %></h1>
            <%}%>
            <% int i = 0; %>
            <% for (Employee employee : employees) { %>
                <tr>
                    <th scope="row"><% ++i; %><%=i%></th>
                    <td><%=employee.getPersonId()%></td>
                    <td><%=employee.getFullName()%></td>
                    <td><%=employee.getAddress()%></td>
                    <td><%=employee.getType()%></td>
                    <td><%=employee.getContractDate()%></td>
                    <td><%=employee.isActive()%></td>
                    <td>
                        <a href="editing-employee.jsp?id=<%=employee.getId()%>&manager=<%=gerente%>" class="btn btn-primary py-0">
                            <span class="bi bi-pen-fill"></span>
                        </a>
                    </td>
                </tr>
            <% } %>
        </tbody>
    </table>
</div>