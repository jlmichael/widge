package widge.model.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import widge.model.Game;
import widge.model.Player;
import widge.model.PlayerGood;
import widge.model.dao.PlayerGoodDAO;

import java.util.List;

public class PlayerGoodDAOImpl implements PlayerGoodDAO {

    private SessionFactory sessionFactory;

    public PlayerGoodDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PlayerGood getPlayerGoodById(Integer id) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerGood WHERE id = :id");
        query.setInteger("id", id);
        return (PlayerGood)query.uniqueResult();
    }

    public List<PlayerGood> getPlayerGoodsForGameAndPlayer(Game game, Player player) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("FROM PlayerGood WHERE game = :game AND player = :player");
        query.setEntity("game", game);
        query.setEntity("player", player);
        return query.list();
    }

    public void addPlayerGood(PlayerGood playerGood) {
        Session session = sessionFactory.openSession();
        session.persist(playerGood);
    }

    public PlayerGood updatePlayerGood(PlayerGood playerGood) {
        Session session = sessionFactory.openSession();
        session.update(playerGood);
        session.refresh(playerGood);
        return playerGood;
    }

    public void deletePlayerGood(PlayerGood playerGood) {
        Session session = sessionFactory.openSession();
        session.delete(playerGood);
    }
}
