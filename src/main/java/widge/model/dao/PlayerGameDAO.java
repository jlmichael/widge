package widge.model.dao;

import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGame;

import java.util.List;

public interface PlayerGameDAO {
    
    public PlayerGame getPlayerGameById(Integer id);
    public PlayerGame getPlayerGameForGameAndPlayer(Game game, Player player);
    public PlayerGame addPlayerGame(PlayerGame PlayerGame);
    public PlayerGame updatePlayerGame(PlayerGame PlayerGame);
    public void deletePlayerGame(PlayerGame PlayerGame);
    
}
