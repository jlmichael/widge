package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGame;
import widge.model.dao.PlayerGameDAO;

import java.util.List;

public class PlayerGameDAOImpl implements PlayerGameDAO {
    private SessionFactory sessionFactory;

    public PlayerGameDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PlayerGame getPlayerGameById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerGame WHERE id = :id");
        query.setInteger("id", id);
        return (PlayerGame)query.uniqueResult();
    }

    public PlayerGame getPlayerGameForGameAndPlayer(Game game, Player player) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerGame WHERE game = :game AND player = :player");
        query.setEntity("game", game);
        query.setEntity("player", player);
        return (PlayerGame)query.uniqueResult();
    }

    public PlayerGame addPlayerGame(PlayerGame playerGame) {
        Session session = sessionFactory.openSession();
        session.persist(playerGame);
        session.refresh(playerGame);
        return playerGame;
    }

    public PlayerGame updatePlayerGame(PlayerGame playerGame) {
        Session session = sessionFactory.openSession();
        session.update(playerGame);
        session.refresh(playerGame);
        return playerGame;
    }

    public void deletePlayerGame(PlayerGame playerGame) {
        Session session = sessionFactory.openSession();
        session.delete(playerGame);
    }
    
}
