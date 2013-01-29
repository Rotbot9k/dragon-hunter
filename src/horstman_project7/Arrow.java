/*
 * Clayton horstman
 * your lab section:13		
 * your lab TA’s name: vinh nquyen
 * your instructor’s name:Dr DeJongh
 */
package horstman_project7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clayton horstman
 *
 */
public class Arrow {

    //Gloabal variables
    int x, y;
    BufferedImage sprite;
    BufferedImageLoader loader = new BufferedImageLoader();
    Rectangle arrowsBounds;

    public Arrow(int x, int y) {

        this.x = x;
        this.y = y;

        //loads the arrow sprite
        try {
            sprite = loader.loadImage("arrow.png");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


        arrowsBounds = new Rectangle(this.x, this.y + 40, 12, 44);
    }

    public void draw(Graphics g) {
//        g.setColor(Color.WHITE);
//        g.fillRect(arrowsBounds.x, arrowsBounds.y, arrowsBounds.width, arrowsBounds.height);
        g.drawImage(sprite, arrowsBounds.x, arrowsBounds.y, null);
    }

    public void move() {


        arrowsBounds.y -= 3;



    }
    /*
     * removes the arrow if a collision is detected bt the collision thread
     */

    public void proccesscollision() {


        Player.arrows.remove(this);





    }
}
