package widge.service.helper;

import org.apache.log4j.Logger;
import widge.model.Game;
import widge.model.Player;
import widge.model.Token;
import widge.model.dao.PlayerDAO;
import widge.model.dao.TokenDAO;
import widge.model.dao.handler.DAOHandler;
import widge.service.Widge;
import widge.service.WidgeRequestStore;
import widge.util.Auth;
import widge.util.RequiresAuthorization;

import javax.ws.rs.*;
import java.util.List;
import java.util.UUID;

public class PlayerHelper extends AbstractHelper {

    private static Logger logger = Logger.getLogger(PlayerHelper.class);

    public PlayerHelper(DAOHandler daoHandler, WidgeRequestStore requestStore) {
        super(daoHandler, requestStore);
    }

    // Request an auth token
    @POST
    @Path("/{id}/token/")
    @Consumes("application/json")
    @Produces("application/json")
    public String loginPlayer(@PathParam("id") String id, Player player) {
        logger.info("PlayerHelper servicing call to loginPlayer");

        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        Player serverPlayer = playerDAO.getPlayerById(Integer.valueOf(id));
        if(serverPlayer == null) {
            logger.error("Could not authenticate player, no player with id: " + id);
            return null;
        }

        if(!Auth.authPlayer(serverPlayer, player.getPassword())) {
            return null;
        }

        // Ok, they have authenticated.  Delete any existing Tokens for this player, then create a new Token
        // and return it's key
        TokenDAO tokenDAO = daoHandler.getTokenDAO();
        List<Token> playerTokens = tokenDAO.getTokensByPlayer(serverPlayer);
        if(playerTokens != null && playerTokens.size() != 0) {
            for(Token playerToken : playerTokens) {
                tokenDAO.deleteToken(playerToken);
            }
        }

        Token token = new Token();
        token.setPlayer(serverPlayer);
        String tokenKey = UUID.randomUUID().toString();
        token.setTokenKey(tokenKey);
        token.updateTokenExpiration(Auth.TOKEN_TIMEOUT_IN_SECONDS);
        tokenDAO.addToken(token);

        return tokenKey;

    }

    // Get a list of all players
    @GET
    @Produces("application/json")
    public List<Player> getPlayers() {
        logger.info("PlayerHelper servicing a call to getPlayers");
        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        List<Player> players = playerDAO.getPlayers();
//        // Wipe out the passwords before returning to end user
        for(Player player : players) {
            player.setPassword(null);
        }
        return players;
    }

    // Get a single player by id
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Player getPlayerById(@PathParam("id") String id) {
        logger.info("PlayerHelper servicing a cal to getPlayerById");
        Integer idInt;
        try {
            idInt = Integer.valueOf(id);
        } catch (NumberFormatException e) {
            logger.error("Could not format string id: " + id + " to integer", e);
            return null;
        }

        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        Player player = playerDAO.getPlayerById(idInt);
        // Wipe out the password field before returning to end user
        player.setPassword(null);

        // For each game I'm in, clear the game's player list to avoid cycles
        if(player.getGamesIn() != null) {
            for(Game game : player.getGamesIn()) {
                game.setPlayersIn(null);
            }
        }
        return player;
    }

    // Update a player by id
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @RequiresAuthorization
    public Player updatePlayer(Player player, @HeaderParam(Widge.HTTP_HEADER_WIDGE_PLAYER_ID) String playerIdStr) {
        // Players can only update themselves.
        logger.info("PlayerHelper servicing a call to updatePlayer");
        logger.info("Updater id is: " + playerIdStr);
        logger.info("Updatee id is: " + player.getId());
        Integer playerId;
        playerId = Integer.valueOf(playerIdStr);
        if(!playerId.equals(player.getId())) {
            logger.error("Player: " + player.getName() + " attempted to update another player!");
            return null;
        }
        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        return playerDAO.updatePlayer(player);
    }

    // Add a new player
    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces("application/json")
    public Player addPlayer(Player player) {
        logger.info("PlayerHelper servicing a call to addPlayer");
        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        return playerDAO.addPlayer(player);
    }

    // Delete a player
    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    @RequiresAuthorization
    public void deletePlayer(@PathParam("id") String id, @HeaderParam(Widge.HTTP_HEADER_WIDGE_PLAYER_ID) String playerIdStr) {
        // Players can only delete themselves.  We check this by verifying that the Widge-Player-Id header is the same
        // as the player we are attempting to delete.  Since auth is required here, we can guarantee that this header
        // will be set.
        logger.info("PlayerHelper servicing a call to deletePlayer");
        PlayerDAO playerDAO = daoHandler.getPlayerDAO();
        Player player = playerDAO.getPlayerById(Integer.valueOf(id));
        logger.info("Deleter id is: " + playerIdStr);
        logger.info("Deletee id is: " + player.getId());
        Integer playerId;
        playerId = Integer.valueOf(playerIdStr);
        if(!playerId.equals(player.getId())) {
            logger.error("Player: " + player.getName() + " attempted to delete another player!");
            return;
        }
        playerDAO.deletePlayer(player);
    }

    /////////////////////
    // Game endpoints
    /////////////////////
    @Path("/{id}/game")
    public GameHelper getGame(@PathParam("id") String id) {
        logger.info("Caught a game endpoint, creating helper to service");
        // Get the game the request refers
        Player player = getPlayerById(id);
        requestStore.setRequestPlayer(player);
        if(player == null) {
            logger.error("Player does not exist: " + id);
            return null;
        }

        GameHelper gameHelper = new GameHelper(daoHandler, requestStore);
        return gameHelper;
    }

}
