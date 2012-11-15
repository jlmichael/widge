package widge.model.dao;

import widge.model.Game;

import java.util.Date;
import java.util.List;

public interface GameDAO {

    public Game getGameById(Integer id);
    public List<Game> getGames();
    public List<Game> getGamesModifiedSinceDate(Date lastModified);
    public Game addGame(Game game);
    public Game updateGame(Game game);
}
