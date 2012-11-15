package widge.service;

import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGame;

// A class for persisting objects on a per-request basis.  This is useful for storing elements of a request, such as
// the Player object currently being accessed, for the life of the service request.  For example, a request to
// /player/12/game/13/market/marketorder/14 would use the PlayerHelper, GameHelper and MarketHelper.  Each Helper
// class would get a reference to the same WidgeRequestStore, allowing the PlayerHelper to create the Player object
// associated with id 12 and store it in the WidgeRequestStore for use in the GameHelper and MarketHelper methods if
// necessary.
public class WidgeRequestStore {

    private Player requestPlayer;
    private Game requestGame;
    private PlayerGame requestPlayerGame;

    public WidgeRequestStore() {
    }

    public Player getRequestPlayer() {
        return requestPlayer;
    }

    public void setRequestPlayer(Player requestPlayer) {
        this.requestPlayer = requestPlayer;
    }

    public Game getRequestGame() {
        return requestGame;
    }

    public void setRequestGame(Game requestGame) {
        this.requestGame = requestGame;
    }

    public PlayerGame getRequestPlayerGame() {
        return requestPlayerGame;
    }

    public void setRequestPlayerGame(PlayerGame requestPlayerGame) {
        this.requestPlayerGame = requestPlayerGame;
    }
}
