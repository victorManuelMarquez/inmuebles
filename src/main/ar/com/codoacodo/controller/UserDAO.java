package main.ar.com.codoacodo.controller;

import main.ar.com.codoacodo.model.User;

public interface UserDAO extends GenericDAO<User> {

    User register(String email, String password) throws Exception;

    User verify(String email, String password) throws Exception;

    int changePassword(Integer id, String newPassword) throws Exception;

}
