package ru.avia.DAO.impl;

import org.hibernate.Session;
import ru.avia.model.Passenger;
import ru.avia.utils.Hibernate;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PassengerDAO extends AbstractDAOImpl<Passenger, Integer> {

    public PassengerDAO() {
        super(Passenger.class);
    }

    public List<Passenger> getAllPassenger() {
        return list();
    }

    public Passenger getByDocumentNumber(String password) {
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Passenger> criteria = builder.createQuery(Passenger.class);


        Root<Passenger> passengerRoot = criteria.from(Passenger.class);
        criteria.select(passengerRoot);

        criteria.where(builder.equal(passengerRoot.get("documentNumber"), password));
        Passenger result;
        try {
            result = session.createQuery(criteria).getSingleResult();
        } catch (NoResultException e) {
            result = null;
        } finally {
            session.close();
        }
        return result;
    }
}