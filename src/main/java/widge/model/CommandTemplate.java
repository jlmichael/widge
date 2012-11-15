package widge.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
