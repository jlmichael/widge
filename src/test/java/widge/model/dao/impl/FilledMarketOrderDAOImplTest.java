package widge.model.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.FilledMarketOrder;
import widge.model.Game;
import widge.model.Player;
import widge.model.dao.FilledMarketOrderDAO;
import widge.model.dao.impl.FilledMarketOrderDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.classextension.EasyMock.*;

public class FilledMarketOrderDAOImplTest extends TestCase {

    @Test
    public void testGetFilledMarketOrdersForGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        List<FilledMarketOrder> orders = new ArrayList<FilledMarketOrder>();
        FilledMarketOrder order1 = new FilledMarketOrder();
        FilledMarketOrder order2 = new FilledMarketOrder();
        orders.add(order1);
        orders.add(order2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM FilledMarketOrder WHERE game = :game")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.list()).andReturn(orders);

        replay(sessionFactory, session, query);

        FilledMarketOrderDAO dao = new FilledMarketOrderDAOImpl(sessionFactory);
        List<FilledMarketOrder> result = dao.getFilledMarketOrdersForGame(game);

        assertNotNull(result);
        assertEquals(orders, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetFilledMarketOrderById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        FilledMarketOrder order = new FilledMarketOrder();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM FilledMarketOrder WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(order);

        replay(sessionFactory, session, query);

        FilledMarketOrderDAO dao = new FilledMarketOrderDAOImpl(sessionFactory);
        FilledMarketOrder result = dao.getFilledMarketOrderById(1);

        assertNotNull(result);
        assertEquals(order, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetFilledMarketOrdersForGameAndSeller() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Player seller = new Player();
        List<FilledMarketOrder> orders = new ArrayList<FilledMarketOrder>();
        FilledMarketOrder order1 = new FilledMarketOrder();
        FilledMarketOrder order2 = new FilledMarketOrder();
        orders.add(order1);
        orders.add(order2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM FilledMarketOrder o WHERE o.game = :game AND o.askOrder.player = :seller")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("seller", seller)).andReturn(query);
        expect(query.list()).andReturn(orders);

        replay(sessionFactory, session, query);

        FilledMarketOrderDAO dao = new FilledMarketOrderDAOImpl(sessionFactory);
        List<FilledMarketOrder> result = dao.getFilledMarketOrdersForGameAndSeller(game, seller);

        assertNotNull(result);
        assertEquals(orders, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetFilledMarketOrdersForGameAndBuyer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Player buyer = new Player();
        List<FilledMarketOrder> orders = new ArrayList<FilledMarketOrder>();
        FilledMarketOrder order1 = new FilledMarketOrder();
        FilledMarketOrder order2 = new FilledMarketOrder();
        orders.add(order1);
        orders.add(order2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM FilledMarketOrder o WHERE o.game = :game AND o.bidOrder.player = :buyer")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("buyer", buyer)).andReturn(query);
        expect(query.list()).andReturn(orders);

        replay(sessionFactory, session, query);

        FilledMarketOrderDAO dao = new FilledMarketOrderDAOImpl(sessionFactory);
        List<FilledMarketOrder> result = dao.getFilledMarketOrdersForGameAndBuyer(game, buyer);

        assertNotNull(result);
        assertEquals(orders, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddFilledMarketOrder() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        FilledMarketOrder order = new FilledMarketOrder();

        expect(sessionFactory.openSession()).andReturn(session);
        session.persist(order);
        expectLastCall();

        replay(sessionFactory, session);

        FilledMarketOrderDAO dao = new FilledMarketOrderDAOImpl(sessionFactory);
        dao.addFilledMarketOrder(order);

        verify(sessionFactory, session);
    }
}
