package widge.model.dao.impl;

import junit.framework.TestCase;
import static org.easymock.classextension.EasyMock.*;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Game;
import widge.model.dao.GameDAO;
import widge.model.dao.impl.GameDAOImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameDAOImplTest extends TestCase {
    @Test
    public void testGetGameById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Game WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(game);

        replay(sessionFactory, session, query);

        GameDAO dao = new GameDAOImpl(sessionFactory);
        Game result = dao.getGameById(1);

        assertNotNull(result);
        assertEquals(game, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetGames() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game1 = new Game();
        Game game2 = new Game();
        List<Game> games = new ArrayList<Game>();
        games.add(game1);
        games.add(game2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Game")).andReturn(query);
        expect(query.list()).andReturn(games);

        replay(sessionFactory, session, query);

        GameDAO dao = new GameDAOImpl(sessionFactory);
        List<Game> results = dao.getGames();

        assertNotNull(results);
        assertEquals(games, results);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetGamesModifiedSinceDate() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game1 = new Game();
        Game game2 = new Game();
        List<Game> games = new ArrayList<Game>();
        games.add(game1);
        games.add(game2);
        Date since = new Date();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Game WHERE LastModifiedDate > :date")).andReturn(query);
        expect(query.setDate("date", since)).andReturn(query);
        expect(query.list()).andReturn(games);

        replay(sessionFactory, session, query);

        GameDAO dao = new GameDAOImpl(sessionFactory);
        List<Game> results = dao.getGamesModifiedSinceDate(since);

        assertNotNull(results);
        assertEquals(games, results);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Game game = new Game();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.persist(game);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();
        session.refresh(game);
        expectLastCall();

        replay(sessionFactory, session, transaction);

        GameDAO dao = new GameDAOImpl(sessionFactory);
        Game result = dao.addGame(game);

        assertNotNull(result);
        assertEquals(game, result);

        verify(sessionFactory, session, transaction);
    }

    @Test
    public void testUpdateGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Game game = new Game();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.saveOrUpdate(game);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();
        session.refresh(game);
        expectLastCall();

        replay(sessionFactory, session, transaction);

        GameDAO dao = new GameDAOImpl(sessionFactory);
        Game result = dao.updateGame(game);

        assertNotNull(result);
        assertEquals(game, result);

        verify(sessionFactory, session, transaction);
    }
}
