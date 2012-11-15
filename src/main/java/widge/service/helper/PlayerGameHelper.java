package widge.service.helper;

import widge.model.PlayerGame;
import widge.model.dao.PlayerGameDAO;
import widge.model.dao.handler.DAOHandler;
import widge.service.WidgeRequestStore;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;

public class PlayerGameHelper extends AbstractHelper {
    public PlayerGameHelper(DAOHandler daoHandler, WidgeRequestStore requestStore) {
        super(daoHandler, requestStore);
    }

    @PUT
    @Path("/")
    public PlayerGame playerJoinGame() {
        if(requestStore.getRequestPlayerGame() != null) {
            return requestStore.getRequestPlayerGame();
        }

        // Add a PlayerGame entity for this player and game
        PlayerGameDAO playerGameDAO = daoHandler.getPlayerGameDAO();
        PlayerGame playerGame = new PlayerGame();
        playerGame.setGame(requestStore.getRequestGame());
        playerGame.setPlayer(requestStore.getRequestPlayer());
        return playerGameDAO.addPlayerGame(playerGame);
    }
}
