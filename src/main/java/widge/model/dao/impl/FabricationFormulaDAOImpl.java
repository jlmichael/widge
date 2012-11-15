package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.FabricationFormula;
import widge.model.dao.FabricationFormulaDAO;

import java.util.List;

public class FabricationFormulaDAOImpl implements FabricationFormulaDAO {

    private SessionFactory sessionFactory;

    public FabricationFormulaDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<FabricationFormula> getFabricationFormulas() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FabricationFormula");
        return query.list();
    }

    public FabricationFormula getFabricationFormulaById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FabricationFormula WHERE id = :id");
        query.setInteger("id", id);
        return (FabricationFormula)query.uniqueResult();
    }
}
