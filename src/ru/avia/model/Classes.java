package ru.avia.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Classes {
    private int id;
    private String name;
    private int placeCount;
    private Aircraft aircraftByAircraftId;
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
    @Column(name = "name", nullable = false, length = 200)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "place_count", nullable = false)
    public int getPlaceCount() {
        return placeCount;
    }

    public void setPlaceCount(int placeCount) {
        this.placeCount = placeCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classes classes = (Classes) o;

        if (id != classes.id) return false;
        if (placeCount != classes.placeCount) return false;
        if (name != null ? !name.equals(classes.name) : classes.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + placeCount;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Aircraft_id", referencedColumnName = "id", nullable = false)
    public Aircraft getAircraftByAircraftId() {
        return aircraftByAircraftId;
    }

    public void setAircraftByAircraftId(Aircraft aircraftByAircraftId) {
        this.aircraftByAircraftId = aircraftByAircraftId;
    }

    @OneToMany(mappedBy = "classesByClassesId")
    public Collection<Reservation> getReservationsById() {
        return reservationsById;
    }

    public void setReservationsById(Collection<Reservation> reservationsById) {
        this.reservationsById = reservationsById;
    }

    @Override
    public String toString() {
        return name;
    }

}
