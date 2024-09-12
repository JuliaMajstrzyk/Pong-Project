package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.junit.Test;

import java.io.*;

import static application.controller.*;


public class gameSerialisation implements Serializable {
    private static gameSerialisation gameSerialisation = new gameSerialisation();
    private gameSerialisation(){

    }

    /**
     * this returns the instance of gameSerialisation class
     * @return the instance of gameSerialisation
     */
    // Public method to get the instance of the class
    public static gameSerialisation getInstance() {
        return gameSerialisation;
    }

    /**
     * this is where the saving happens
     * @param player1Name the name of player 1
     * @param player2Name the name of player 2
     * @param rectangleWidth the width of the rectangle
     * @param rectangleHeight the height of the rectangle
     * @param player1score the score of player 1
     * @param player2score the score of player 2
     * @param originalballSpeedX the ball speed for X
     * @param originalballSpeedY the ball speed for Y
     */
    public static void saveGameForReload(String player1Name, String player2Name, int rectangleWidth, int rectangleHeight, int player1score, int player2score, double originalballSpeedX, double originalballSpeedY) {
        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\maja\\Downloads\\FinalOOPPhase3JBM\\OOPPhase2JBM\\JavaProjectPart1OOPJBMPhase2\\gameSetting.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) { // objectOutPut stream created to write game state to the file
            out.writeObject(player1Name); // writes the name of player 1
            out.writeObject(player2Name); // writes the name of player 2

            out.writeInt(rectangleWidth); // writes the width of the paddle
            out.writeInt(rectangleHeight); // writes the height of the paddle

            if (isSpeedChanged) { // if the ball speed has been changed
                out.writeDouble(ballSpeedX); // writes the updated ball speed to the file
                out.writeDouble(ballSpeedY);
            } else {
                out.writeDouble(originalballSpeedX); // otherwise write the original ball speed to the file
                out.writeDouble(originalballSpeedY);
            }
            out.writeInt(player1score); // writes the score of player 1
            out.writeInt(player2score); // writes the score of player 2

            int goalsToWin = ViewPong.goalsToWin; // gets the goals to win value
            int ballSpeedInc = ViewPong.ballSpeedInc; // gets the ball speed increment

            out.writeInt(goalsToWin); // writes the goals to win value
            out.writeInt(ballSpeedInc); // writes the ball speed increment

            System.out.println("Game saving...");
        } catch (IOException e) {
            e.printStackTrace(); // for any errors in this process, it is caught and printed to the console
        }
    }

    /**
     * this reloads the game state
     * @param controller the controller object
     * @param player1 the player 1 object
     * @param player2 the player2 object
     * @param rectangle the rectangle object
     * @param rectangle1 the second rectangle object
     * @param movementX the movement for X
     * @param movementY the movement for y
     * @param player1Score the score of player 1
     * @param player2Score the score of player 2
     */
    public void reloadGame(controller controller, Player player1, Player player2, Rectangle rectangle, Rectangle rectangle1, double movementX, double movementY, int player1Score, int player2Score) {
        try (FileInputStream fileIn = new FileInputStream("C:\\Users\\maja\\Downloads\\FinalOOPPhase3JBM\\OOPPhase2JBM\\JavaProjectPart1OOPJBMPhase2\\gameSetting.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            // reading all the values from the file
            String player1Name = (String) in.readObject();
            String player2Name = (String) in.readObject();
            int savedRectangleWidth = in.readInt();
            int savedRectangleHeight = in.readInt();
            movementX = in.readDouble();
            movementY = in.readDouble();
            player1Score = in.readInt();
            player2Score = in.readInt();
            int goalsToWin = in.readInt();
            int ballSpeedInc = in.readInt();

            // updating the game state and player information
            player1.setName(player1Name);
            player2.setName(player2Name);

            rectangle.resize(savedRectangleWidth, savedRectangleHeight);
            rectangle1.resize(savedRectangleWidth, savedRectangleHeight);

            player1.setScore(player1Score);
            player2.setScore(player2Score);

            controller.movementX = movementX;
            controller.movementY = movementY;

            ViewPong.goalsToWin = goalsToWin;
            ViewPong.ballSpeedInc = ballSpeedInc;
            // updates score text + name text in UI
            controller.updateScoreText();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found. Unable to reload game.");
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}