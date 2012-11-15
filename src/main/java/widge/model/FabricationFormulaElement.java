package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "FabricationFormulaElement")
@XmlRootElement
public class FabricationFormulaElement {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @Column(name = "InputGood_id")
    private Good inputGood;

    @Column(name = "QuantityNeeded")
    private Integer quantityNeeded;

    @Column(name = "FabricationFormula_id")
    @ManyToOne
    private FabricationFormula fabricationFormula;

    public FabricationFormulaElement() {
    }

    public FabricationFormulaElement(Integer id, Good inputGood, Integer quantityNeeded, FabricationFormula fabricationFormula) {
        this.id = id;
        this.inputGood = inputGood;
        this.quantityNeeded = quantityNeeded;
        this.fabricationFormula = fabricationFormula;
    }

    @XmlTransient
    public String asURI() {
        return "/fabricationformulaelement/" + id;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Good getInputGood() {
        return inputGood;
    }

    public void setInputGood(Good inputGood) {
        this.inputGood = inputGood;
    }

    @XmlElement
    public Integer getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(Integer quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    @XmlElement
    public FabricationFormula getFabricationFormula() {
        return fabricationFormula;
    }

    public void setFabricationFormula(FabricationFormula fabricationFormula) {
        this.fabricationFormula = fabricationFormula;
    }
}
