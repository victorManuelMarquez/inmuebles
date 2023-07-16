package main.ar.com.codoacodo.controller;

interface GenericDAO<E> {

    E getById(Integer id) throws Exception;

    int create(E element) throws Exception;

    int update(E element) throws Exception;

    int delete(E element) throws Exception;

}
