package widge.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="Token")
public class Token {

    @Id
    @GeneratedValue
    @Column(name="Id")
    private Integer id;

    @Column(name = "TokenKey")
    private String tokenKey;

    @OneToOne
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    @Column(name = "Player_id")
    private Player player;

    @Column(name = "TokenExpiration")
    private Date tokenExpiration;

    public Token() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public void updateTokenExpiration(int expirationPeriodInSeconds) {
        Calendar cal = Calendar.getInstance();
        if(this.tokenExpiration == null) {
            this.tokenExpiration = new Date();
        }
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, expirationPeriodInSeconds);
        this.tokenExpiration = cal.getTime();
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", tokenKey='" + tokenKey + '\'' +
                ", player=" + player +
                ", tokenExpiration=" + tokenExpiration +
                '}';
    }
}
