package ru.avia.DAO.impl;

import ru.avia.model.Aircraft;

import java.util.List;

public class AircraftDAO extends AbstractDAOImpl<Aircraft, Integer> {

    public AircraftDAO() {
        super(Aircraft.class);
    }

    public List<Aircraft> getAllAircraft() {
        return list();
    }

}