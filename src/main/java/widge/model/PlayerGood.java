package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name = "PlayerGood")
@XmlRootElement
public class PlayerGood {

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

    @ManyToOne
    @Column(name = "Good_id")
    private Good good;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "LastModifiedDate")
    private Date lastModifiedDate;

    public PlayerGood() {
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

    @XmlTransient
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @XmlElement(name = "good")
    public String getGoodAsURI() {
        return good.asURI();
    }

    @XmlElement
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlElement
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}

