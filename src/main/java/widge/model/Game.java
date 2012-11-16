package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The Game object represents a single instance of a Widge game on the server. Games define boundaries between players,
 * actions, goods, etc., such that each is self-contained and player actions do not span Game boundaries. A Game
 * consists of an (invisible) id, a name, and an ending condition. Games also have Turns.
 * @see Turn
 */
@Entity
@Table(name="Game")
@XmlRootElement(name="game")
public class Game {

    @Id
    @GeneratedValue
    @Column(name="Id")
    private int id;

    @Column(name="Name")
    private String name;

    @Column(name="LastModifiedDate")
    private Date lastModifiedDate;

    @ManyToMany
    @JoinTable(name = "PlayerGame", joinColumns = { @JoinColumn(name = "Game_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "Player_id", nullable = false, updatable = false) })
    private List<Player> playersIn;

    @OneToMany
    private List<Turn> turns;

    public Game() {
    }

    public Game(int id, String name, Date lastModifiedDate, List<Player> playersIn, List<Turn> turns) {
        this.id = id;
        this.name = name;
        this.lastModifiedDate = lastModifiedDate;
        this.playersIn = playersIn;
        this.turns = turns;
    }

    public String asURI() {
        return "/game/" + String.valueOf(id);
    }

    @XmlElement
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @XmlTransient
    public List<Player> getPlayersIn() {
        return playersIn;
    }

    public void setPlayersIn(List<Player> playersIn) {
        this.playersIn = playersIn;
    }

    @XmlElement(name="players")
    public List<String> getPlayersInAsURIs() {
        if(playersIn == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for(Player player : playersIn) {
            result.add(player.asURI());
        }
        return result;
    }

    @XmlTransient
    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }

    @XmlElement(name = "turns")
    public List<String> getTurnsAsURIs() {
        if(turns == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        for(Turn turn : turns) {
            result.add(turn.asURI());
        }
        return result;
    }



}
