package widge.model.dao;

import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGood;

import java.util.List;

public interface PlayerGoodDAO {

    public PlayerGood getPlayerGoodById(Integer id);
    public List<PlayerGood> getPlayerGoodsForGameAndPlayer(Game game, Player player);
    public void addPlayerGood(PlayerGood playerGood);
    public PlayerGood updatePlayerGood(PlayerGood playerGood);
    public void deletePlayerGood(PlayerGood playerGood);
    
}
