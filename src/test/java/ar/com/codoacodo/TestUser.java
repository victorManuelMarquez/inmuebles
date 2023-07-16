package ar.com.codoacodo;

import main.ar.com.codoacodo.controller.UserDAO;
import main.ar.com.codoacodo.controller.UserDAOImpl;
import main.ar.com.codoacodo.model.User;

public final class TestUser {

    public static void main(String[] args) {
        UserDAO dao = new UserDAOImpl();
        String email = "alguien@example";
        String password = "12345678";
        User user = null;
        boolean session = false;
        // buscar en la bd para crear un usuario
        try {
            user = dao.verify(email, password);
            System.out.println(user);
            if (user != null && user.getPassword().equals(password)) {
                System.out.println("Bienvenido: " + user);
                session = true;
            } else
                System.out.println("Correo o contraseña incorrecta.");
            if (user == null) {
                user = dao.register(email, password);
                System.out.println("Nuevo: " + user);
                session = user != null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // actualizar contraseña
        if (session) {
            try {
                String old = user.getPassword();
                if (dao.changePassword(user.getId(), "12345678") > 0)
                    System.out.println("Contraseña cambiada.");
                user = dao.getById(user.getId());
                System.out.println(old + " >> " + user.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
