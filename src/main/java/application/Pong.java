package application;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main class for my pong game
 */
public class Pong extends Application{
    /**
     * This method launches the app
     * @param args
     */
    public static void main(String[] args){
        //heap.heap();
        //stack.stack();
        launch(args);
    }

    /**
     * This overriden method starts the app
     * @param stage the stage for the app
     */
    @Override
    public void start(Stage stage){
        ViewPong viewPong = new ViewPong(stage);
        viewPong.initialize(); // initialising pongview
    }
}
