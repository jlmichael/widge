package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.CommandTemplate;
import widge.model.dao.CommandTemplateDAO;

import java.util.List;


public class CommandTemplateDAOImpl implements CommandTemplateDAO {

    private SessionFactory sessionFactory;

    public CommandTemplateDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<CommandTemplate> getCommandTemplates() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM CommandTemplate");
        return (List<CommandTemplate>)query.list();
    }

    public CommandTemplate getCommandTemplateById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM CommandTemplate WHERE id = :id");
        query.setInteger("id", id);
        return (CommandTemplate)query.uniqueResult();
    }
}
