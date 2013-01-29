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
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Player implements Runnable {

    int x, y, xDirection;
    String name;
    public int score = 0;
    int highscore;
    int timer;
    BufferedImage spriteSheet;
    BufferedImage sprite;
    BufferedImageLoader loader = new BufferedImageLoader();
    SpriteSheet ss;
    ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
    static ArrayList<Arrow> arrows = new ArrayList<Arrow>();
    ArrayList<BufferedImage> shooting = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> reloading = new ArrayList<BufferedImage>();
    boolean outOfAmmo = false;
    boolean ready = true;
    int misses = 0;
    boolean gameover = false;
    boolean wins = false;
    Rectangle p1;
    SoundManger s = new SoundManger() {
        @Override
        public void initSounds() {
            sounds.add(new Sound("launch", Sound.getURL("launch.wav")));
            sounds.add(new Sound("loading", Sound.getURL("loading.wav")));

        }
    };

    public Player(String name, int score) {
        this.name = name;
        this.score = score;

    }

    public Player(int x, int y) {
        this.x = x;
        this.y = y;

        p1 = new Rectangle(x, y, 10, 10);
        //grabs the sprites
        try {
            spriteSheet = loader.loadImage("ballista.png");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SpriteSheet ss = new SpriteSheet(spriteSheet);

        //normal movement
        sprites.add(ss.grabSprite(0, 132, 65, 62));
        sprites.add(ss.grabSprite(131, 135, 66, 55));
        sprites.add(ss.grabSprite(264, 210, 66, 63));
        // sprites.add(ss.grabSprite(67, 0, 66, 55));

        // shooting animation
        shooting.add(ss.grabSprite(0, 66, 65, 62));
        shooting.add(ss.grabSprite(0, 197, 65, 62));
        //set of sprites when waiting on reload
        reloading.add(ss.grabSprite(0, 197, 65, 62));
        reloading.add(ss.grabSprite(131, 199, 66, 55));
        reloading.add(ss.grabSprite(264, 139, 66, 63));
        sprite = sprites.get(0);







    }
    /*
     * adds up every time the player misses
     */

    public void proccessMiss() {


        misses++;





    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == e.VK_LEFT) {
            ready = false;
            setXDirection(-2);
            if (outOfAmmo) {
                sprite = reloading.get(2);
            } else {
                sprite = sprites.get(2);
            }
            this.x = -1;
        }
        if (e.getKeyCode() == e.VK_RIGHT) {
            //
            ready = false;
            setXDirection(+2);
            if (outOfAmmo) {
                sprite = reloading.get(1);
            } else {
                sprite = sprites.get(1);
            }


            //sprite = sprites.get(1);
            this.x += 1;


        }





    }

    public void keyReleased(KeyEvent e) {



        if (e.getKeyCode() == e.VK_RIGHT) {
            ready = true;
            setXDirection(0);
            if (outOfAmmo) {
                sprite = reloading.get(0);
            } else {
                sprite = sprites.get(0);
            }

        }
        if (e.getKeyCode() == e.VK_LEFT) {
            ready = true;
            setXDirection(0);
            if (outOfAmmo) {
                sprite = reloading.get(0);
            } else {
                sprite = sprites.get(0);
            }
        }

        if (e.getKeyCode() == e.VK_SPACE) {

            if (!outOfAmmo && ready) {

                s.playSound("launch");

                addArrow();


            }

        }


    }

    public void setXDirection(int xdir) {
        xDirection = xdir;

    }

    public void move() {

        for (int x = 0; x < arrows.size(); x++) {
            arrows.get(x).move();
            if (arrows.get(x).arrowsBounds.y <= 0) {
                misses++;
                arrows.remove(x);
            }

        }

        p1.x += xDirection;
        if (p1.x <= 8) {
            p1.x = 8;
        }
        if (p1.x >= 830) {
            p1.x = 830;
        }

    }

    public void draw(Graphics g) {


        g.drawImage(sprite, p1.x, p1.y - 40, null);
        for (int x = 0; x < arrows.size(); x++) {
            arrows.get(x).draw(g);

        }







    }
    /*
     * adds arrow to the screen
     */

    public void addArrow() {



        if (!outOfAmmo && ready) {

            s.playSound("launch");

            Arrow shot = new Arrow(p1.x + 26, p1.y - 62);
            sprite = shooting.get(0);
            sprite = shooting.get(1);
            arrows.add(shot);
            outOfAmmo = true;
            timer = 0;

        }






    }

    @Override
    public void run() {
        try {
            while (true) {



                timer++;

                move();


                if (outOfAmmo && timer == 250) {
                    outOfAmmo = false;
                    s.playSound("loading");
                    if (this.xDirection == 0) {

                        sprite = sprites.get(0);

                    }

                }
                if (misses >= 10) {
                    gameover = true;
                }
                if (score >= 20) {
                    wins = true;
                }

                Thread.sleep(8);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
