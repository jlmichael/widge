package widge.model.dao;

import widge.model.Game;
import widge.model.Turn;

import java.util.List;

public interface TurnDAO {

    public Turn getTurnById(Integer id);
    public List<Turn> getTurnsForGame(Game game);
    public Turn getNextTurnForGame(Game game);
    public void addTurn(Turn turn);
    public Turn updateTurn(Turn turn);
    
}
