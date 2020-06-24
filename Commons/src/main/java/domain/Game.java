package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Games", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Game implements Serializable {
    private Integer id;
    private Integer currentRound;
    private Integer sentResponses;

    public Game() {
    }

    public Game(Integer id, Integer currentRound, Integer sentResponses) {
        this.id = id;
        this.currentRound = currentRound;
        this.sentResponses = sentResponses;
    }

    @Id
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="currentRound")
    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    @Column(name="sentResponses")
    public Integer getSentResponses() {
        return sentResponses;
    }

    public void setSentResponses(Integer sentResponses) {
        this.sentResponses = sentResponses;
    }

}
