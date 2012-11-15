package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Game;
import widge.model.MarketOrder;
import widge.model.Player;
import widge.model.dao.MarketOrderDAO;

import java.util.List;

public class MarketOrderDAOImpl implements MarketOrderDAO {

    private SessionFactory sessionFactory;

    public MarketOrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<MarketOrder> getMarketOrdersForGame(Game game) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM MarketOrder WHERE game = :game");
        query.setEntity("game", game);
        return query.list();
    }

    public List<MarketOrder> getMarketOrdersForGameAndSeller(Game game, Player seller) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM MarketOrder WHERE game = :game AND player = :seller AND orderType = :type");
        query.setEntity("game", game);
        query.setEntity("seller", seller);
        query.setEntity("type", MarketOrder.MarketOrderType.ASK);
        return query.list();
    }

    public List<MarketOrder> getMarketOrdersForGameAndBuyer(Game game, Player buyer) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM MarketOrder WHERE game = :game AND player = :buyer AND orderType = :type");
        query.setEntity("game", game);
        query.setEntity("buyer", buyer);
        query.setEntity("type", MarketOrder.MarketOrderType.BID);        
        return query.list();
    }

    public MarketOrder getMarketOrderById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM MarketOrder WHERE id = :id");
        query.setInteger("id", id);
        return (MarketOrder)query.uniqueResult();
    }

    public void addMarketOrder(MarketOrder marketOrder) {
        Session session = sessionFactory.openSession();
        session.persist(marketOrder);
    }

    public MarketOrder updateMarketOrder(MarketOrder order) {
        Session session = sessionFactory.openSession();
        session.update(order);
        session.refresh(order);
        return order;
    }

    public void deleteMarketOrder(MarketOrder order) {
        Session session = sessionFactory.openSession();
        session.delete(order);
    }
}
