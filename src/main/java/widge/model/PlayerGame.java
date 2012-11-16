package widge.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * The PlayerGame object maps Players to Games. It consists of an (invisible) id, a Player, and a Game.
 */
@Entity
@Table(name = "PlayerGame")
@XmlRootElement
public class PlayerGame {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @Column(name = "Game_id")
    private Game game;

    @ManyToOne
    @Column(name = "Player_id")
    private Player player;

    @Column(name = "Cash")
    private Integer cash;

    public PlayerGame() {
    }

    @XmlTransient
    public String asURI() {
        return "/playergame/" + id;
    }


    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @XmlElement(name = "game")
    public String getGameAsURI() {
        return game.asURI();
    }

    @XmlTransient
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @XmlElement(name = "player")
    public String getPlayerAsURI() {
        return player.asURI();
    }

    @XmlElement
    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }
}
