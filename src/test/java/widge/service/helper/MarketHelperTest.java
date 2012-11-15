package widge.service.helper;

import junit.framework.TestCase;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Before;
import org.junit.Test;
import widge.model.Game;
import widge.model.Good;
import widge.model.MarketOrder;
import widge.model.Player;
import widge.model.dao.*;
import widge.model.dao.handler.DAOHandler;
import widge.service.Widge;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;


public class MarketHelperTest extends TestCase {

    private Dispatcher dispatcher;
    private GameDAO gameDAO;
    private MarketOrderDAO marketOrderDAO;
    private PlayerDAO playerDAO;

    @Before
    public void setUp() throws Exception {
        dispatcher = MockDispatcherFactory.createDispatcher();
        marketOrderDAO = createNiceMock(MarketOrderDAO.class);
        gameDAO = createNiceMock(GameDAO.class);
        playerDAO = createNiceMock(PlayerDAO.class);
        DAOHandler daoHandler = new DAOHandler();
        daoHandler.setPlayerDAO(playerDAO);
        daoHandler.setGameDAO(gameDAO);
        daoHandler.setMarketOrderDAO(marketOrderDAO);
        dispatcher.getRegistry().addSingletonResource(new Widge(daoHandler));
    }

    @Test
    public void testGetOrders() throws Exception {
        Game game = new Game();
        game.setId(1);
        Good good = new Good();
        good.setId(1);
        Player player = new Player();
        player.setId(1);
        MarketOrder order1 = new MarketOrder();
        order1.setId(1);
        order1.setGame(game);
        order1.setGood(good);
        order1.setPlayer(player);
        MarketOrder order2 = new MarketOrder();
        order2.setId(2);
        order2.setGame(game);
        order2.setGood(good);
        order2.setPlayer(player);
        List<MarketOrder> orders = new ArrayList<MarketOrder>();
        orders.add(order1);
        orders.add(order2);
        expect(gameDAO.getGameById(1)).andReturn(game);
        expect(marketOrderDAO.getMarketOrdersForGame(game)).andReturn(orders);

        replay(marketOrderDAO, gameDAO);

        MockHttpRequest request = MockHttpRequest.get("/game/1/market/marketorder");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("[{\"marketOrder\":{\"game\":\"\\/game\\/1\",\"good\":\"\\/good\\/1\",\"id\":1,\"player\":\"\\/player\\/1\"}},{\"marketOrder\":{\"game\":\"\\/game\\/1\",\"good\":\"\\/good\\/1\",\"id\":2,\"player\":\"\\/player\\/1\"}}]", response.getContentAsString());

        verify(marketOrderDAO, gameDAO);
    }

    @Test
    public void testGetOrdersBySeller() throws Exception {
        Game game = new Game();
        game.setId(1);
        Good good = new Good();
        good.setId(1);
        Player player = new Player();
        player.setId(1);
        MarketOrder order1 = new MarketOrder();
        order1.setGame(game);
        order1.setId(1);
        order1.setGood(good);
        order1.setPlayer(player);
        order1.setOrderType(MarketOrder.MarketOrderType.ASK);
        MarketOrder order2 = new MarketOrder();
        order2.setGame(game);
        order2.setId(2);
        order2.setGood(good);
        order2.setPlayer(player);
        order2.setOrderType(MarketOrder.MarketOrderType.ASK);
        List<MarketOrder> orders = new ArrayList<MarketOrder>();
        orders.add(order1);
        orders.add(order2);
        expect(gameDAO.getGameById(1)).andReturn(game);
        expect(playerDAO.getPlayerById(1)).andReturn(player);
        expect(marketOrderDAO.getMarketOrdersForGameAndSeller(game, player)).andReturn(orders);

        replay(gameDAO, marketOrderDAO, playerDAO);

        MockHttpRequest request = MockHttpRequest.get("/game/1/market/marketorder?seller=1");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("[{\"marketOrder\":{\"game\":\"\\/game\\/1\",\"good\":\"\\/good\\/1\",\"id\":1,\"orderType\":\"ASK\",\"player\":\"\\/player\\/1\"}},{\"marketOrder\":{\"game\":\"\\/game\\/1\",\"good\":\"\\/good\\/1\",\"id\":2,\"orderType\":\"ASK\",\"player\":\"\\/player\\/1\"}}]", response.getContentAsString());

        verify(marketOrderDAO, gameDAO, playerDAO);
    }

    @Test
    public void testGetOrdersByBuyer() throws Exception {
        Game game = new Game();
        game.setId(1);
        Good good = new Good();
        good.setId(1);
        Player player = new Player();
        player.setId(1);
        MarketOrder order1 = new MarketOrder();
        order1.setGame(game);
        order1.setId(1);
        order1.setGood(good);
        order1.setPlayer(player);
        order1.setOrderType(MarketOrder.MarketOrderType.BID);
        MarketOrder order2 = new MarketOrder();
        order2.setGame(game);
        order2.setId(2);
        order2.setGood(good);
        order2.setPlayer(player);
        order2.setOrderType(MarketOrder.MarketOrderType.BID);
        List<MarketOrder> orders = new ArrayList<MarketOrder>();
        orders.add(order1);
        orders.add(order2);
        expect(gameDAO.getGameById(1)).andReturn(game);
        expect(playerDAO.getPlayerById(1)).andReturn(player);
        expect(marketOrderDAO.getMarketOrdersForGameAndBuyer(game, player)).andReturn(orders);

        replay(gameDAO, marketOrderDAO, playerDAO);

        MockHttpRequest request = MockHttpRequest.get("/game/1/market/marketorder?buyer=1");
        MockHttpResponse response = new MockHttpResponse();
        request.accept("application/json");
        request.contentType("application/json");
        dispatcher.invoke(request, response);

        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("[{\"marketOrder\":{\"game\":\"\\/game\\/1\",\"good\":\"\\/good\\/1\",\"id\":1,\"orderType\":\"BID\",\"player\":\"\\/player\\/1\"}},{\"marketOrder\":{\"game\":\"\\/game\\/1\",\"good\":\"\\/good\\/1\",\"id\":2,\"orderType\":\"BID\",\"player\":\"\\/player\\/1\"}}]", response.getContentAsString());

        verify(marketOrderDAO, gameDAO, playerDAO);
    }

    
}
