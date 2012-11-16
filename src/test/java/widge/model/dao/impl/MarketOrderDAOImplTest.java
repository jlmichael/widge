package widge.model.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Game;
import widge.model.MarketOrder;
import widge.model.Player;
import widge.model.dao.MarketOrderDAO;
import widge.model.dao.impl.MarketOrderDAOImpl;

import static org.easymock.classextension.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

public class MarketOrderDAOImplTest extends TestCase {

    @Test
    public void testGetMarketOrdersForGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        List<MarketOrder> orders = new ArrayList<MarketOrder>();
        MarketOrder order1 = new MarketOrder();
        MarketOrder order2 = new MarketOrder();
        orders.add(order1);
        orders.add(order2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM MarketOrder WHERE game = :game")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.list()).andReturn(orders);

        replay(sessionFactory, session, query);

        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        List<MarketOrder> result = dao.getMarketOrdersForGame(game);

        assertNotNull(result);
        assertEquals(orders, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetMarketOrdersForGameAndSeller() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Player seller = new Player();
        List<MarketOrder> orders = new ArrayList<MarketOrder>();
        MarketOrder order1 = new MarketOrder();
        MarketOrder order2 = new MarketOrder();
        orders.add(order1);
        orders.add(order2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM MarketOrder WHERE game = :game AND player = :seller AND orderType = :type")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("seller", seller)).andReturn(query);
        expect(query.setEntity("type", MarketOrder.MarketOrderType.ASK)).andReturn(query);
        expect(query.list()).andReturn(orders);

        replay(sessionFactory, session, query);

        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        List<MarketOrder> result = dao.getMarketOrdersForGameAndSeller(game, seller);

        assertNotNull(result);
        assertEquals(orders, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetMarketOrdersForGameAndBuyer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Player buyer = new Player();
        List<MarketOrder> orders = new ArrayList<MarketOrder>();
        MarketOrder order1 = new MarketOrder();
        MarketOrder order2 = new MarketOrder();
        orders.add(order1);
        orders.add(order2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM MarketOrder WHERE game = :game AND player = :buyer AND orderType = :type")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("buyer", buyer)).andReturn(query);
        expect(query.setEntity("type", MarketOrder.MarketOrderType.BID)).andReturn(query);
        expect(query.list()).andReturn(orders);

        replay(sessionFactory, session, query);

        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        List<MarketOrder> result = dao.getMarketOrdersForGameAndBuyer(game, buyer);

        assertNotNull(result);
        assertEquals(orders, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetMarketOrderById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        MarketOrder order = new MarketOrder();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM MarketOrder WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(order);

        replay(sessionFactory, session, query);

        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        MarketOrder result = dao.getMarketOrderById(1);

        assertNotNull(result);
        assertEquals(order, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddMarketOrder() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        MarketOrder order = new MarketOrder();

        expect(sessionFactory.openSession()).andReturn(session);
        session.persist(order);
        expectLastCall();

        replay(sessionFactory, session);

        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        dao.addMarketOrder(order);

        verify(sessionFactory, session);
    }

    @Test
    public void testUpdateMarketOrder() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        MarketOrder order = new MarketOrder();

        expect(sessionFactory.openSession()).andReturn(session);
        session.update(order);
        expectLastCall();
        session.refresh(order);
        expectLastCall();

        replay(sessionFactory, session);

        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        dao.updateMarketOrder(order);

        verify(sessionFactory, session);
    }

    @Test
    public void testDeleteMarketOrder() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        MarketOrder order = new MarketOrder();

        expect(sessionFactory.openSession()).andReturn(session);
        session.delete(order);
        expectLastCall();

        replay(sessionFactory, session);
        
        MarketOrderDAO dao = new MarketOrderDAOImpl(sessionFactory);
        dao.deleteMarketOrder(order);

        verify(sessionFactory, session);
    }
}
