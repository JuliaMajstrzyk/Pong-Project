package application;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * ViewPong class represents the UI for pong game
 */

public class ViewPong implements Serializable {
    private Stage stage;
    private Player player1;
    private Player player2;
    private int rectangleWidth;
    private int rectangleHeight;
    public static int goalsToWin;
    public static int ballSpeedInc;
    public static double speed;
    private Spinner<Integer> ballSpeedSpinner;
    private Ball ball;
    private rectangle rectangle, rectangle1;
    public double width = 1000, height = 700;

    /**
     * Constructs a viewpong object with stage
     * @param stage the stage for ViewPong object
     */
    public ViewPong(Stage stage){
        this.stage = stage;
        initialize();
    }

    /**
     * Pong game initialisation
     */
    public void initialize(){
        // creating ball, rectangle (paddle) and rectangle1 (paddle) objects
        ball = new Ball(20, Color.YELLOW);
        rectangle = new rectangle(20, 100, Color.PURPLE);
        rectangle1 = new rectangle(20, 100, Color.DEEPSKYBLUE);

        //stage.getIcons().add(new Image("file:src/pongIcon.png"));

        // creating the menu UI elements
        Label playerName1 = new Label("Enter name for player 1 ");
        playerName1.setTextFill(Color.WHITE);
        playerName1.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        TextField playerTextField1 = new TextField();
        playerTextField1.setPromptText("Player 1");

        Label playerName2 = new Label("Enter name for player 2 ");
        playerName2.setTextFill(Color.WHITE);
        playerName2.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        TextField playerTextField2 = new TextField();
        playerTextField2.setPromptText("Player 2");

        Label speedText = new Label("Enter ball speed");
        speedText.setTextFill(Color.WHITE);
        speedText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        ballSpeedSpinner = new Spinner<>(1, 10, 2);

        Label heightText = new Label("Enter the height of the racket");
        heightText.setTextFill(Color.WHITE);
        heightText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        Slider heightSlider = new Slider(0, 250, 150);
        heightSlider.setShowTickMarks(true);
        heightSlider.setMajorTickUnit(25);
        heightSlider.setShowTickLabels(true);

        Label widthText = new Label("Enter the width of the racket");
        widthText.setTextFill(Color.WHITE);
        widthText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        Slider widthSlider = new Slider(0, 250, 40);
        widthSlider.setShowTickMarks(true);
        widthSlider.setMajorTickUnit(25);
        widthSlider.setShowTickLabels(true);

        Label goalsText = new Label("Enter the number of goals necessary to win a game");
        goalsText.setTextFill(Color.WHITE);
        goalsText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        Spinner<Integer> goalsNeededSpinner = new Spinner<>(1, 10, 3);

        Label ballSpeedText = new Label("Enter how often you want the ball speed to increase");
        ballSpeedText.setTextFill(Color.WHITE);
        ballSpeedText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        Spinner<Integer> ballSpeedIncreaseSpinner = new Spinner<>(1, 10, 3);

        // creating exit game button
        Button exitGame = new Button("Exit game");
        exitGame.setOnAction(e ->{
            exitGame(); // calls on exitGame() method to exit the game
        });

        // creating begin game button
        Button beginBTN = new Button("Begin Game");
        beginBTN.setOnAction(e->{
            // retrieving all inputs from user
            String name1 = playerTextField1.getText();
            String name2 = playerTextField2.getText();
            double height = heightSlider.getValue();
            double width = widthSlider.getValue();
            int goals = goalsNeededSpinner.getValue();
            int ballSpeedIncrease = ballSpeedIncreaseSpinner.getValue();
            speed = ballSpeedSpinner.getValue();

            // retrieved values are then used to initialise variables and objects for the game
            player1 = new Player(name1);
            player2 = new Player(name2);
            rectangleHeight = (int) height;
            rectangleWidth = (int) width;
            goalsToWin = goals;
            ballSpeedInc = ballSpeedIncrease;

            // begin game method is then called to start the game
            beginGame();
        });


        Image img = new Image("file:src/pongLogoT.png");
        ImageView logoView = new ImageView(img);

        Image bgImage = new Image("file:src/backgroundgif.gif");
        ImageView bgImageView = new ImageView(bgImage); // stopped working for some reason ;((

        //Buttons held in HBox
        HBox btns = new HBox(10, beginBTN, exitGame);

        //VBox contains all menu components
        VBox menu = new VBox(10, logoView, playerName1, playerTextField1, playerName2, playerTextField2,
                speedText, ballSpeedSpinner, widthText, widthSlider, heightText, heightSlider,
                goalsText, goalsNeededSpinner, ballSpeedText, ballSpeedIncreaseSpinner, btns);

        //Secondary vbox which holds original VBox menu
        VBox layout = new VBox(menu);

        // Alignments
        menu.setMaxWidth(width / 2);
        btns.setAlignment(Pos.CENTER);
        menu.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);

