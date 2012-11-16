package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.FabricationFormula;
import widge.model.dao.FabricationFormulaDAO;

import java.util.List;

/**
 * A class for accessing FabricationFormulas
 */
public class FabricationFormulaDAOImpl implements FabricationFormulaDAO {

    private SessionFactory sessionFactory;

    /**
     * Create a new DAO instance using the given SessionFactory
     * @param sessionFactory The Hibernate SessionFactory to use
     */    
    public FabricationFormulaDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get a list of all FabricationFormulas
     * @return A List of all FabricationFormulas
     */
    public List<FabricationFormula> getFabricationFormulas() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FabricationFormula");
        return query.list();
    }

    /**
     * Get a single FabricationFormula with the given ID
     * @param id The ID of the FabricationFormula to fetch
     * @return the requested FabricationFormula
     */
    public FabricationFormula getFabricationFormulaById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM FabricationFormula WHERE id = :id");
        query.setInteger("id", id);
        return (FabricationFormula)query.uniqueResult();
    }
}
