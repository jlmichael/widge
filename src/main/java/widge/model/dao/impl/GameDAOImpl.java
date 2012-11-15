package widge.model.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Game;
import widge.model.dao.GameDAO;

import java.util.Date;
import java.util.List;

public class GameDAOImpl implements GameDAO {
    private Logger logger = Logger.getLogger(PlayerDAOImpl.class);

    private SessionFactory sessionFactory;

    public GameDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Game getGameById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Game WHERE id = :id");
        query.setInteger("id", id);
        return (Game)query.uniqueResult();
    }

    public List<Game> getGames() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Game");
        return (List<Game>)query.list();
    }

    public List<Game> getGamesModifiedSinceDate(Date lastModified) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Game WHERE LastModifiedDate > :date");
        query.setDate("date", lastModified);
        return (List<Game>)query.list();
    }

    public Game addGame(Game game) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.persist(game);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Could not add game: " + game);
            session.getTransaction().rollback();
            return null;
        }
        session.refresh(game);
        return game;
    }

    public Game updateGame(Game game) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(game);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Could not update game: " + game);
            session.getTransaction().rollback();
            return null;
        }
        session.refresh(game);
        return game;
    }
}
