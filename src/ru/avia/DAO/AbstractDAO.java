package ru.avia.DAO;

import java.io.Serializable;
import java.util.List;

public interface AbstractDAO<T, PK extends Serializable> {

    PK save(T newInstance);

    T get(PK id);

    void update(T transientObject);

    void delete(T persistentObject);

    List<T> list();

}
