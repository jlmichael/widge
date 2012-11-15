package widge.model.dao;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.Good;
import widge.model.dao.impl.GoodDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.classextension.EasyMock.*;

public class GoodDAOImplTest extends TestCase {

    @Test
    public void testGetGoods() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Good good1 = new Good();
        Good good2 = new Good();
        List<Good> goods = new ArrayList<Good>();
        goods.add(good1);
        goods.add(good2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Good")).andReturn(query);
        expect(query.list()).andReturn(goods);

        replay(sessionFactory, session, query);

        GoodDAO dao = new GoodDAOImpl(sessionFactory);
        List<Good> result = dao.getGoods();

        assertNotNull(result);
        assertEquals(goods, result);

        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetGoodById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        Good good = new Good();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM Good WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(good);

        replay(sessionFactory, session, query);

        GoodDAO dao = new GoodDAOImpl(sessionFactory);
        Good result = dao.getGoodById(1);

        assertNotNull(result);
        assertEquals(good, result);

        verify(sessionFactory, session, query);
    }
}
