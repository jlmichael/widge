package widge.model.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Player;
import widge.model.Token;
import widge.model.dao.TokenDAO;

import java.util.List;

public class TokenDAOImpl implements TokenDAO {
    public static Logger logger = Logger.getLogger(TokenDAOImpl.class);

    private SessionFactory sessionFactory;

    public TokenDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addToken(Token token) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(token);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Error trying to create token: ", e);
            session.getTransaction().rollback();
        }

    }

    public List<Token> getTokensByPlayer(Player player) {
        Session session = sessionFactory.openSession();
        String queryStr = "FROM Token WHERE player = :player";
        Query query = session.createQuery(queryStr);
        query.setEntity("player", player);
        return (List<Token>)query.list();
    }

    public void deleteToken(Token token) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(token);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.error("Error when trying to delete token: " , e);
            session.getTransaction().rollback();
        }
    }

    public Token getTokenByTokenKey(String tokenKey) {
        Session session = sessionFactory.openSession();
        String queryStr = "FROM Token WHERE tokenKey = :tokenKey";
        Query query = session.createQuery(queryStr);
        query.setString("tokenKey", tokenKey);
        return (Token)query.uniqueResult();
    }
}
