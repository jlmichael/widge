package widge.model.dao;

import widge.model.*;

import java.util.List;

public interface PlayerCommandDAO {

    public PlayerCommand getPlayerCommandById(Integer id);
    public List<PlayerCommand> getPlayerCommandsForGameAndPlayer(Game game, Player player);
    public List<PlayerCommand> getPlayerCommandsForGameAndTurn(Game game, Turn turn);
    public void addPlayerCommand(PlayerCommand playerCommand);
    public PlayerCommand updatePlayerCommand(PlayerCommand playerCommand);
    public void deletePlayerCommand(PlayerCommand playerCommand);
    
}
