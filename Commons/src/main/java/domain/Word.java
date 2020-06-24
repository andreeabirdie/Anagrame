package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="Words")
public class Word implements Serializable {
    private String letters;
    private String word;
    private Integer points;

    public Word() {
    }

    public Word(String letters, String word) {
        this.letters = letters;
        this.word = word;
    }

    @Id
    @Column(name="letters")
    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    @Id
    @Column(name="word")
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Column(name="points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
