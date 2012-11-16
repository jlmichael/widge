package widge.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * A FabricationFormula object represents the recipe used to produce a Good from lower Goods in the hierarchy. It
 * consists of an (invisible) id, the finished Good being produced, an input Good needed to fabricate the finished good,
 * and the quantity of input needed. The set of all FabricationFormula objects with the same finished Good represents
 * the total recipe needed for that finished Good.
 * @see Good
 * @see FabricationFormulaElement
 */
@Entity
@Table(name = "FabricationFormula")
@XmlRootElement
public class FabricationFormula {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @Column(name = "finishedGood_id")
    private Good finishedGood;

    @OneToMany
    private List<FabricationFormulaElement> inputGoods;

    public FabricationFormula() {
    }

    public FabricationFormula(Integer id, Good finishedGood) {
        this.id = id;
        this.finishedGood = finishedGood;
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Good getFinishedGood() {
        return finishedGood;
    }

    public void setFinishedGood(Good finishedGood) {
        this.finishedGood = finishedGood;
    }

    // We don't send the serialized input goods, instead we send URIs
    @XmlTransient
    public List<FabricationFormulaElement> getInputGoods() {
        return inputGoods;
    }

    public void setInputGoods(List<FabricationFormulaElement> inputGoods) {
        this.inputGoods = inputGoods;
    }

    @XmlElement(name="inputGoods")
    public List<String> getInputGoodsAsURIs() {
        List<String> result = new ArrayList<String>();
        for(FabricationFormulaElement element : inputGoods) {
            result.add(element.asURI());
        }
        return result;
    }
}
