package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

@Entity
@Table(name="Good")
@XmlRootElement
public class Good {

    private enum goodType {RAW, INTERMEDIATE, FINISHED}

    @Id
    @GeneratedValue
    @Column(name="Id")
    private Integer id;

    @Column(name="Name")
    private String name;

    @Column(name="Type")
    @Enumerated(EnumType.STRING)
    private goodType type;

    @Column(name="LastModified")
    private Date lastModified;

    public Good() {
    }

    @XmlTransient
    public String asURI() {
        return "/good/" + id;
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
    public goodType getType() {
        return type;
    }

    public void setType(goodType type) {
        this.type = type;
    }

    @XmlElement
    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}

