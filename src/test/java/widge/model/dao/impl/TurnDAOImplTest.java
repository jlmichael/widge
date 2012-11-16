package widge.model.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Game;
import widge.model.Turn;
import widge.model.dao.TurnDAO;
import widge.model.dao.impl.TurnDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.classextension.EasyMock.*;

public class TurnDAOImplTest extends TestCase {
    @Test
    public void testGetTurnById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Turn turn = new Turn();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Turn WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(turn);

        replay(sessionFactory, session, query);

        TurnDAO dao = new TurnDAOImpl(sessionFactory);
        Turn result = dao.getTurnById(1);

        assertNotNull(result);
        assertEquals(turn, result);

        verify(sessionFactory, session, query);

    }

    @Test
    public void testGetTurnsForGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        List<Turn> turns = new ArrayList<Turn>();
        Turn turn1 = new Turn();
        Turn turn2 = new Turn();
        turns.add(turn1);
        turns.add(turn2);
        Game game = new Game();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Turn WHERE game = :game")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.list()).andReturn(turns);

        replay(sessionFactory, session, query);

        TurnDAO dao = new TurnDAOImpl(sessionFactory);
        List<Turn> result = dao.getTurnsForGame(game);

        assertNotNull(result);
        assertEquals(turns, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetNextTurnForGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Turn turn = new Turn();
        Game game = new Game();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Turn WHERE game = :game AND startTime IS NULL ORDER BY turnNumber")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.uniqueResult()).andReturn(turn);

        replay(sessionFactory, session, query);

        TurnDAO dao = new TurnDAOImpl(sessionFactory);
        Turn result = dao.getNextTurnForGame(game);

        assertNotNull(result);
        assertEquals(turn, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddTurn() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Turn turn = new Turn();

        expect(sessionFactory.openSession()).andReturn(session);
        session.persist(turn);
        expectLastCall();

        replay(sessionFactory, session);

        TurnDAO dao = new TurnDAOImpl(sessionFactory);
        dao.addTurn(turn);

        verify(sessionFactory, session);
    }

    @Test
    public void testUpdateTurn() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Turn turn = new Turn();

        expect(sessionFactory.openSession()).andReturn(session);
        session.update(turn);
        expectLastCall();
        session.refresh(turn);
        expectLastCall();

        replay(sessionFactory, session);

        TurnDAO dao = new TurnDAOImpl(sessionFactory);
        Turn result = dao.updateTurn(turn);

        assertNotNull(result);
        assertEquals(turn, result);

        verify(sessionFactory, session);
    }
}
