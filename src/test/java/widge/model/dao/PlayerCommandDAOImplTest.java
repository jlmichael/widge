package widge.model.dao;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerCommand;
import widge.model.Turn;
import widge.model.dao.impl.PlayerCommandDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.classextension.EasyMock.*;

public class PlayerCommandDAOImplTest extends TestCase {

    @Test
    public void testGetPlayerCommandById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        PlayerCommand pc = new PlayerCommand();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerCommand WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(pc);

        replay(sessionFactory, session, query);

        PlayerCommandDAO dao = new PlayerCommandDAOImpl(sessionFactory);
        PlayerCommand result = dao.getPlayerCommandById(1);

        assertNotNull(result);
        assertEquals(pc, result);

        verify(sessionFactory, session, query);

    }

    @Test
    public void testGetPlayerCommandsForGameAndPlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Player player = new Player();
        List<PlayerCommand> pcs = new ArrayList<PlayerCommand>();
        PlayerCommand pc1 = new PlayerCommand();
        PlayerCommand pc2 = new PlayerCommand();
        pcs.add(pc1);
        pcs.add(pc2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerCommand WHERE game = :game AND player = :player")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("player", player)).andReturn(query);
        expect(query.list()).andReturn(pcs);

        replay(sessionFactory, session, query);

        PlayerCommandDAO dao = new PlayerCommandDAOImpl(sessionFactory);
        List<PlayerCommand> result = dao.getPlayerCommandsForGameAndPlayer(game, player);

        assertNotNull(result);
        assertEquals(pcs, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetPlayerCommandsForGameAndTurn() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Game game = new Game();
        Turn turn = new Turn();
        List<PlayerCommand> pcs = new ArrayList<PlayerCommand>();
        PlayerCommand pc1 = new PlayerCommand();
        PlayerCommand pc2 = new PlayerCommand();
        pcs.add(pc1);
        pcs.add(pc2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM PlayerCommand WHERE game = :game AND turn = :turn")).andReturn(query);
        expect(query.setEntity("game", game)).andReturn(query);
        expect(query.setEntity("turn", turn)).andReturn(query);
        expect(query.list()).andReturn(pcs);

        replay(sessionFactory, session, query);

        PlayerCommandDAO dao = new PlayerCommandDAOImpl(sessionFactory);
        List<PlayerCommand> result = dao.getPlayerCommandsForGameAndTurn(game, turn);

        assertNotNull(result);
        assertEquals(pcs, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testAddPlayerCommand() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerCommand pc = new PlayerCommand();

        expect(sessionFactory.openSession()).andReturn(session);
        session.persist(pc);
        expectLastCall();

        replay(sessionFactory, session);

        PlayerCommandDAO dao = new PlayerCommandDAOImpl(sessionFactory);
        dao.addPlayerCommand(pc);

        verify(sessionFactory, session);
    }

    @Test
    public void testUpdatePlayerCommand() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerCommand pc = new PlayerCommand();

        expect(sessionFactory.openSession()).andReturn(session);
        session.update(pc);
        expectLastCall();
        session.refresh(pc);
        expectLastCall();

        replay(sessionFactory, session);
        
        PlayerCommandDAO dao = new PlayerCommandDAOImpl(sessionFactory);
        PlayerCommand result = dao.updatePlayerCommand(pc);

        assertNotNull(result);
        assertEquals(pc, result);

        verify(sessionFactory, session);
    }

    @Test
    public void testDeletePlayerCommand() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        PlayerCommand pc = new PlayerCommand();

        expect(sessionFactory.openSession()).andReturn(session);
        session.delete(pc);
        expectLastCall();

        replay(sessionFactory, session);

        PlayerCommandDAO dao = new PlayerCommandDAOImpl(sessionFactory);
        dao.deletePlayerCommand(pc);

        verify(sessionFactory, session);
    }
}
