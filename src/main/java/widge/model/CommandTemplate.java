package widge.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * CommandTemplate - The description of a command a Player can issue in a Game.  Each consists of an (invisible) id, a
 * name, a function name (invisible to players), and a description. The function name refers to the function the server
 * executes in order to implement the command.  When a Player issues a Command, it gets added to the queue as a
 * PlayerCommand object.
 * @see PlayerCommand
 */
@Entity
@Table(name="CommandTemplate")
@XmlRootElement
public class CommandTemplate {

    @Id
    @GeneratedValue
    @Column(name="Id")
    private Integer id;

    @Column(name="Name")
    private String name;

    @Column(name="FunctionName")
    private String functionName;

    @Column(name="Description")
    @Type(type="text")
    private String description;

    public CommandTemplate() {
    }

    /**
     * Return the URI for accessing this CommandTemplate instance
     * @return the URI for accessing this CommandTemplate instance
     */
    @XmlTransient
    public String asURI() {
        return "/commandtemplate/" + id;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
