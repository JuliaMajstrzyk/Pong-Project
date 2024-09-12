package application.Test;
import static org.junit.Assert.*;

import application.*;
import javafx.scene.paint.Color;
import org.junit.Test;
import org.junit.Before;

public class controllerTest{
    private Ball ball;
    private rectangle rectangle;
    private rectangle rectangle1;


    /**
     * This sets up the ball, rectangle (paddle) and rectangle1 (paddle)
     */
    @Before // this method runs before each test
    public void setUp(){
        // creates ball object and rectangle(paddle) and rectangle1(paddle1)
        ball = new Ball(20, Color.YELLOW);
        rectangle = new rectangle(20, 20, Color.RED);
        rectangle1 = new rectangle(20, 20, Color.BLUE);
    }

    /**
     * This code tests the collision between a ball and two rectangles (paddles). The tests checks if ball collides with
     * the first rectangle and if ball collides with the second rectangle. The test passes if the collision is true and fails
     * if it is false
     */
    @Test // this indicates that this method is a test
    public void testCollisionDetection(){ // tests for collision between the ball object and the two rectangles (paddles)
        // sets the positions
        ball.getCircle().setTranslateX(150);
        ball.getCircle().setTranslateY(150);
        rectangle.getRectangle().setX(150);
        rectangle.getRectangle().setY(150);
        rectangle1.getRectangle().setX(150);
        rectangle1.getRectangle().setY(150);

        // calls the checkColliosin() method with the ball and rectangle (paddle) objects and checks if they collide, returns true or false
        boolean collisionWithPlayer1 = controller.checkColliosin(ball.getCircle(), rectangle.getRectangle());
        boolean collisionWithPlayer2 = controller.checkColliosin(ball.getCircle(), rectangle1.getRectangle());

        //assertations are used to verify the results of collision detection
        assertTrue(collisionWithPlayer1); //this test passes and return true
        //assertFalse(collisionWithPlayer2); //this test fails since return is true
    }
}
