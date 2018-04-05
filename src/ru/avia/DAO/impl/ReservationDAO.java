package ru.avia.DAO.impl;

import org.hibernate.Session;
import ru.avia.model.Classes;
import ru.avia.model.Reservation;
import ru.avia.utils.Hibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ReservationDAO extends AbstractDAOImpl<Reservation, Integer> {
    public ReservationDAO() {
        super(Reservation.class);
    }

    public List<Reservation> getAllReservation() {
        return list();
    }


    public boolean getBusy(Classes classes, Integer number){
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Reservation> criteria = builder.createQuery(Reservation.class);

        Root<Reservation> root = criteria.from(Reservation.class);
        criteria.select(root);
        criteria.where(builder.and(
                builder.equal(root.get("classesByClassesId"), classes),
                builder.equal(root.get("numberPlace"), number)
                )
        );
        List<Reservation> result = session.createQuery(criteria).getResultList();
        session.close();

        if (result.isEmpty()) {
            return false;
        }
        return true;
    }
}
