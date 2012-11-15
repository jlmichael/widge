package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Game;
import widge.model.Turn;
import widge.model.dao.TurnDAO;

import java.util.List;

public class TurnDAOImpl implements TurnDAO {

    private SessionFactory sessionFactory;

    public TurnDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Turn getTurnById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Turn WHERE id = :id");
        query.setInteger("id", id);
        return (Turn)query.uniqueResult();
    }

    public List<Turn> getTurnsForGame(Game game) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Turn WHERE game = :game");
        query.setEntity("game", game);
        return query.list();
    }

    public Turn getNextTurnForGame(Game game) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Turn WHERE game = :game AND startTime IS NULL ORDER BY turnNumber");
        query.setEntity("game", game);
        return (Turn)query.uniqueResult();
    }

    public void addTurn(Turn turn) {
        Session session = sessionFactory.openSession();
        session.persist(turn);
    }

    public Turn updateTurn(Turn turn) {
        Session session = sessionFactory.openSession();
        session.update(turn);
        session.refresh(turn);
        return turn;
    }
}
