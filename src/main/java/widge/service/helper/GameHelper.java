package widge.service.helper;

import org.apache.log4j.Logger;
import widge.model.Game;
import widge.model.dao.GameDAO;
import widge.model.dao.handler.DAOHandler;
import widge.service.WidgeRequestStore;

import javax.ws.rs.*;
import java.util.List;

public class GameHelper extends AbstractHelper {

    private static Logger logger = Logger.getLogger(GameHelper.class);

    public GameHelper(DAOHandler daoHandler, WidgeRequestStore requestStore) {
        super(daoHandler, requestStore);
    }

    @GET
    @Produces("application/json")
    public List<Game> getGames() {
        logger.info("GameHelper servicing a call to getGames");
        GameDAO gameDAO = daoHandler.getGameDAO();
        return gameDAO.getGames();
    }

    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Game getGameById(@PathParam("id") String id) {
        logger.info("GameHelper servicing a call to getGameById");
        Integer idInt;
        try {
            idInt = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.error("Could not format string id: " + id + " to integer", e);
            return null;
        }

        GameDAO gameDAO = daoHandler.getGameDAO();
        return gameDAO.getGameById(idInt);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Game addGame(Game game) {
        logger.info("GameHelper servicing a call to addGame");
        GameDAO gameDAO = daoHandler.getGameDAO();
        return gameDAO.addGame(game);
    }

    ////////////////////
    // Market endpoints
    ////////////////////
    @Path("/{id}/market")
    public MarketHelper getMarket(@PathParam("id") String id) {
        logger.info("Caught a market endpoint, creating helper to service");
        // Get the game the request refers
        Game game = getGameById(id);
        requestStore.setRequestGame(game);
        if(game == null) {
            logger.error("Game does not exist: " + id);
            return null;
        }


        MarketHelper marketHelper = new MarketHelper(daoHandler, requestStore);
        return marketHelper; 
    }
}
