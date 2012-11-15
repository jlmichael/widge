package widge.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name = "PlayerCommand")
@XmlRootElement
public class PlayerCommand {

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
    @Column(name = "Command_id")
    private CommandTemplate commandTemplate;

    @Column(name = "Parameter1")
    private String parameter1;

    @Column(name = "Parameter2")
    private String parameter2;

    @Column(name = "Parameter3")
    private String parameter3;

    @Column(name = "Parameter4")
    private String parameter4;

    @OneToOne
    @Column(name = "Turn_id")
    private Turn turn;

    @Column(name = "ExecutionTime")
    private Date executionTime;

    @Column(name = "Log")
    @Type(type = "text")
    private String log;

    public PlayerCommand() {
    }

    @XmlTransient
    public String asURI() {
        return "/playercommand/" + id;
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
    public CommandTemplate getCommandTemplate() {
        return commandTemplate;
    }

    public void setCommandTemplate(CommandTemplate commandTemplate) {
        this.commandTemplate = commandTemplate;
    }

    @XmlElement(name = "commandTemplate")
    public String getCommandTemplateAsURI() {
        return commandTemplate.asURI();
    }

    @XmlElement
    public String getParameter1() {
        return parameter1;
    }

    public void setParameter1(String parameter1) {
        this.parameter1 = parameter1;
    }

    @XmlElement
    public String getParameter2() {
        return parameter2;
    }

    public void setParameter2(String parameter2) {
        this.parameter2 = parameter2;
    }

    @XmlElement
    public String getParameter3() {
        return parameter3;
    }

    public void setParameter3(String parameter3) {
        this.parameter3 = parameter3;
    }

    @XmlElement
    public String getParameter4() {
        return parameter4;
    }

    public void setParameter4(String parameter4) {
        this.parameter4 = parameter4;
    }

    @XmlTransient
    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @XmlElement(name = "turn")
    public String getTurnAsURI() {
        return turn.asURI();
    }

    @XmlElement
    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }

    @XmlElement
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}

