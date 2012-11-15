package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerCommand;
import widge.model.Turn;
import widge.model.dao.PlayerCommandDAO;

import java.util.List;

public class PlayerCommandDAOImpl implements PlayerCommandDAO {
    private SessionFactory sessionFactory;

    public PlayerCommandDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PlayerCommand getPlayerCommandById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerCommand WHERE id = :id");
        query.setInteger("id", id);
        return (PlayerCommand)query.uniqueResult();
    }

    public List<PlayerCommand> getPlayerCommandsForGameAndPlayer(Game game, Player player) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerCommand WHERE game = :game AND player = :player");
        query.setEntity("game", game);
        query.setEntity("player", player);
        return query.list();
    }

    public List<PlayerCommand> getPlayerCommandsForGameAndTurn(Game game, Turn turn) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerCommand WHERE game = :game AND turn = :turn");
        query.setEntity("game", game);
        query.setEntity("turn", turn);
        return query.list();
    }

    public void addPlayerCommand(PlayerCommand playerCommand) {
        Session session = sessionFactory.openSession();
        session.persist(playerCommand);
    }

    public PlayerCommand updatePlayerCommand(PlayerCommand playerCommand) {
        Session session = sessionFactory.openSession();
        session.update(playerCommand);
        session.refresh(playerCommand);
        return playerCommand;
    }

    public void deletePlayerCommand(PlayerCommand playerCommand) {
        Session session = sessionFactory.openSession();
        session.delete(playerCommand);
    }
}
