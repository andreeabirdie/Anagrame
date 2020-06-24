package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Rounds")
public class Round implements Serializable {
    private Integer gameID;
    private Integer round;
    private String player;
    private Integer points;
    private String letters;
    private String word;

    public Round() {
    }

    public Round(Integer gameID, String player, Integer round) {
        this.gameID = gameID;
        this.player = player;
        this.round = round;
    }

    public Round(Integer gameID, Integer round, String player, String letters, Integer points) {
        this.gameID = gameID;
        this.round = round;
        this.player = player;
        this.points = points;
        this.letters = letters;
    }

    @Id
    @Column(name="gameID")
    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    @Id
    @Column(name="round")
    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    @Id
    @Column(name="player")
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Column(name="points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Column(name="letters")
    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Column(name="word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}


