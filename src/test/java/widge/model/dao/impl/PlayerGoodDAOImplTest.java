package widge.model.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGood;
import widge.model.dao.PlayerGoodDAO;
import widge.model.dao.impl.PlayerGoodDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.classextension.EasyMock.*;

public class PlayerGoodDAOImplTest extends TestCase {

    @Test
    public void testGetPlayerGoodById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        PlayerGood pg = new PlayerGood();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerGood WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(pg);

        replay(sessionFactory, session, query);

        PlayerGoodDAO dao = new PlayerGoodDAOImpl(sessionFactory);
        PlayerGood result = dao.getPlayerGoodById(1);

        assertNotNull(result);
        assertEquals(pg, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetPlayerGoodsForGameAndPlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        List<PlayerGood> playerGoods = new ArrayList<PlayerGood>();
        PlayerGood pg1 = new PlayerGood();
        PlayerGood pg2 = new PlayerGood();
        playerGoods.add(pg1);
        playerGoods.add(pg2);
        Game game = new Game();
        Player player = new Player();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerGood WHERE game = :game AND player = :player")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("player", player)).andReturn(query);
        expect(query.list()).andReturn(playerGoods);

        replay(sessionFactory, session, query);

        PlayerGoodDAO dao = new PlayerGoodDAOImpl(sessionFactory);
        List<PlayerGood> result = dao.getPlayerGoodsForGameAndPlayer(game, player);

        assertNotNull(result);
        assertEquals(playerGoods, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddPlayerGood() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerGood pg1 = new PlayerGood();

        expect(sessionFactory.openSession()).andReturn(session);
        session.persist(pg1);
        expectLastCall();

        replay(sessionFactory, session);

        PlayerGoodDAO dao = new PlayerGoodDAOImpl(sessionFactory);
        dao.addPlayerGood(pg1);

        verify(sessionFactory, session);
    }

    @Test
    public void testUpdatePlayerGood() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerGood pg1 = new PlayerGood();

        expect(sessionFactory.openSession()).andReturn(session);
        session.update(pg1);
        expectLastCall();
        session.refresh(pg1);
        expectLastCall();

        replay(sessionFactory, session);

        PlayerGoodDAO dao = new PlayerGoodDAOImpl(sessionFactory);
        PlayerGood result = dao.updatePlayerGood(pg1);

        assertNotNull(result);
        assertEquals(pg1, result);

        verify(sessionFactory, session);
    }

    @Test
    public void testDeletePlayerGood() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerGood pg1 = new PlayerGood();

        expect(sessionFactory.openSession()).andReturn(session);
        session.delete(pg1);
        expectLastCall();

        replay(sessionFactory, session);
        
        PlayerGoodDAO dao = new PlayerGoodDAOImpl(sessionFactory);
        dao.deletePlayerGood(pg1);

        verify(sessionFactory, session);
    }
}
