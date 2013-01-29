
package horstman_project7;

import horstman_project7.BufferedImageLoader;
import horstman_project7.Enemy;
import horstman_project7.Main;
import horstman_project7.SpriteSheet;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author viro
 */

/*
 * this is the main dragon class
 */
public class Dragon extends Enemy {

    int x, y, xDirection;
    int count;
    //graphics
    BufferedImage spriteSheet;
    BufferedImage sprite;
    BufferedImage lcollisionSprite;
    BufferedImage rcollisionSprite;
    BufferedImageLoader loader = new BufferedImageLoader();
    SpriteSheet ss;
    ArrayList<BufferedImage> leftsprites = new ArrayList<BufferedImage>();
    ArrayList<BufferedImage> rightsprites = new ArrayList<BufferedImage>();
    int spriteCounter = 0;
    //stats
    int hp;
    int pointValue;
    boolean collision = false;
    int offset;

    public Dragon(int x, int y) {

        //this ranomly picks if the dragon will spawn on the left or right
        int side = 1 + (int) (Math.random() * ((100 - 1) + 1));
        if (side > 50) {
            this.x = 850;
        }
        if (side < 50) {
            this.x = 0;
        }
        this.y = y;
        //this randomly chooses which type of dragin to spawn however i was only able to finish 2
        int choice = 1 + (int) (Math.random() * ((100 - 1) + 1));
        if (choice > 50) {
            whiteDragon();
        }
        if (choice < 50) {
            blackDragon();
        }



    }

    /*
     * sets which direction the dragon will go
     */
    public void setXDirection(int xdir) {
        xDirection = xdir;
    }

    /*
     * draws out the graphics for teh dragon
     */
    public void draw(Graphics g) {

        //dectects direction and if the dragon is hit to figure out what sprite to draw to the screen
        if (xDirection > 0 && !collision) {
            g.drawImage(rightsprites.get(spriteCounter), super.bounds.x, super.bounds.y, null);
        }
        if (xDirection < 0 && !collision) {
            g.drawImage(leftsprites.get(spriteCounter), super.bounds.x, super.bounds.y, null);
        }

        if (xDirection > 0 && collision) {
            g.drawImage(rcollisionSprite, super.bounds.x, super.bounds.y, null);



        }
        if (xDirection < 0 && collision) {
            g.drawImage(lcollisionSprite, super.bounds.x, super.bounds.y, null);

        }











    }

    /*
     * procceses sounds and stat changes when the dragon is hit
     */
    public void proccesscollision(int index) {
        //place holder
        super.updateTimer = 0;
        collision = true;
        hp--;


        if (hp <= 0) {
            //procceses the dragon death
            Enemy.mobs.remove(this);
            Main.player1.score += pointValue;
            super.totalKilled++;
        }

        super.s.playSound("pain");
        super.updateSpeed = 44;






    }
    /*
     * updates the place of the sprite animation
     */

    public void update() {
        if (spriteCounter >= leftsprites.size() - 1) {
            spriteCounter = 0;

        }
        // used to make the collision sprite stay on the screen long enough to be seen
        collision = false;

        super.updateSpeed = 8;

        spriteCounter++;





    }

    public void move() {

        super.bounds.x += xDirection;

        //Bounce the ball when edge is detected
        if (super.bounds.x <= 0) {
            setXDirection(+1);
            bounds.x += 1;
            count++;

        }
        if (super.bounds.x >= 850) {
            setXDirection(-1);
            bounds.x -= 1;
            count++;
        }

        if (count > 3) {
            count = 0;
            bounds.y += 30;


        }


    }
    /*
     * initialzes the black dragon type
     */

    private void blackDragon() {

        setXDirection(1);
        offset = 40;
        //sets hp and value of the dragon
        hp = 2;
        pointValue = 2;

        //sets up the sprite arraylist for the animation 
        try {
            spriteSheet = loader.loadImage("blackdragon.png");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SpriteSheet ss = new SpriteSheet(spriteSheet);

        leftsprites.add(ss.grabSprite(3, 5, 50, 54));
        leftsprites.add(ss.grabSprite(56, 5, 50, 54));
        leftsprites.add(ss.grabSprite(104, 5, 50, 54));
        leftsprites.add(ss.grabSprite(154, 5, 50, 54));
        leftsprites.add(ss.grabSprite(203, 5, 50, 54));
        rightsprites.add(ss.grabSprite(450, 5, 50, 54));
        rightsprites.add(ss.grabSprite(403, 5, 50, 54));
        rightsprites.add(ss.grabSprite(356, 5, 50, 54));
        rightsprites.add(ss.grabSprite(305, 5, 50, 54));
        rightsprites.add(ss.grabSprite(254, 5, 50, 54));
        lcollisionSprite = ss.grabSprite(214, 548, 50, 54);
        rcollisionSprite = ss.grabSprite(272, 548, 50, 54);


        sprite = leftsprites.get(0);




        super.bounds = new Rectangle(this.x, this.y + offset, 50, 54);

    }
    /*
     * initialzes the white dragon type
     */

    private void whiteDragon() {
        setXDirection(-1);
        offset = 30;
        //sets hp and value of the dragon
        hp = 3;
        pointValue = 1;


        //sets up the sprite arraylist for the animation
        try {
            spriteSheet = loader.loadImage("whitedragon.png");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        SpriteSheet ss = new SpriteSheet(spriteSheet);

        rightsprites.add(ss.grabSprite(0, 0, 105, 96));
        rightsprites.add(ss.grabSprite(0, 0, 105, 96));
        rightsprites.add(ss.grabSprite(5, 197, 90, 96));
        rightsprites.add(ss.grabSprite(5, 197, 90, 96));
        rightsprites.add(ss.grabSprite(104, 0, 105, 96));
        rightsprites.add(ss.grabSprite(104, 0, 105, 96));
        //right
        leftsprites.add(ss.grabSprite(342, 7, 100, 96));
        leftsprites.add(ss.grabSprite(342, 7, 100, 96));
        leftsprites.add(ss.grabSprite(235, 7, 105, 96));
        leftsprites.add(ss.grabSprite(235, 7, 105, 96));
        leftsprites.add(ss.grabSprite(358, 197, 90, 96));
        leftsprites.add(ss.grabSprite(358, 197, 90, 96));



        // hit 
        rcollisionSprite = ss.grabSprite(88, 196, 105, 96);
        lcollisionSprite = ss.grabSprite(244, 202, 105, 96);



        sprite = leftsprites.get(0);


        super.bounds = new Rectangle(this.x, this.y + offset, 105, 94);



    }
}
