<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page import="main.ar.com.codoacodo.model.Employee,main.ar.com.codoacodo.controller.*"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inmuebles S.A</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
</head>
<body class="d-flex flex-column vh-100 bg-body-secondary" style="padding-top: 56px;">
    <header>
        <nav class="navbar navbar-expand-lg fixed-top bg-body-tertiary border-bottom fixed-top" aria-label="Main navigation">
            <div class="container-fluid">
                <a href="index.jsp" class="navbar-brand"><i class="bi bi-buildings-fill"></i> Inmuebles S.A</a>
                <button class="navbar-toggler p-0 border-0" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar" aria-controls="offcanvasNavbar" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="offcanvas offcanvas-start" tabindex="-1" id="offcanvasNavbar" aria-labelledby="offcanvasNavbarLabel">
                    <div class="offcanvas-header">
                        <button class="btn-close" type="button" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                    </div>
                    <div class="offcanvas-body">
                        <ul class="navbar-nav justify-content-start flex-grow-1 ps-3">
                            <li class="nav-item">
                                <a href="index.jsp" class="nav-link active"><i class="bi bi-house-fill"></i> Home</a>
                            </li>
                        </ul>
                        <form class="d-flex mb-0 mt-2 mt-lg-0" role="search">
                            <input type="search" class="form-control me-2" placeholder="Buscar..." aria-label="Search">
                            <button class="btn btn-outline-success" type="submit">Buscar</button>
                        </form>
                        <div class="d-flex flex-nowrap justify-content-center mb-0 ms-lg-3 mt-3 mt-lg-0">
                            <button class="btn btn-danger me-2" data-bs-dismiss="offcanvas">
                                <a href="logoutUsuario.jsp" class="d-flex text-decoration-none">Cerrar Sesión</a>
                            </button>
                            <button class="btn btn-primary" data-bs-dismiss="offcanvas">
                                <a href="register.jsp" class="d-flex text-decoration-none" style="color: inherit;">Registrarse</a>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    </header>
    <main class="flex-shrink-0 py-5">
        <div class="container" id="apartadoIngresar">
            <div class="shadow bg-body px-3 py-3 p-lg-5">
            <%  Employee empleado = (new EmployeeDAOImpl()).getById(Integer.parseInt(request.getParameter("empleado")));
                if (empleado.getType().equalsIgnoreCase("gerente")) { %>
                <jsp:include page="manager.jsp"/>
            <% } else { %>
                <jsp:include page="employee.jsp"/>
            <% } %>
            </div>
        </div>
    </main>
    <footer class="mt-auto py-3 bg-body-tertiary border-top">
        <div class="container">
            <div class="d-flex flex-wrap justify-content-between align-items-center py-3 my-4 border-top">
                <div class="col-md-4 d-flex align-items-center">
                    <a href="#" class="mb-3 me-2 mb-md-0 text-body-secondary text-decoration-none lh-1">
                        <i class="bi bi-buildings-fill"></i>
                    </a>
                    <span class="mb-3 mb-md-0 text-body-secondary">&copy; 2023, Víctor M. Márquez.</span>
                </div>
            </div>
        </div>
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</body>
</html>