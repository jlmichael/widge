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
import widge.model.Token;
import widge.model.dao.*;
import widge.model.dao.handler.DAOHandler;
import widge.service.Widge;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

public class PlayerHelperTest extends TestCase {

    private Dispatcher dispatcher;
    private PlayerDAO playerDAO;
    private TokenDAO tokenDAO;

    @Before
    public void setUp() throws Exception {
        dispatcher = MockDispatcherFactory.createDispatcher();
        playerDAO = createNiceMock(PlayerDAO.class);
        tokenDAO = createNiceMock(TokenDAO.class);
        DAOHandler daoHandler = new DAOHandler();
        daoHandler.setPlayerDAO(playerDAO);
        daoHandler.setTokenDAO(tokenDAO);

        dispatcher.getRegistry().addSingletonResource(new Widge(daoHandler));
    }

    @Test
    public void testGetPlayers() throws Exception {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setName("foo");
        player2.setName("bar");
        Game game1 = new Game();
        game1.setName("some game");
        game1.setId(10);
        Game game2 = new Game();
        game2.setName("another game");
        game2.setId(666);
        List<Game> games = new ArrayList<Game>();
        games.add(game1);
        games.add(game2);
        player2.setGamesIn(games);
        List<Player> players = new ArrayList<Player>();
        players.add(player1);
        players.add(player2);
        expect(playerDAO.getPlayers()).andReturn(players);

        replay(playerDAO);

        MockHttpRequest request = MockHttpRequest.get("/player");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("[{\"player\":{\"name\":\"foo\"}},{\"player\":{\"games\":[\"\\/game\\/10\",\"\\/game\\/666\"],\"name\":\"bar\"}}]", response.getContentAsString());
    }

    @Test
    public void testLoginPlayer() throws Exception {
        Player serverPlayer = new Player();
        serverPlayer.setPassword("9ecd99d0d4f7293e69950aee5a6b7f84");
        Token oldToken = new Token();
        List<Token> oldTokens = new ArrayList<Token>();
        oldTokens.add(oldToken);

        expect(playerDAO.getPlayerById(2)).andReturn(serverPlayer);
        expect(tokenDAO.getTokensByPlayer(serverPlayer)).andReturn(oldTokens);
        tokenDAO.deleteToken(oldToken);
        expectLastCall();
        tokenDAO.addToken((Token)anyObject());
        expectLastCall();

        replay(playerDAO, tokenDAO);

        MockHttpRequest request = MockHttpRequest.post("/player/2/token");
        request.content("{\"player\":{\"email\":\"chachi@mufanga.com\",\"id\":2,\"lastModified\":\"2011-05-18T21:40:19-07:00\",\"name\":\"hamscray\",\"password\":\"foo\"}}".getBytes());
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        verify(playerDAO, tokenDAO);
    }

    @Test
    public void testGetPlayerById() throws Exception {
        Player player = new Player();
        player.setId(1);
        player.setName("foo");
        player.setPassword("shouldn't see this");

        expect(playerDAO.getPlayerById(1)).andReturn(player);

        replay(playerDAO);

        MockHttpRequest request = MockHttpRequest.get("/player/1");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals("{\"player\":{\"id\":1,\"name\":\"foo\"}}", response.getContentAsString());
        verify(playerDAO);
    }

    @Test
    public void testAddPlayer() throws Exception {
        Player player = new Player();
        player.setPassword("foo");
        expect(playerDAO.addPlayer((Player)anyObject())).andReturn(player);

        replay(playerDAO);

        MockHttpRequest request = MockHttpRequest.post("/player");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        request.content("{\"player\":{\"password\":\"foo\"}}".getBytes());
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("{\"player\":{\"password\":\"foo\"}}", response.getContentAsString());
        verify(playerDAO);
    }

    @Test
    public void testDeletePlayer() throws Exception {
        Player player = new Player();
        player.setId(1);
        expect(playerDAO.getPlayerById(1)).andReturn(player);
        playerDAO.deletePlayer((Player)anyObject());
        expectLastCall();

        replay(playerDAO);

        MockHttpRequest request = MockHttpRequest.delete("/player/1");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        request.header(Widge.HTTP_HEADER_WIDGE_PLAYER_ID, "1");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(204, response.getStatus());
        verify(playerDAO);
    }

    @Test
    public void testInvalidDeletePlayer() throws Exception {
        Player player = new Player();
        player.setId(1);
        expect(playerDAO.getPlayerById(1)).andReturn(player);
        replay(playerDAO);

        MockHttpRequest request = MockHttpRequest.delete("/player/1");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        request.header(Widge.HTTP_HEADER_WIDGE_PLAYER_ID, "2");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(204, response.getStatus());
        verify(playerDAO);
    }

}


