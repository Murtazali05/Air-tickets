package ru.avia.DAO.impl;

import org.hibernate.Session;
import ru.avia.model.City;
import ru.avia.model.Flight;
import ru.avia.utils.Hibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FlightDAO extends AbstractDAOImpl<Flight, Integer> {

    public FlightDAO() {
        super(Flight.class);
    }

    public List<Flight> getAllFlight() {
        return list();
    }

    public List<Flight> getFilterFlight(City cityFrom, City cityTo, LocalDate dateFrom){
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);

        Root<Flight> flightRoot = criteria.from(Flight.class);
        criteria.select(flightRoot);

        Instant instant = Instant.from(dateFrom.atStartOfDay(ZoneId.systemDefault()));
        Date newDateFrom = Date.from(instant);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newDateFrom);

        criteria.where(
                builder.and(builder.equal(flightRoot.get("cityByCityFromId"), cityFrom),
                        builder.equal(flightRoot.get("cityByCityToId"), cityTo),
                        builder.greaterThan(flightRoot.get("flightDate"), calendar)
                )
        );

        List<Flight> results = session.createQuery(criteria).getResultList();
        session.close();
        return results;
    }

    public List<Flight> getFutureFlights(){
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> criteria = builder.createQuery(Flight.class);

        Root<Flight> flightRoot = criteria.from(Flight.class);
        criteria.select(flightRoot);

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        criteria.where(builder.greaterThan(flightRoot.get("flightDate"), calendar));

        List<Flight> results = session.createQuery(criteria).getResultList();
        session.close();
        return results;
    }
}