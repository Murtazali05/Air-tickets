package ru.avia.DAO.impl;

import org.hibernate.Session;
import ru.avia.DAO.AbstractDAO;
import ru.avia.utils.Hibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class AbstractDAOImpl<T, PK extends Serializable> implements AbstractDAO<T, PK> {

    private Class<T> type;

    public AbstractDAOImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public PK save(T newInstance) {
        Session session = Hibernate.getSession();
        session.beginTransaction();
        PK id = (PK) session.save(newInstance);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    @Override
    public T get(PK id) {
        Session session = Hibernate.getSession();
        T object = session.get(type, id);
        session.close();
        return object;
    }

    @Override
    public void update(T transientObject) {
        Session session = Hibernate.getSession();
        session.beginTransaction();
        session.update(transientObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(T persistentObject) {
        Session session = Hibernate.getSession();
        session.beginTransaction();
        session.delete(persistentObject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<T> list() {
        Session session = Hibernate.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);

        Root<T> root = criteria.from(type);
        criteria.select(root);

        List<T> objects = session.createQuery(criteria).getResultList();
        session.close();
        return objects;
    }
}

