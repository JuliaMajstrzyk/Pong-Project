package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

/**
 * Rectangle class which represents a rectangle object which may be known as paddles.
 */
public class rectangle implements Serializable{
    public int width;
    public int height;
    public Rectangle rectangle;

    /**
     * Constructs a new rectangle with specified widht, height and colour
     * @param w width of the rectangle (paddle)
     * @param h height of the rectangle (paddle)
     * @param c colour of the rectangle (paddle)
     */
    public rectangle(int w, int h, Color c){
        this.width = w;
        this.height = h;
        this.rectangle = new Rectangle(w, h, c); // created rectangle obejct
    }

    /**
     * Sets size of retangle to specified width and height
     * @param w the new width of the rectangle(paddle)
     * @param h the new height of the rectangle(paddle)
     */
    public void setSize(int w, int h){
        rectangle.setWidth(w);
        rectangle.setHeight(h);
    }

    /**
     * Retrieves rectangle object
     * @return rectangle object
     */
    public Rectangle getRectangle(){
        return rectangle;
    }

}