package widge.model.dao;

import widge.model.Player;

import java.util.List;

public interface PlayerDAO {

    public Player getPlayerById(Integer id);
    public Player getPlayerByName(String name);
    public List<Player> getPlayers();
    public Player addPlayer(Player player);
    public Player updatePlayer(Player player);
    public void deletePlayer(Player player);
}
