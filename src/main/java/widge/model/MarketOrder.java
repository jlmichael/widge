package widge.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

/**
 * A MarketOrder object represents a single order to either buy or sell the given Good at a given price. It consists of
 * an (invisible) id, the Game in which the MarketOrder exists, the Good being bought or sold, the Player executing the
 * order, the type of order (ask or bid), the quantity of the Good being bought or sold, and the time at which the order
 * was placed. When a MarketOrder is completely filled (quantity left to buy or sell is 0), it is removed from the game.
 * @see Good
 */
@Entity
@Table(name = "MarketOrder")
@XmlRootElement
public class MarketOrder {

    public enum MarketOrderType {ASK, BID}

    @Id
    @GeneratedValue
    @Column(name = "Id")
    private Integer id;

    @ManyToOne
    @Column(name = "Game_id")
    private Game game;

    @ManyToOne
    @Column(name = "Good_id")
    private Good good;

    @ManyToOne
    @Column(name = "Player_id")
    private Player player;

    @Enumerated(EnumType.STRING)
    @Column(name = "OrderType")
    private MarketOrderType orderType;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private Float price;

    @Column(name = "OrderTime")
    private Date orderTime;

    public MarketOrder() {
    }

    public MarketOrder(Integer id, Game game, Good good, Player player, MarketOrderType orderType, Integer quantity, Float price, Date orderTime) {
        this.id = id;
        this.game = game;
        this.good = good;
        this.player = player;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
        this.orderTime = orderTime;
    }

    public String asURI() {
        return "/marketorder/" + id;
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
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    @XmlElement(name = "good")
    public String getGoodAsURI() {
        return good.asURI();
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

    @XmlElement
    public MarketOrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(MarketOrderType orderType) {
        this.orderType = orderType;
    }

    @XmlElement
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlElement
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @XmlElement
    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
