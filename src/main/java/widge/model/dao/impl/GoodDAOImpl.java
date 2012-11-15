package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Good;
import widge.model.dao.GoodDAO;

import java.util.List;

public class GoodDAOImpl implements GoodDAO {

    private SessionFactory sessionFactory;

    public GoodDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Good> getGoods() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Good");
        return query.list();
    }

    public Good getGoodById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Good WHERE id = :id");
        query.setInteger("id", id);
        return (Good)query.uniqueResult();
    }
}
