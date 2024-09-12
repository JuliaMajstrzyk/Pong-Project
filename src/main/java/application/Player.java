package application;

import java.io.Serializable;

/**
 * This is the player class in the pong game
 */
public class Player implements Serializable{
    private String name;
    private int score;

    /**
     * This is the constructor for player, which initialises the names and scores
     * @param n the name of the player
     */
    public Player(String n){
        this.name = n;
        this.score = 0;
    }

    /**
     * retrieves the name
     * @return the name
     */
    public String getName(){ // retrieves name
        return name;
    }

    /**
     * sets the name
     * @param n the name
     * @return returns the set name
     */
    public String setName(String n){
        name = n;
        return name;
    }

    /**
     * sets the score to the given value
     * @param s the value to set the score to
     */
    public void setScore(int s){
        score = s;
    }

    /**
     * retrieves the score
     * @return the score value
     */
    public int getScore(){ // retrieves score
        return score;
    }

    /**
     * increments and returns updated score value
     * @return the updated score
     */
    public int incrementScore(){ // increments and returns updated score
        score++;
        return score;
    }

    /**
     * resets the score back to zero
     */
    public void resetScore(){
        score = 0;
    }

}
