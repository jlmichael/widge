package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * The FilledMarketOrder represents the fulfillment of a pair of ask and bid MarketOrders. It consists of an (invisible)
 * id, the Game to which it refers, the ask MarketOrder that was filled, the bid MarketOrder that was filled, the
 * quantity of Goods that changed hands, the Turn at which the orders were filled, and the timestamp of the MarketOrders
 * being filled. The set of all FilledMarketOrder objects for a given Good represent the market activity for that Good.
 * This set of FilledMarketOrder objects are what get used in calculating fair market value of any remaining Goods at
 * the end of the Game.
 * @see MarketOrder
 * @see Good
 */
@Entity
@Table(name = "FilledMarketOrder")
@XmlRootElement
public class FilledMarketOrder {

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @Column(name = "Game_id")
    private Game game;

    @OneToOne
    @Column(name = "AskOrder_id")
    private MarketOrder askOrder;

    @OneToOne
    @Column(name = "BidOrder_id")
    private MarketOrder bidOrder;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "ExecutionTime")
    private Date executionTime;

    public FilledMarketOrder(Integer id, Game game, MarketOrder askOrder, MarketOrder bidOrder, Integer quantity, Date executionTime) {
        this.id = id;
        this.game = game;
        this.askOrder = askOrder;
        this.bidOrder = bidOrder;
        this.quantity = quantity;
        this.executionTime = executionTime;
    }

    public FilledMarketOrder() {
    }

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @XmlTransient
    public MarketOrder getAskOrder() {
        return askOrder;
    }

    public void setAskOrder(MarketOrder askOrder) {
        this.askOrder = askOrder;
    }

    @XmlElement(name="askOrder")
    public String getAskOrderAsURI() {
        return askOrder.asURI();
    }

    @XmlTransient
    public MarketOrder getBidOrder() {
        return bidOrder;
    }

    public void setBidOrder(MarketOrder bidOrder) {
        this.bidOrder = bidOrder;
    }

    @XmlElement(name="bidOrder")
    public String getBidOrderAsURI() {
        return bidOrder.asURI();
    }

    @XmlElement
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlElement
    public Date getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Date executionTime) {
        this.executionTime = executionTime;
    }
}

