package widge.service.helper;

import junit.framework.TestCase;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;
import widge.model.Game;
import widge.model.Player;
import widge.model.dao.*;
import widge.model.dao.handler.DAOHandler;
import widge.service.Widge;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;


import java.util.ArrayList;
import java.util.List;

public class GameHelperTest extends TestCase {

    private Dispatcher dispatcher;
    private GameDAO gameDAO;
    private DAOHandler daoHandler;

    @Before
    public void setUp() {
        dispatcher = MockDispatcherFactory.createDispatcher();
        gameDAO = createNiceMock(GameDAO.class);
        daoHandler = new DAOHandler();
        daoHandler.setGameDAO(gameDAO);

        dispatcher.getRegistry().addSingletonResource(new Widge(daoHandler));
    }

    @Test
    public void testGetGames() throws Exception {
        Game game1 = new Game();
        game1.setId(1);
        Game game2 = new Game();
        game2.setId(2);
        Player player1 = new Player();
        player1.setId(666);
        Player player2 = new Player();
        player2.setId(777);
        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        game2.setPlayersIn(players);
        List<Game> games = new ArrayList<Game>();
        games.add(game1);
        games.add(game2);
        expect(gameDAO.getGames()).andReturn(games);

        replay(gameDAO);

        MockHttpRequest request = MockHttpRequest.get("/game");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("[{\"game\":{\"id\":1}},{\"game\":{\"id\":2,\"players\":[\"\\/player\\/666\",\"\\/player\\/777\"]}}]", response.getContentAsString());

        verify(gameDAO);
    }

    @Test
    public void testGetGameById() throws Exception {
        Game game = new Game();
        game.setId(1);
        expect(gameDAO.getGameById(1)).andReturn(game);

        replay(gameDAO);

        MockHttpRequest request = MockHttpRequest.get("/game/1");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("{\"game\":{\"id\":1}}", response.getContentAsString());

        verify(gameDAO);
    }

    @Test
    public void testAddGame() throws Exception {
        Game game = new Game();
        game.setId(1);
        expect(gameDAO.addGame((Game)anyObject())).andReturn(game);

        replay(gameDAO);

        MockHttpRequest request = MockHttpRequest.post("/game");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        request.content("{\"game\":{\"id\":1}}".getBytes());
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("{\"game\":{\"id\":1}}", response.getContentAsString());
        verify(gameDAO);
    }
}
