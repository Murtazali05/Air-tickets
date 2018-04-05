package ru.avia.model;

import javax.persistence.*;

@Entity
public class Reservation {
    private int id;
    private int numberPlace;
    private Flight flightByFlightId;
    private Passenger passengerByPassengerId;
    private Classes classesByClassesId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number_place", nullable = false)
    public int getNumberPlace() {
        return numberPlace;
    }

    public void setNumberPlace(int numberPlace) {
        this.numberPlace = numberPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (id != that.id) return false;
        if (numberPlace != that.numberPlace) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + numberPlace;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    public Flight getFlightByFlightId() {
        return flightByFlightId;
    }

    public void setFlightByFlightId(Flight flightByFlightId) {
        this.flightByFlightId = flightByFlightId;
    }

    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = false)
    public Passenger getPassengerByPassengerId() {
        return passengerByPassengerId;
    }

    public void setPassengerByPassengerId(Passenger passengerByPassengerId) {
        this.passengerByPassengerId = passengerByPassengerId;
    }

    @ManyToOne
    @JoinColumn(name = "classes_id", referencedColumnName = "id", nullable = false)
    public Classes getClassesByClassesId() {
        return classesByClassesId;
    }

    public void setClassesByClassesId(Classes classesByClassesId) {
        this.classesByClassesId = classesByClassesId;
    }
}
