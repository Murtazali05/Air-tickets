package ru.avia.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class City {
    private int id;
    private String name;
    private String postcode;
    private Country countryByCountryId;
    private Collection<Flight> flightsById;
    private Collection<Flight> flightsById_0;

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
    @Column(name = "postcode", nullable = false, length = 45)
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        if (postcode != null ? !postcode.equals(city.postcode) : city.postcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + ", " + countryByCountryId.getName();
    }

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    public Country getCountryByCountryId() {
        return countryByCountryId;
    }

    public void setCountryByCountryId(Country countryByCountryId) {
        this.countryByCountryId = countryByCountryId;
    }

    @OneToMany(mappedBy = "cityByCityFromId")
    public Collection<Flight> getFlightsById() {
        return flightsById;
    }

    public void setFlightsById(Collection<Flight> flightsById) {
        this.flightsById = flightsById;
    }

    @OneToMany(mappedBy = "cityByCityToId")
    public Collection<Flight> getFlightsById_0() {
        return flightsById_0;
    }

    public void setFlightsById_0(Collection<Flight> flightsById_0) {
        this.flightsById_0 = flightsById_0;
    }
}
