package ru.avia.model;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

@Entity
public class Flight {
    private int id;
    private String number;
    private Calendar flightDate;
    private Calendar flightTime;
    private Aircraft aircraftByAircraftId;
    private City cityByCityFromId;
    private City cityByCityToId;
    private Collection<Reservation> reservationsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "number", nullable = false, length = 100)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "flight_date", nullable = false)
    public Calendar getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Calendar flightDate) {
        this.flightDate = flightDate;
    }

    @Basic
    @Column(name = "flight_time", nullable = false)
    public Calendar getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Calendar flightTime) {
        this.flightTime = flightTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Flight flight = (Flight) o;

        if (id != flight.id) return false;
        if (!number.equals(flight.number)) return false;
        if (!flightDate.equals(flight.flightDate)) return false;
        return flightTime.equals(flight.flightTime);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + number.hashCode();
        result = 31 * result + flightDate.hashCode();
        result = 31 * result + flightTime.hashCode();
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "aircraft_id", referencedColumnName = "id", nullable = false)
    public Aircraft getAircraftByAircraftId() {
        return aircraftByAircraftId;
    }

    public void setAircraftByAircraftId(Aircraft aircraftByAircraftId) {
        this.aircraftByAircraftId = aircraftByAircraftId;
    }

    @ManyToOne
    @JoinColumn(name = "city_from_id", referencedColumnName = "id", nullable = false)
    public City getCityByCityFromId() {
        return cityByCityFromId;
    }

    public void setCityByCityFromId(City cityByCityFromId) {
        this.cityByCityFromId = cityByCityFromId;
    }

    @ManyToOne
    @JoinColumn(name = "city_to_id", referencedColumnName = "id", nullable = false)
    public City getCityByCityToId() {
        return cityByCityToId;
    }

    public void setCityByCityToId(City cityByCityToId) {
        this.cityByCityToId = cityByCityToId;
    }

    @OneToMany(mappedBy = "flightByFlightId")
    public Collection<Reservation> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<Reservation> reservationsById) {
        this.reservationsById = reservationsById;
    }

    @Transient
    public SimpleStringProperty dateDepartureProperty() {
        SimpleDateFormat formatter=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return new SimpleStringProperty(formatter.format(this.getFlightDate().getTime()));
    }

    @Transient
    public SimpleStringProperty dateArrivalProperty() {
        SimpleDateFormat formatter=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return new SimpleStringProperty(formatter.format(this.getFlightTime().getTime()));
    }

    @Transient
    public SimpleStringProperty countPlaceProperty() {
        Collection<Classes> classesCollection = this.getAircraftByAircraftId().getClassesById();
        Integer countPlace = 0;
        for (Classes classes : classesCollection) {
            countPlace += classes.getPlaceCount();
        }
        return new SimpleStringProperty(countPlace.toString());
    }

}
