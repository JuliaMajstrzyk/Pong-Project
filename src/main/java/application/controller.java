package application;

import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.io.*;

/**
 * this is the controller class that handles pong games logic
 */

public class controller{
    Rectangle rectangle;
    Rectangle rectangle1;
    Player player1;
    Player player2;
    private static Ball ball;
    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean zPressed = false;
    private boolean pPressed = false;
    public double movementY = 0;
    public double movementX = 0;
    public static double ballSpeedX = 0;
    public static double ballSpeedY = 0;
    private Text player1ScoreText;
    private Text player2ScoreText;
    private ViewPong viewPong;
    private boolean pause = false;
    private int bounceCount=0;
    public boolean reloadButtonClicked = false;
    public static boolean isSpeedChanged = false;

    /**
     * This is the constructor for controller class
     * @param rectangle this is the first paddle
     * @param rectangle1 this is the second paddle
     * @param ball this is the ball
     * @param player1 this is player 1 object
     * @param player2 this is player 2 object
     * @param player1ScoreText  this is the text object to display player 1 score
     * @param player2ScoreText this is the object to display player 2 score
     * @param viewPong this is the main game view
     */
    public controller(Rectangle rectangle, Rectangle rectangle1, Ball ball, Player player1, Player player2, Text player1ScoreText, Text player2ScoreText, ViewPong viewPong){
        this.viewPong = viewPong;
        this.player1ScoreText = player1ScoreText;
        this.player2ScoreText = player2ScoreText;
        this.rectangle = rectangle;
        this.rectangle1 = rectangle1;
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
        startBallThread(); // this begins the ball movement thread
        startKeyPressThread(); // this begins the key press thread
        this.viewPong = viewPong;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Pause Logic
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This method toggles the pause state
     */
    public void togglePause(){
        pause = !pause;
    }

    /**
     *
     * @return boolean value if the game is paused, true/false
     */
    public boolean isPaused(){
        return pause;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Rectangle (paddle) movement logic
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Hanles the key presses to move the paddles and toggle pause
     * @param movementPlayer this value is the amount that the paddle moves when key is pressed
     */
    public void handleKeyPresses(int movementPlayer){
        if(!pause){
            if (wPressed){
                rectangle.setLayoutY(rectangle.getLayoutY() - movementPlayer); // adjusting the layout with the movement of player
            }
            if (sPressed){
                rectangle.setLayoutY(rectangle.getLayoutY() + movementPlayer);
            }
            if (aPressed){
                rectangle1.setLayoutY(rectangle1.getLayoutY() - movementPlayer);
            }
            if (zPressed){
                rectangle1.setLayoutY(rectangle1.getLayoutY() + movementPlayer);
            }
        }
        if(pPressed){
            togglePause(); // this toggles the pause state is p is pressed
        }
    }

    /**
     * This handles the key pressed events and sets them as true if key has been pressed by user
     * @param e this is the object that represent the key press event
     */
    public void keyPressed(KeyEvent e){
        switch (e.getCode()){ // switch case used to check which key is pressed
            case W:
                wPressed = true;
                break;
            case S:
                sPressed = true;
                break;
            case A:
                aPressed = true;
                break;
            case Z:
                zPressed = true;
                break;
            case P:
                pPressed = true;
                break;
        }
    }

    /**
     * This handles when the keys are released by the user, all are set back to false once key is released
     * @param e KeyEvent object representing key release event
     */
    public void keyReleased(KeyEvent e){
        switch (e.getCode()){ // works similarily to the key pressed, instead it checks if key was released
            case W:
                wPressed = false;
                break;
            case S:
                sPressed = false;
                break;
            case A:
                aPressed = false;
                break;
            case Z:
                zPressed = false;
                break;
            case P:
                pPressed = false;
                break;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Threads
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This thread handles key presses and therefore allows for the movement of the rectangles/paddles
     */
    private void startKeyPressThread(){
        Thread keyPressThread = new Thread(()->{
            while (true){ // inifitne loop
                try{
                    Thread.sleep(10); // sleep every 10 miliseconds
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                Platform.runLater(()->{
                    handleKeyPresses(5); // this gets called every 10 miliseconds
                });
            }
        });
        keyPressThread.setDaemon(true);
        keyPressThread.start(); // begins program
    }

    /**
     * This is the thread that begins the ball movement
     */
    private void startBallThread(){
        Thread ballThread = new Thread(()->{
            while(true){
                try{
                    Thread.sleep(10); // puts the execution to sleep for 10 miliseconds
                }catch (InterruptedException e){ // for interruptions
                    e.printStackTrace(); // printing the interruption
                }
                Platform.runLater(() ->{ // this method makes sure the specified code is executed in thread, updates UI components smoothly
                    move();
                    handleColliosion(rectangle, rectangle1, ball.getCircle());
                });
            }
        });
        ballThread.setDaemon(true); // marks thread as a daemon thread, automatically terminate once program exits
        ballThread.start(); // begins execution
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Ball movement + interactions for goals
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * this method moves the ball around and interacts with players. Checks for the goals that have been scored and updates the
     * values of scores for each player on each score. Indicates which player also scored by displaying a message. Checks
     * if the set goals to win the game has been reached
     */
    public void move(){
        if(!pause){ // checks if the game is not paused in order to move the ball
//            if(reloadButtonClicked){ // if reload button is set to true then this piece runs and reloads the game
//                reloadGame(String player1Name, String player2Name, int rectangleWidth, int rectangleHeight, int player1score, int player2score, double originalballSpeedX, double originalballSpeedY);
//                reloadButtonClicked = false; // sets reload button back to false
//            }
            double currentX = ball.getCircle().getTranslateX(); // stores x-coordinates of the ball
            double currentY = ball.getCircle().getTranslateY(); // stores y-coordinates of the ball
            double circleRadius = ball.getCircle().getRadius(); // stores radius of the ball

            double sceneWidth = ball.getCircle().getScene().getWidth(); // stores width of the scene
            double sceneHeight = ball.getCircle().getScene().getHeight(); // stores height of the scene

            // chaingn positions
            double newPosX = currentX + movementX;
            double newPosY = currentY + movementY;

            if(player1.getScore()>=ViewPong.goalsToWin || player2.getScore()>=ViewPong.goalsToWin){ // checks if either players scored enough goals to end the game
                Player winner = (player1.getScore()>=ViewPong.goalsToWin?player1:player2); // checks which player won
                endGame(winner.getName()); // end game method is then called with the name of the winner
                pause = true; // pauses the game
                return; // leaves the method compeletely
            }

            // collisions
            if (newPosX <= circleRadius || newPosX >= (sceneWidth - circleRadius)){
                if (newPosX <= circleRadius){
                    player2.incrementScore(); // player 2 scores
                    updateScoreText(); // updates score text method
                    if(!isGameEnded()){
                        viewPong.showGoalMessage(player2.getName() + " scored!"); // displays a message with player name and saying they scored
                    }
                } else{
                    player1.incrementScore(); // player 1 scores
                    updateScoreText();
                    if(!isGameEnded())
                        viewPong.showGoalMessage(player1.getName() + " scored!");
                }
                newPosX = sceneWidth / 2; // this resets the ball position any time someone scores in the game
                newPosY = sceneHeight / 2;
                movementX *= -1; // also reverses the movement of the ball

            }
            //bouncing off of the top and bottom of the screen
            if (newPosY <= circleRadius || newPosY >= (sceneHeight - circleRadius)) {
                movementY *= -1; // reversing circle y
                newPosY = currentY + movementY;
            }
            ball.getCircle().setTranslateX(newPosX);
            ball.getCircle().setTranslateY(newPosY);
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Collision detection
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Handles the collisions between the ball and the paddles by calling checkColliosin(), if true, movementX is reversed
     * and bounceCount is incremented.
     * also checks for bounce count modulo ballSpeedInc and
     * finally calls on increaseBallSpeed() if the bounce count is divisible by ballSpeedInc
     * @param rectangle first paddle
     * @param rectangle1 second paddle
     * @param circle the ball
     */
    public void handleColliosion(Rectangle rectangle, Rectangle rectangle1, Circle circle){
        if(checkColliosin(circle, rectangle)||checkColliosin(circle, rectangle1)){ // if this condition is true
            movementX *= -1; // ball movement is reversed
            bounceCount++; // bounce count is incremented

            int ballSpeedInc = viewPong.ballSpeedInc; // stores the value of ballSpeedInc which is the number of bounces needed before the ball speeds up

            if(bounceCount % ballSpeedInc ==0) { // if bounceCount is divisible by ballSpeedInc
                increaseBallSpeed(); // this method is called to increase the ball speed
            }
        }
    }

    /**
     * This checks the collision between ball and paddle by getting bounds of each object, and checking if they intersect
     * @param circle ball
     * @param rectangle paddle
     * @return returns true or false depending on whether the two objects intersect
     */
    public static boolean checkColliosin(Circle circle, Rectangle rectangle){
        return circle.getBoundsInParent().intersects(rectangle.getBoundsInParent()); // checks if the circle and rectangle intersect, returns true or false depending on if collision occurs or not
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Player information updates
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * updates scores texts for player 1 and player 2
     */
    public void updateScoreText() {
        player1ScoreText.setText(player1.getName() + ": " + player1.getScore());
        player2ScoreText.setText(player2.getName() + ": " + player2.getScore());
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Increasing of ball speed on collision and saving increased ball speed
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This method increases the ball speed by a factor of 1.5, it also calls on saveIncreasedBallSpeed() to save it's
     * current values.
     */
    public void increaseBallSpeed(){
        movementX *= 1.5; // increases ball speed for X
        movementY *= 1.5; // increases ball speed for Y
        saveIncreasedBallSpeed(); // calls on this method to save the increased ball speed
    }
    /**
     * This method saves the increased ball speed by updating the values of ballSpeedX and ballSpeedY
     * This method also sets a boolean value to true which indicates that the ball speed has been changed
     */
    public void saveIncreasedBallSpeed(){
        ballSpeedX = movementX; // saves the increased ball speed for X
        ballSpeedY = movementY; // saves the increased ball speed for Y
        isSpeedChanged = true; // sets isSpeedChanged to true
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Saving game using serialisation + reloading game
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This saves the game for future reload by serializing the objects
     * @param player1Name the name of player 1
     * @param player2Name the name of player 2
     * @param rectangleWidth the width of the paddle
     * @param rectangleHeight the height of the paddle
     * @param player1score the score of player 1
     * @param player2score the score of player 2
     * @param originalballSpeedX the original speed of the ball (x)
     * @param originalballSpeedY the original speed of the ball (y)
     */
    public static void saveGameForReload(String player1Name, String player2Name, int rectangleWidth, int rectangleHeight, int player1score, int player2score, double originalballSpeedX, double originalballSpeedY) {
        gameSerialisation.getInstance().saveGameForReload(player1Name, player2Name, rectangleWidth, rectangleHeight, player1score, player2score, originalballSpeedX, originalballSpeedY);
    }

    /**
     * Reloads the game state from the serialized file and updates the game state and player information
     */
    public void reloadGame(controller controller){
        reloadButtonClicked = true; // sets reloadButtonClicked to true
        gameSerialisation.getInstance().reloadGame(controller, player1, player2, rectangle, rectangle1, movementX, movementY, player1.getScore(), player2.getScore());
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Restarting the game logic
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Restarts the game by resetting the ball position and paddle positions, setting the score to 0 and resetting the
     * movementX and movementY values to the initial speed. Unpauses the game if it was paused.
     */
    public void restartGame(){
        double width = ball.getCircle().getScene().getWidth();
        double height = ball.getCircle().getScene().getHeight();
        // reset ball position
        ball.getCircle().setTranslateX(width / 2);
        ball.getCircle().setTranslateY(height / 2);

        // reseting rectangle positions
        double initialRectangleY = 0;
        rectangle.setLayoutY(initialRectangleY);
        rectangle1.setLayoutY(initialRectangleY);

        // update score text
        player1.resetScore();
        player2.resetScore();
        updateScoreText();

        // reset movementX and movementY to original values
        movementX = ViewPong.speed;
        movementY = ViewPong.speed;

        // unpause the game if it was paused
        if (pause) {
            togglePause();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Game end logic
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * This method is to end the game and display the winner
     * @param winner winner of game name
     */
    public void endGame(String winner){
        viewPong.showGameEndMessage(winner);
    }

    /**
     * This method determines if the game has ended by checking the current score of the players and comparing it to the
     * goals to win variable set in the ViewPong class
     * @return true if game has ended or false if game has not ended
     */
    public boolean isGameEnded() {
        return player1.getScore() >= ViewPong.goalsToWin || player2.getScore() >= ViewPong.goalsToWin;
    }

}