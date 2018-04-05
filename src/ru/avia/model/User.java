package ru.avia.model;


import ru.avia.DAO.impl.PassengerDAO;

public final class User {

    private static User instance = null;

    private User() {}
    private Passenger current;

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public Passenger getCurrent() {
        PassengerDAO passengerDAO = new PassengerDAO();
        return passengerDAO.get(this.current.getId());
    }

    public void setCurrent(Passenger current) {
        this.current = current;
    }
}
