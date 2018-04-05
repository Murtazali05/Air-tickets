package ru.avia.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Status {
    private int idStatus;
    private String name;
    private Collection<Passenger> passengersByIdStatus;

    @Id
    @Column(name = "idStatus", nullable = false)
    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
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

        Status status = (Status) o;

        if (idStatus != status.idStatus) return false;
        if (name != null ? !name.equals(status.name) : status.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idStatus;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "statusByStatusId")
    public Collection<Passenger> getPassengersByIdStatus() {
        return passengersByIdStatus;
    }

    public void setPassengersByIdStatus(Collection<Passenger> passengersByIdStatus) {
        this.passengersByIdStatus = passengersByIdStatus;
    }
}
