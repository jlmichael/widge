package widge.model.dao;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.FabricationFormula;
import widge.model.dao.impl.FabricationFormulaDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.classextension.EasyMock.*;

public class FabricationFormulaDAOImplTest extends TestCase {

    public void testGetFabricationFormulas() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        FabricationFormula formula1 = new FabricationFormula();
        FabricationFormula formula2 = new FabricationFormula();
        List<FabricationFormula> formulas = new ArrayList<FabricationFormula>();
        formulas.add(formula1);
        formulas.add(formula2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM FabricationFormula")).andReturn(query);
        expect(query.list()).andReturn(formulas);

        replay(sessionFactory, session, query);

        FabricationFormulaDAO dao = new FabricationFormulaDAOImpl(sessionFactory);
        List<FabricationFormula> result = dao.getFabricationFormulas();

        assertEquals(formulas, result);
        verify(sessionFactory, session, query);
    }

    public void testGetFabricationFormula() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        FabricationFormula formula = new FabricationFormula();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM FabricationFormula WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(formula);

        replay(sessionFactory, session, query);

        FabricationFormulaDAO dao = new FabricationFormulaDAOImpl(sessionFactory);
        FabricationFormula result = dao.getFabricationFormulaById(new Integer(1));

        assertEquals(formula, result);
        verify(sessionFactory, session, query);
    }
}
