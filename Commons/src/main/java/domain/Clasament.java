package domain;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;

@Entity
@Table(name="Clasamente", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Clasament implements Serializable {
    private Integer gameID;
    private String player1;
    private String player2;
    private String player3;
    private Integer points1;
    private Integer points2;
    private Integer points3;

    public Clasament() {
    }

    public Clasament(Integer gameID, String player1, String player2, String player3, Integer points1, Integer points2, Integer points3) {
        this.gameID = gameID;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.points1 = points1;
        this.points2 = points2;
        this.points3 = points3;
    }

    @Id
    @Column(name="id")
    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    @Column(name="player1")
    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    @Column(name="player2")
    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    @Column(name="player3")
    public String getPlayer3() {
        return player3;
    }

    public void setPlayer3(String player3) {
        this.player3 = player3;
    }

    @Column(name="points1")
    public Integer getPoints1() {
        return points1;
    }

    public void setPoints1(Integer points1) {
        this.points1 = points1;
    }

    @Column(name="points2")
    public Integer getPoints2() {
        return points2;
    }

    public void setPoints2(Integer points2) {
        this.points2 = points2;
    }

    @Column(name="points3")
    public Integer getPoints3() {
        return points3;
    }

    public void setPoints3(Integer points3) {
        this.points3 = points3;
    }
}