        //Stackpane originally created to hold background image and menu
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;"); // colouring background
        root.getChildren().addAll(bgImageView, layout); // bg Image and menu added to stackpane

        //This was to bind the background image to the stage width and height
        bgImageView.fitWidthProperty().bind(stage.widthProperty());
        bgImageView.fitHeightProperty().bind(stage.heightProperty());

        Scene selectPlayerScene = new Scene(root, width, height); // creating scene with root, width and height

        // Set scene to stage and display
        stage.setScene(selectPlayerScene);
        stage.show();
    }

    /**
     * Exiting the game
     */
    private void exitGame(){
        Platform.exit();
    }

    /**
     * Saves the current game state for a later reload, calls saveGameForReload() in controller
     * @param rectangleWidth the width of teh rectangle
     * @param rectangleHeight the height of the rectangle
     * @param player1score the score of player 1
     * @param player2score the score of player 2
     */
    public void saveGameForReload(int rectangleWidth, int rectangleHeight, int player1score, int player2score) {
        controller.saveGameForReload(player1.getName(), player2.getName(), rectangleWidth, rectangleHeight, player1score, player2score, speed, speed);
    }


    /**
     * This method begins the game
     */
    private void beginGame(){
        //fetching player names
        String player1Names = player1.getName();
        String player2Names = player2.getName();

        //These text elements are for usernames to be displayed and their scores
        Text player1Text = new Text(player1Names + ": " + player1.getScore());
        player1Text.setFill(Color.WHITE);
        player1Text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        Text player2Text = new Text(player2Names + ": " + player2.getScore());
        player2Text.setFill(Color.WHITE);
        player2Text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        // initialising game controller which controls all components on screen
        controller controller = new controller(rectangle.getRectangle(), rectangle1.getRectangle(), ball, player1,
                player2, player1Text, player2Text, this);

        rectangle.setSize(rectangleWidth, rectangleHeight); //setting racket size according to user input
        rectangle1.setSize(rectangleWidth, rectangleHeight);

        //another exit game button
        Button exitGame = new Button("Exit game");
        exitGame.setOnAction(e->{
            exitGame();
        });

        Button saveGame = new Button("Save game");
        saveGame.setOnAction(e ->{
            saveGameForReload(rectangleWidth, rectangleHeight, player1.getScore(), player2.getScore());
            saveAlert();
        });

        //restart game button
        Button restartBtn = new Button("Restart Game");
        restartBtn.setOnAction(e ->{
            removeWinnerMessage(); // this calls on a method to remove the winner text from the screen once restart button is pressed
            controller.restartGame(); // calls on restartGame method in controller
        });

        //reload game button
        Button reloadGame = new Button("Reload Game");
        reloadGame.setOnAction(e ->{
            controller.reloadGame(controller);
        });

        Button saveToDB = new Button("Save to DB");
        saveToDB.setOnAction(e -> {
            try {
                database.getConnection();
                addingLoadingDB addingLoadingDB = new addingLoadingDB();
                addingLoadingDB.saveGame(player1.getName(), player2.getName(), player1.getScore(), player2.getScore(), goalsToWin);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            saveAlert();
        });

        Button reloadFromDB = new Button("Reload from DB");
        reloadFromDB.setOnAction(e -> {
            try {
                database.getConnection();
                addingLoadingDB addingLoadingDB = new addingLoadingDB();
                addingLoadingDB.loadGame(player1, player2, player1.getScore(), player2.getScore(), goalsToWin);
                controller.updateScoreText();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            ball.getCircle().setTranslateX(width / 2);
            ball.getCircle().setTranslateY(height / 2);
        });

        //Layouts and alignment for player names and scores
        HBox playerBox = new HBox();
        playerBox.setAlignment(Pos.CENTER);

        VBox player2Box = new VBox(player2Text);
        player2Box.setAlignment(Pos.CENTER);

        VBox player1Box = new VBox(player1Text);
        player1Box.setAlignment(Pos.CENTER);

        playerBox.setSpacing(300);
        playerBox.getChildren().addAll(player1Box, player2Box);
        playerBox.setPrefSize(width, height / 6);

        HBox buttonBox = new HBox(30);
        buttonBox.getChildren().addAll(exitGame, restartBtn, saveGame, reloadGame, saveToDB, reloadFromDB);

        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        // gamePane which holds all game elements
        Pane gamePane = new Pane();
        gamePane.getChildren().addAll(rectangle.getRectangle(), rectangle1.getRectangle(),
                ball.getCircle(), playerBox, buttonBox);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;"); // setting colour again for this screen

        Image bgImage = new Image("file:src/backgroundgif.gif"); // backgroudn, this too stopped working ;/
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.fitWidthProperty().bind(stage.widthProperty());
        bgImageView.fitHeightProperty().bind(stage.heightProperty());
        root.getChildren().addAll(bgImageView, gamePane);

        // Listner for stage resizing and adjusting of elements
        ChangeListener<Number> resize = (observableValue, oldVal, newVal) ->{
            adjustingElems(playerBox); // method that adjusts the elements on resize
        };

        stage.widthProperty().addListener(resize);
        stage.heightProperty().addListener(resize);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        ball.getCircle().setTranslateX(width / 2);
        ball.getCircle().setTranslateY(height / 2);
        adjustingElems(playerBox); // method that adjusts the elements

        controller.movementX = speed; // movement of ball set
        controller.movementY = speed;

        //these are key event listners for paddle movement
        scene.setOnKeyPressed(event->{
            controller.keyPressed(event);
        });

        scene.setOnKeyReleased(event->{
            controller.keyReleased(event);
        });

        stage.show();
    }

    /**
     * This displays a message announcing a goal scored
     * @param message the goal message
     */
    public void showGoalMessage(String message){
        //initial creation for goal message
        Text goalMessage = new Text(message);
        goalMessage.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        goalMessage.setFill(Color.WHITE);goalMessage.setX(width / 2);
        goalMessage.setY(height / 2);

        // fetching the layout of the scene
        StackPane root = (StackPane) stage.getScene().getRoot();root.getChildren().add(goalMessage); // adding goal message, this also displays on top of everything
        StackPane.setAlignment(goalMessage, Pos.CENTER);

        //adding a fade out animation for goal message
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), goalMessage);fadeOut.setFromValue(1); // sets initial opacity
        fadeOut.setToValue(0); // sets final opacity
        fadeOut.setOnFinished(e -> root.getChildren().remove(goalMessage)); // this is an event handler that removes goalMessage from StackPane
        fadeOut.play();
    }

    /**
     * Creates a text object that displays the winner
     * @param winner the name of the winner of the game
     */
    public void showGameEndMessage(String winner){
        // creating the winner text
        Text winnerTextMessage = new Text("Game Over! \nThe winner is: " + winner);
        winnerTextMessage.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
        winnerTextMessage.setFill(Color.WHITE);

        // fetches the layout of the scene
        StackPane root = (StackPane) stage.getScene().getRoot();
        root.getChildren().add(winnerTextMessage);
        StackPane.setAlignment(winnerTextMessage, Pos.CENTER);
    }

    /**
     * Removes the winner message from the scene
     */
    public void removeWinnerMessage(){
        StackPane root = (StackPane) stage.getScene().getRoot();

        root.getChildren().removeIf(node -> node instanceof Text); // removes the winner message from root
    }

    /**
     * Adjusts the elements when the window is resized
     * @param playerBox the region representing the playerbox
     */
    public void adjustingElems(Region playerBox){
        width = stage.getWidth();
        height = stage.getHeight();

        Rectangle paddle1 = rectangle.getRectangle();
        Rectangle paddle2 = rectangle1.getRectangle();

        // adjusting positions of paddles
        paddle1.setX(10);
        paddle1.setY(height / 2.0 - paddle1.getHeight() / 2.0);
        paddle2.setX(width -  paddle2.getWidth() - 26);
        paddle2.setY(height / 2.0 - paddle2.getHeight() / 2.0);

        playerBox.setPrefSize(width, height / 6);
    }
    public void saveAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Saved Successfully");
        alert.setHeaderText(null);
        alert.setContentText("Game saved successfully!");
        alert.showAndWait();
    }
}
