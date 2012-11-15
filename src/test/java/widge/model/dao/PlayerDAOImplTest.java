package widge.model.dao;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Player;
import widge.model.dao.impl.PlayerDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImplTest extends TestCase {
    @Test
    public void testGetPlayerById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Player player = new Player();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Player WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 666)).andReturn(query);
        expect(query.uniqueResult()).andReturn(player);
        replay(sessionFactory, session, query);

        PlayerDAOImpl dao = new PlayerDAOImpl(sessionFactory);
        Player result = dao.getPlayerById(666);
        assertEquals(result, player);
        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Player player = new Player();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Player WHERE name = :name")).andReturn(query);
        expect(query.setString("name", "foo")).andReturn(query);
        expect(query.uniqueResult()).andReturn(player);
        replay(sessionFactory, session, query);

        PlayerDAOImpl dao = new PlayerDAOImpl(sessionFactory);
        Player result = dao.getPlayerByName("foo");
        assertEquals(result, player);
        verify(sessionFactory, session, query);

    }

    @Test
    public void testGetPlayers() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Player player = new Player();
        List<Player> players = new ArrayList<Player>();
        players.add(player);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Player")).andReturn(query);
        expect(query.list()).andReturn(players);
        replay(sessionFactory, session, query);

        PlayerDAOImpl dao = new PlayerDAOImpl(sessionFactory);
        List<Player> result = dao.getPlayers();
        assertEquals(result, players);
        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddPlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Player player = new Player();
        player.setPassword("foo");

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.persist(player);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();
        session.refresh(player);
        expectLastCall();

        replay(sessionFactory, session, transaction);

        PlayerDAOImpl dao = new PlayerDAOImpl(sessionFactory);
        Player result = dao.addPlayer(player);
        assertEquals(result, player);
        assertEquals("9ecd99d0d4f7293e69950aee5a6b7f84", player.getPassword());
        verify(sessionFactory, session, transaction);
    }

    @Test
    public void testUpdatePlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Player player = new Player();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.update(player);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();
        session.refresh(player);
        expectLastCall();

        replay(sessionFactory, session, transaction);

        PlayerDAOImpl dao = new PlayerDAOImpl(sessionFactory);
        Player result = dao.updatePlayer(player);

        assertNotNull(result);
        assertEquals(player, result);
        verify(sessionFactory, session, transaction);
    }

    @Test
    public void testDeletePlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Player player = new Player();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.delete(player);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();

        replay(sessionFactory, session, transaction);

        PlayerDAOImpl dao = new PlayerDAOImpl(sessionFactory);
        dao.deletePlayer(player);
        verify(sessionFactory, session, transaction);
    }
}
