package widge.service;

import org.apache.log4j.Logger;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGame;
import widge.model.dao.*;
import widge.model.dao.handler.DAOHandler;
import widge.service.helper.GameHelper;
import widge.service.helper.PlayerGameHelper;
import widge.service.helper.PlayerHelper;

import javax.ws.rs.*;

@Path("/")
public class Widge {
    private static Logger logger = Logger.getLogger(Widge.class);
    public static final String HTTP_HEADER_WIDGE_PLAYER_ID = "Widge-Player-Id";
    public static final String HTTP_HEADER_WIDGE_PLAYER_TOKEN = "Widge-Player-Token";

    // The DAO handler for the application
    private DAOHandler daoHandler;

    public Widge(DAOHandler daoHandler) {
        this.daoHandler = daoHandler;
    }

    //////////////////////////////////
    // Player endpoints
    //////////////////////////////////
    @Path("/player")
    public PlayerHelper getPlayer() {
        logger.info("Caught a player endpoint, creating helper to service");
        WidgeRequestStore requestStore = new WidgeRequestStore();
        return new PlayerHelper(daoHandler, requestStore);
    }

    //////////////////////////////////
    // Game endpoints
    //////////////////////////////////
    @Path("/game")
    public GameHelper getGame() {
        logger.info("Caught a game endpoint, creating helper to service");
        WidgeRequestStore requestStore = new WidgeRequestStore();
        return new GameHelper(daoHandler, requestStore);
    }

    //////////////////////////////////
    // PlayerGame endpoints
    //////////////////////////////////
    @Path("/player/{playerId}/game/{gameId}")
    public PlayerGameHelper getPlayerGame(@PathParam("playerId") String playerId, @PathParam("gameId") String gameId) {
        logger.info("Caught a playergame endpoint, creating helper to service");
        WidgeRequestStore requestStore = new WidgeRequestStore();
        // Get the associated PlayerGame
        GameDAO gameDAO = daoHandler.getGameDAO();
        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        PlayerGameDAO playerGameDAO = daoHandler.getPlayerGameDAO();
        Integer idInt;

        try {
            idInt = Integer.valueOf(playerId);
        } catch (NumberFormatException e) {
            logger.error("Could not format string id: " + playerId + " to integer", e);
            return null;
        }
        Player player = playerDAO.getPlayerById(idInt);
        if(player == null) {
            logger.error("Could not find player with id: " + idInt);
            return null;
        }
        requestStore.setRequestPlayer(player);

        try {
            idInt = Integer.valueOf(gameId);
        } catch (NumberFormatException e) {
            logger.error("Could not format string id: " + gameId + " to integer", e);
            return null;
        }
        Game game = gameDAO.getGameById(idInt);
        if(game == null) {
            logger.error("Could not find game with id: " + idInt);
            return null;
        }
        requestStore.setRequestGame(game);

        PlayerGame playerGame = playerGameDAO.getPlayerGameForGameAndPlayer(game, player);
        if(playerGame == null) {
            logger.warn("Player: " + playerId + " is not playing in game: " + gameId);
            return null;
        }
        requestStore.setRequestPlayerGame(playerGame);

        return new PlayerGameHelper(daoHandler, requestStore);

    }

}
