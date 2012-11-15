package widge.model.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Player;
import widge.model.dao.PlayerDAO;
import widge.util.Auth;

import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {
    private Logger logger = Logger.getLogger(PlayerDAOImpl.class);

    private SessionFactory sessionFactory;

    public PlayerDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Player getPlayerById(Integer id) {
        Session session = sessionFactory.openSession();
        String queryStr = "FROM Player WHERE id = :id";
        Query query = session.createQuery(queryStr);
        query.setInteger("id", id);
        return (Player)query.uniqueResult();
    }

    public Player getPlayerByName(String name) {
        Session session = sessionFactory.openSession();
        String queryStr = "FROM Player WHERE name = :name";
        Query query = session.createQuery(queryStr);
        query.setString("name", name);
        return (Player)query.uniqueResult();
    }

    public List<Player> getPlayers() {
        Session session = sessionFactory.openSession();
        String queryStr = "FROM Player";
        Query query = session.createQuery(queryStr);
        return (List<Player>)query.list();
    }

    public Player addPlayer(Player player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            // Salt and digest the password
            player.setPassword(Auth.getSaltedDigest(player.getPassword()));
            session.persist(player);
            session.getTransaction().commit();
            session.refresh(player);
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
        return player;
    }

    public Player updatePlayer(Player player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.update(player);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Error trying to delete player: " + player.getId(), e);
            session.getTransaction().rollback();
        }
        session.refresh(player);
        return player;
    }

    public void deletePlayer(Player player) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.delete(player);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Error trying to delete player: " + player.getId(), e);
            session.getTransaction().rollback();
        }
    }

}
