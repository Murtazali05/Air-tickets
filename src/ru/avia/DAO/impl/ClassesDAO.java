package ru.avia.DAO.impl;

import ru.avia.model.Classes;

import java.util.List;

public class ClassesDAO extends AbstractDAOImpl<Classes, Integer> {

    public ClassesDAO() {
        super(Classes.class);
    }

    public List<Classes> getAllClasses() {
        return list();
    }

}