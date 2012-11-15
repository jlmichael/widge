package widge.model.dao.impl;

import junit.framework.TestCase;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.junit.Test;
import widge.model.CommandTemplate;
import widge.model.dao.CommandTemplateDAO;
import widge.model.dao.impl.CommandTemplateDAOImpl;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.*;

public class CommandTemplateDAOImplTest extends TestCase {
    @Test
    public void testGetCommands() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        CommandTemplate command1 = new CommandTemplate();
        CommandTemplate command2 = new CommandTemplate();
        List<CommandTemplate> commands = new ArrayList<CommandTemplate>();
        commands.add(command1);
        commands.add(command2);

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM CommandTemplate")).andReturn(query);
        expect(query.list()).andReturn(commands);

        replay(sessionFactory, session, query);

        CommandTemplateDAO dao = new CommandTemplateDAOImpl(sessionFactory);
        List<CommandTemplate> result = dao.getCommandTemplates();

        assertNotNull(result);
        assertEquals(commands, result);
        verify(sessionFactory, session, query);
    }

    @Test
    public void testGetCommandById() throws Exception {
        SessionFactory sessionFactory = createNiceMock(SessionFactory.class);
        Session session = createNiceMock(Session.class);
        Query query = createNiceMock(Query.class);
        CommandTemplate command = new CommandTemplate();

        expect(sessionFactory.openSession()).andReturn(session);
        expect(session.createQuery("FROM CommandTemplate WHERE id = :id")).andReturn(query);
        expect(query.setInteger("id", 1)).andReturn(query);
        expect(query.uniqueResult()).andReturn(command);

        replay(sessionFactory, session, query);

        CommandTemplateDAO dao = new CommandTemplateDAOImpl(sessionFactory);
        CommandTemplate result = dao.getCommandTemplateById(1);

        assertNotNull(result);
        assertEquals(command, result);
        verify(sessionFactory, session, query);
    }
}
