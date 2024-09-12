package application;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Ball class which represents the ball object for the pong game
 */
public class Ball implements Serializable{
    private int size;
    private Circle circle;
    private Color color;
    private int speed;

    /**
     * Constructor to initialise the ball object
     * @param s the size of the ball
     * @param c the color of the ball
     */
    public Ball(int s, Color c){
        this.size = s;
        this.circle = new Circle(size);
        this.color = c;
        this.circle.setFill(color);
        this.speed = 0;
    }

    /**
     * Returns the circle (ball) object
     * @return the circle(ball)
     */
    public Circle getCircle(){
        return circle;
    }

}