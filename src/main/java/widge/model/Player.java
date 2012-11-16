package widge.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The Player object represents a player account in the game world. The Player consists of an id (never displayed,
 * invisible to all users, including the owner), a name, a password, a score, and an optional email address. Players can
 * join Games, and within a Game Players can hold goods, issue commands, extract resources, buy and sell goods, and
 * accumulate points.
 * @see Game 
 */
@Entity
@Table(name="Player", uniqueConstraints=@UniqueConstraint(columnNames={"Name"}))
@XmlRootElement(name = "player")
public class Player {

    @Id
    @GeneratedValue
    @Column(name="Id", updatable=false)
    private Integer id;

    @NotNull
    @Column(name="Name")
    private String name;

    @Column(name="Email")
    private String email;

    @Column(name="Password")
    private String password;

    @Column(name="LastModified")
    private Date lastModified;

    @ManyToMany
    @JoinTable(name = "PlayerGame", joinColumns = { @JoinColumn(name = "Player_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "Game_id", nullable = false, updatable = false) })
    private List<Game> gamesIn;

    public Player() {
    }

    public Player(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.lastModified = new Date();
    }

    public String asURI() {
        return "/player/" + id; 
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement    
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    // We don't want to serialize the games, instead send them as URIs
    @XmlTransient
    public List<Game> getGamesIn() {
        return gamesIn;
    }

    public void setGamesIn(List<Game> gamesIn) {
        this.gamesIn = gamesIn;
    }

    @XmlElement(name="games")
    public List<String> getGamesInAsURIs() {
        List<String> result = new ArrayList<String>();
        if(gamesIn == null) {
            return null;
        }
        for(Game game : gamesIn) {
            result.add(game.asURI());
        }
        return result;
    }
}
