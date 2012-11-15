package widge.model;

import junit.framework.TestCase;

import java.util.Date;

import static org.easymock.classextension.EasyMock.createNiceMock;
import static org.easymock.classextension.EasyMock.replay;

public class PlayerTest extends TestCase {

    public void testPlayer() throws Exception {
        Player p = new Player();
        assertNotNull(p);
        assertNull(p.getId());
        assertNull(p.getName());
        assertNull(p.getEmail());
        assertNull(p.getPassword());
        assertNull(p.getLastModified());

        p.setId(1);
        p.setName("foo");
        p.setEmail("bar");
        p.setPassword("baz");
        p.setLastModified(new Date(123));
        assertEquals(new Integer(1), p.getId());
        assertEquals("foo", p.getName());
        assertEquals("bar", p.getEmail());
        assertEquals("baz", p.getPassword());
        assertEquals(123, p.getLastModified().getTime());

    }

}
