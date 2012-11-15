package widge.model.dao;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Player;
import widge.model.Token;
import widge.model.dao.impl.TokenDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;


public class TokenDAOImplTest extends TestCase {
    @Test
    public void testAddToken() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Token token = new Token();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.persist(token);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();

        replay(sessionFactory, session, transaction);

        TokenDAOImpl dao = new TokenDAOImpl(sessionFactory);
        dao.addToken(token);
        verify(sessionFactory, session, transaction);
    }

    @Test
    public void testGetTokensByPlayer() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Player player = new Player();
        Token token = new Token();
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(token);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Token WHERE player = :player")).andReturn(query);
        expect(query.setEntity("player", player)).andReturn(query);
        expect(query.list()).andReturn(tokens);

        replay(sessionFactory, session, query);

        TokenDAOImpl dao = new TokenDAOImpl(sessionFactory);
        List<Token> result = dao.getTokensByPlayer(player);
        assertEquals(tokens, result);
        verify(sessionFactory, session, query);
    }

    @Test
    public void testDeleteToken() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Transaction transaction = createNiceMock(Transaction.class);
        Token token = new Token();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.beginTransaction()).andReturn(transaction);
        session.delete(token);
        expectLastCall();
        expect(session.getTransaction()).andReturn(transaction);
        transaction.commit();
        expectLastCall();

        replay(sessionFactory, session, transaction);

        TokenDAOImpl dao = new TokenDAOImpl(sessionFactory);
        dao.deleteToken(token);
        verify(sessionFactory, session, transaction);

    }

    @Test
    public void testGetTokenByTokenKey() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Token token = new Token();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Token WHERE tokenKey = :tokenKey")).andReturn(query);
        expect(query.setString("tokenKey", "foo")).andReturn(query);
        expect(query.uniqueResult()).andReturn(token);

        replay(sessionFactory, session, query);

        TokenDAOImpl dao = new TokenDAOImpl(sessionFactory);
        Token result = dao.getTokenByTokenKey("foo");
        assertEquals(token, result);
        verify(sessionFactory, session, query);
    }
}
