package main.ar.com.codoacodo.controller;

import main.ar.com.codoacodo.model.House;

import java.util.List;

public interface HouseDAO extends GenericDAO<House> {

    List<House> findAll() throws Exception;

    List<House> search(String value) throws Exception;

}
