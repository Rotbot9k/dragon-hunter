/*
 * Clayton horstman
 * your lab section:13		
 * your lab TA’s name: vinh nquyen
 * your instructor’s name:Dr DeJongh
 */
package horstman_project7;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author viro101
 */
public class Collision implements Runnable {

    public Collision() {
    }

    @Override
    /*
     * this thread concurrently looks at the arrows and  dragon to detect a collision right when it happens
     */
    public void run() {
        try {
            while (true) {

                if (!Enemy.mobs.isEmpty() && !Player.arrows.isEmpty()) {
                    for (int x = 0; x < Player.arrows.size(); x++) {
                        for (int y = 0; y < Enemy.mobs.size(); y++) {
                            if (Player.arrows.get(x).arrowsBounds.intersects(Enemy.mobs.get(y).bounds)) {
                                Enemy.mobs.get(y).proccesscollision(y);
                                Player.arrows.get(x).proccesscollision();




                            }



                        }
                    }

                }


                Thread.sleep(30);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Collision.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
