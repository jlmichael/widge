package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.CommandTemplate;
import widge.model.dao.CommandTemplateDAO;

import java.util.List;


/**
 * A class for accessing CommandTemplates.
 */
public class CommandTemplateDAOImpl implements CommandTemplateDAO {

    private SessionFactory sessionFactory;

    /**
     * Create a new DAO instance using the given SessionFactory
     * @param sessionFactory The Hibernate SessionFactory to use
     */
    public CommandTemplateDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get a list of all CommandTemplates.
     * @return A List of all known CommandTemplates
     */
    public List<CommandTemplate> getCommandTemplates() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM CommandTemplate");
        return (List<CommandTemplate>)query.list();
    }

    /**
     * Get the specified CommandTemplate
     * @param id the ID of the CommandTemplate to fetch.
     * @return the requested CommandTemplate
     */
    public CommandTemplate getCommandTemplateById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM CommandTemplate WHERE id = :id");
        query.setInteger("id", id);
        return (CommandTemplate)query.uniqueResult();
    }
}
