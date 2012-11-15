package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.FilledMarketOrder;
import widge.model.Game;
import widge.model.Player;
import widge.model.dao.FilledMarketOrderDAO;

import java.util.List;

public class FilledMarketOrderDAOImpl implements FilledMarketOrderDAO {

    private SessionFactory sessionFactory;

    public FilledMarketOrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<FilledMarketOrder> getFilledMarketOrdersForGame(Game game) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FilledMarketOrder WHERE game = :game");
        query.setEntity("game", game);
        return query.list();
    }

    public FilledMarketOrder getFilledMarketOrderById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FilledMarketOrder WHERE id = :id");
        query.setInteger("id", id);
        return (FilledMarketOrder)query.uniqueResult();
    }

    public List<FilledMarketOrder> getFilledMarketOrdersForGameAndSeller(Game game, Player seller) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FilledMarketOrder o WHERE o.game = :game AND o.askOrder.player = :seller");
        query.setEntity("game", game);
        query.setEntity("seller", seller);
        return query.list();
    }

    public List<FilledMarketOrder> getFilledMarketOrdersForGameAndBuyer(Game game, Player buyer) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FilledMarketOrder o WHERE o.game = :game AND o.bidOrder.player = :buyer");
        query.setEntity("game", game);
        query.setEntity("buyer", buyer);
        return query.list();
    }

    public void addFilledMarketOrder(FilledMarketOrder order) {
        Session session = sessionFactory.openSession();
        session.persist(order);
    }
}
