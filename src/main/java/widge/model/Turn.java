package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name = "Turn")
@XmlRootElement
public class Turn {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @Column(name = "Game_id")
    private Game game;

    @Column(name = "TurnNumber")
    private Integer turnNumber;

    @Column(name = "StartTime")
    private Date startTime;

    @Column(name = "EndTime")
    private Date endTime;

    @Column(name = "LastModifiedDate")
    private Date lastModifiedDate;

    public Turn() {
    }

    public Turn(Integer id, Game game, Integer turnNumber, Date startTime, Date endTime, Date lastModifiedDate) {
        this.id = id;
        this.game = game;
        this.turnNumber = turnNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lastModifiedDate = lastModifiedDate;
    }

    @XmlTransient
    public String asURI() {
        return "/turn/" + id;
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

    @XmlElement
    public Integer getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(Integer turnNumber) {
        this.turnNumber = turnNumber;
    }

    @XmlElement
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @XmlElement
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @XmlElement
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
