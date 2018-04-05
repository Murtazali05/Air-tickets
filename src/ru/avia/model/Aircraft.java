package ru.avia.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Aircraft {
    private int id;
    private String name;
    private Collection<Classes> classesById;
    private Collection<Flight> flightsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aircraft aircraft = (Aircraft) o;

        if (id != aircraft.id) return false;
        if (name != null ? !name.equals(aircraft.name) : aircraft.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "aircraftByAircraftId")
    public Collection<Classes> getClassesById() {
        return classesById;
    }

    public void setClassesById(Collection<Classes> classesById) {
        this.classesById = classesById;
    }

    @OneToMany(mappedBy = "aircraftByAircraftId")
    public Collection<Flight> getFlightsById() {
        return flightsById;
    }

    public void setFlightsById(Collection<Flight> flightsById) {
        this.flightsById = flightsById;
    }

    @Override
    public String toString() {
        return name;
    }
}
