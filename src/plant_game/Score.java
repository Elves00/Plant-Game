/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 * A score object holds a name and score value from a game and implements the
 * comparable interface this allows for ranking of score objects
 *
 * @author breco
 */
public class Score implements Comparable<Score> {

    private String name;
    private int score;

    /**
     *
     *
     * @param name
     * @param score
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Score o) {

        if (o.score > this.score) {
            return 1;
        } else if (o.score == this.score) {
            return 0;
        } else {
            return -1;
        }
    }

    public String toString() {
        return this.name + " " + this.score;
    }

}
