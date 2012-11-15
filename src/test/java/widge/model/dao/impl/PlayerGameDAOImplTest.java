package widge.model.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGame;
import widge.model.dao.PlayerGameDAO;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.classextension.EasyMock.createNiceMock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

public class PlayerGameDAOImplTest extends TestCase {
    
    @Test
    public void testGetPlayerGameById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        PlayerGame pg = new PlayerGame();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerGame WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(pg);

        replay(sessionFactory, session, query);

        PlayerGameDAO dao = new PlayerGameDAOImpl(sessionFactory);
        PlayerGame result = dao.getPlayerGameById(1);

        assertNotNull(result);
        assertEquals(pg, result);

        verify(sessionFactory, session, query);

    }

    @Test
    public void testGetPlayerGamesForGameAndPlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Player player = new Player();
        PlayerGame pg = new PlayerGame();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerGame WHERE game = :game AND player = :player")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("player", player)).andReturn(query);
        expect(query.uniqueResult()).andReturn(pg);

        replay(sessionFactory, session, query);

        PlayerGameDAO dao = new PlayerGameDAOImpl(sessionFactory);
        PlayerGame result = dao.getPlayerGameForGameAndPlayer(game, player);

        assertNotNull(result);
        assertEquals(pg, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddPlayerGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerGame pg = new PlayerGame();

        expect(sessionFactory.openSession()).andReturn(session);
        session.persist(pg);
        expectLastCall();
        session.refresh(pg);
        expectLastCall();

        replay(sessionFactory, session);

        PlayerGameDAO dao = new PlayerGameDAOImpl(sessionFactory);
        PlayerGame result = dao.addPlayerGame(pg);

        assertNotNull(result);
        assertEquals(pg, result);

        verify(sessionFactory, session);
    }

    @Test
    public void testUpdatePlayerGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerGame pg = new PlayerGame();

        expect(sessionFactory.openSession()).andReturn(session);
        session.update(pg);
        expectLastCall();
        session.refresh(pg);
        expectLastCall();

        replay(sessionFactory, session);
        
        PlayerGameDAO dao = new PlayerGameDAOImpl(sessionFactory);
        PlayerGame result = dao.updatePlayerGame(pg);

        assertNotNull(result);
        assertEquals(pg, result);

        verify(sessionFactory, session);
    }

    @Test
    public void testDeletePlayerGame() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerGame pg = new PlayerGame();

        expect(sessionFactory.openSession()).andReturn(session);
        session.delete(pg);
        expectLastCall();

        replay(sessionFactory, session);

        PlayerGameDAO dao = new PlayerGameDAOImpl(sessionFactory);
        dao.deletePlayerGame(pg);

        verify(sessionFactory, session);
    }
    
}
