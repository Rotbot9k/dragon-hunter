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
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/*
 * this thread controls everything enemy related
 */
public class Enemy implements Runnable {

    //Gloabal variables
    int x, y, xDirection, yDirection;
    int count;
    BufferedImage spriteSheet;
    BufferedImage sprite;
    BufferedImageLoader loader = new BufferedImageLoader();
    SpriteSheet ss;
    ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    public static ArrayList<Dragon> mobs = new ArrayList<Dragon>();
    int updateTimer = 1;
    public static int totalKilled;
    static int updateSpeed = 8;
    int spawnTimer = 0;
    public Rectangle bounds;
    //used to play sound
    SoundManger s = new SoundManger() {
        @Override
        public void initSounds() {
            sounds.add(new Sound("pain", Sound.getURL("pain.wav")));


        }
    };

    public Enemy() {


        //Set ball moving randomly

        setXDirection(1);



    }

    public void setXDirection(int xdir) {
        xDirection = xdir;
    }

    /*
     * calls all the draw methods for dragons
     */
    public void draw(Graphics g) {

        for (int x = 0; x < mobs.size(); x++) {
            mobs.get(x).draw(g);
        }




    }
    /*
     * place holder method
     */

    public void proccesscollision() {
    }
    /*
     * 
     */

    public void move() {
        for (int x = 0; x < mobs.size(); x++) {
            mobs.get(x).move();

        }



    }
    /*
     * place holder
     */

    public void update() {
    }

    /*
     * adds new enemies aka dragin to the mobs arraylist
     */
    public void addEnemy() {


        Dragon enemy2 = new Dragon(50, 50);

        mobs.add(enemy2);


    }

    @Override
    public void run() {
        try {


            while (true) {





                move();
                // times how long to wait to update the sprite animation
                if (updateTimer == updateSpeed) {
                    for (int x = 0; x < mobs.size(); x++) {

                        mobs.get(x).update();

                    }
                    updateTimer = 0;
                }
                //times whe to spawn a new mob then resets the timer
                if (spawnTimer == 1000) {
                    addEnemy();
                    spawnTimer = 0;
                }



                updateTimer++;
                spawnTimer++;
                Thread.sleep(20);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
