
package horstman_project7;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Main extends JFrame {

    //Double buffering
    Image dbImage;
    Graphics dbg;
    //graphics
    BufferedImage background;
    BufferedImageLoader loader = new BufferedImageLoader();
    //
    static Player player1 = new Player(200, 700);
    public static Enemy enemys = new Enemy();
    //creates threads
    Thread enemy = new Thread(enemys);
    Collision testing = new Collision();
    Thread hitTesting = new Thread(testing);
    Thread p1 = new Thread(player1);
    //boolean value used to manage game states without a proper FSM
    public static boolean gameStarted = false;
    boolean gamePaused = false;
    boolean scoreScreen = false;
    boolean firstStart = true;
    boolean firesetting = false;
    boolean startHover;
    boolean quitHover;
    boolean scoreHover;
    boolean fireHover;
    //Menu Buttons
    Rectangle startButton = new Rectangle(400, 200, 100, 25);
    Rectangle quitButton = new Rectangle(400, 300, 100, 25);
    Rectangle scoreButton = new Rectangle(400, 250, 100, 25);
    Rectangle settingsButton = new Rectangle(400, 350, 100, 25);
    Rectangle fireButton = new Rectangle(660, 765, 50, 25);
    //Variables for screen size
    int GWIDTH = 900,
            GHEIGHT = 800;
    //Dimension of GWIDTH*GHEIGHT
    Dimension screenSize = new Dimension(GWIDTH, GHEIGHT);
    SoundManger s = new SoundManger() {
        @Override
        public void initSounds() {
            sounds.add(new Sound("Theme", Sound.getURL("theme.wav")));

        }
    };

    //Creates main window
    public Main() {

        this.setTitle("Dragon Hunter");
        this.setSize(screenSize);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.DARK_GRAY);
        this.addKeyListener(new KeyHandler());
        this.addMouseListener(new MouseHandler());
        this.addMouseMotionListener(new MouseHandler());
        this.setLocationRelativeTo(null);
        enemys.addEnemy();
        try {
            //gets high score from file
            getHighScores();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    /*
     * changes the game state to started by starting all the threads and  changing gameStarted to true
     */

    public void startGame() {
        gameStarted = true;


        if (firstStart) {
            JOptionPane.showMessageDialog(rootPane, "Use the spacebar to launch arrows and the arrow keys to control the player", "instructions", WIDTH);
            enemy.start();
            p1.start();
            s.loopSound("Theme");


            hitTesting.start();
            firstStart = false;
        }


    }

    public static void main(String[] args) {


        Main m = new Main();
    }

    @Override
    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());


        dbg = dbImage.getGraphics();
        draw(dbg);


        g.drawImage(dbImage, 0, 0, this);
    }

    public void draw(Graphics g) {
        String startText = "Start Game";
        String fire;
        if (player1.gameover) {
            gameStarted = false;



            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", 375, 75);

            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.GRAY);
            if (!quitHover) {
                g.setColor(Color.CYAN);
            } else {
                g.setColor(Color.PINK);
            }
            g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
            g.setColor(Color.GRAY);
            g.drawString("Quit Game", quitButton.x + 20, quitButton.y + 17);

            s.stopAllSounds();

        }
        if (player1.wins) {
            gameStarted = false;

            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.setColor(Color.WHITE);
            g.drawString("YOU WIN!!!!!!!!!!!!!!!!!!!!!!!!!", 375, 75);

            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.setColor(Color.GRAY);
            if (!quitHover) {
                g.setColor(Color.CYAN);
            } else {
                g.setColor(Color.PINK);
            }
            g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
            g.setColor(Color.GRAY);
            g.drawString("The End", quitButton.x + 20, quitButton.y + 17);

            s.stopAllSounds();

        }

        if (gamePaused && !gameStarted) {
            startText = " Resume";

        }
        if (!gameStarted && !scoreScreen && !player1.gameover && !player1.wins) {
            printMenu(g);
        } else if (gameStarted) {
            //Game drawings
            try {
                background = loader.loadImage("background.png");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            g.drawImage(background, 0, 0, this);


            enemys.draw(g);



            //b.p1.draw(g);
            player1.draw(g);
            //Scores
            g.setColor(Color.WHITE);

            g.drawString("Score: " + player1.score, this.GWIDTH - 70, 50);
            g.drawString("High score: " + player1.highscore, 20, 50);


            if (firesetting) {
                g.setFont(new Font("Arial", Font.BOLD, 12));

                if (!fireHover) {
                    g.setColor(Color.CYAN);
                } else {
                    g.setColor(Color.PINK);
                }
                g.fillRect(fireButton.x, fireButton.y, fireButton.width, fireButton.height);
                g.setColor(Color.GRAY);
                g.drawString("fire", fireButton.x + 20, fireButton.y + 17);
            }




        } else if (scoreScreen) {
            //Game drawings
            printHighScores(g);



        }
//               


        repaint();
    }

    public void printMenu(Graphics g) {
        String fire;
        // sets the text for the fire setting button
        if (firesetting) {
            fire = "on";
        } else {
            fire = "off";
        }
        //sets the text for the start button
        String startText = "Start Game";
        if (gamePaused && !gameStarted && !firstStart) {
            startText = " Resume";

        }

        //Menu
        g.setFont(new Font("Arial", Font.BOLD, 26));
        g.setColor(Color.WHITE);
        g.drawString("Dragon Hunter", 375, 75);
        if (!startHover) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.PINK);
        }

        g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.setColor(Color.GRAY);
        g.drawString(startText, startButton.x + 20, startButton.y + 17);
        //highscore
        if (!scoreHover) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.PINK);
        }
        g.fillRect(scoreButton.x, scoreButton.y, scoreButton.width, scoreButton.height);
        g.setColor(Color.GRAY);
        g.drawString("High Score", scoreButton.x + 20, scoreButton.y + 17);

        //quit button
        if (!quitHover) {
            g.setColor(Color.CYAN);
        } else {
            g.setColor(Color.PINK);
        }
        g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
        g.setColor(Color.GRAY);
        g.drawString("Quit Game", quitButton.x + 20, quitButton.y + 17);
        //settings
        if (firesetting) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        g.drawString("fire Button:" + fire, settingsButton.x + 20, settingsButton.y + 17);






    }
    /*
     * prints the high scores from the highscore.txt file however due to time this is ot fully baked
     */

    public void printHighScores(Graphics g) {
        try {
            int y = 0;
            ArrayList<Player> scores = getHighScores();


            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.setColor(Color.WHITE);
            g.drawString("High Score", 375, 75);

            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.setColor(Color.WHITE);




            for (int x = 0; x < 10; x++) {
                g.drawString((x + 1) + " " + scores.get(x).name + "   " + scores.get(x).score, 15, 200 + y);
                y += 20;
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    public class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == e.VK_ESCAPE) {
                gameStarted = false;
                gamePaused = true;
            }

            player1.keyPressed(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == e.VK_ESCAPE) {
                if (gameStarted) {
                    gameStarted = false;
                    gamePaused = true;




                }


                scoreScreen = false;

            }
            if (e.getKeyCode() == e.VK_SPACE) {



                player1.keyPressed(e);





            }


            player1.keyReleased(e);
        }
    }

    public class MouseHandler extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            int mx = e.getX();
            int my = e.getY();
            //start
            if (mx > startButton.x && mx < startButton.x + startButton.width
                    && my > startButton.y && my < startButton.y + startButton.height) {
                startHover = true;
            } else {
                startHover = false;
            }
            //quit
            if (mx > quitButton.x && mx < quitButton.x + quitButton.width
                    && my > quitButton.y && my < quitButton.y + quitButton.height) {
                quitHover = true;
            } else {
                quitHover = false;
            }
            //fire
            if (mx > fireButton.x && mx < fireButton.x + fireButton.width
                    && my > fireButton.y && my < fireButton.y + fireButton.height) {
                fireHover = true;
            } else {
                fireHover = false;
            }
            //highschool 
            if (mx > scoreButton.x && mx < scoreButton.x + scoreButton.width
                    && my > scoreButton.y && my < scoreButton.y + scoreButton.height) {
                scoreHover = true;
            } else {
                scoreHover = false;
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int mx = e.getX();
            int my = e.getY();
            if (mx > startButton.x && mx < startButton.x + startButton.width
                    && my > startButton.y && my < startButton.y + startButton.height) {
                //starts the game
                startGame();

            }
            if (mx > quitButton.x && mx < quitButton.x + quitButton.width
                    && my > quitButton.y && my < quitButton.y + quitButton.height) {
                //closes program
                if (!gameStarted) {
                    System.exit(0);
                }

            }
            if (mx > fireButton.x && mx < fireButton.x + fireButton.width
                    && my > fireButton.y && my < fireButton.y + fireButton.height) {
                //closes program
                if (gameStarted) {
                    player1.addArrow();
                }

            }
            if (mx > scoreButton.x && mx < scoreButton.x + scoreButton.width
                    && my > scoreButton.y && my < scoreButton.y + scoreButton.height) {
                scoreScreen = true;


            }
            if (mx > settingsButton.x && mx < settingsButton.x + settingsButton.width
                    && my > settingsButton.y && my < settingsButton.y + settingsButton.height) {
                firesetting = !firesetting;


            }
            // highscore 

            ;


        }
    }

    /*
     * reads in the high scores from the highscore.txt file however due to time this is ot fully baked
     */
    public ArrayList<Player> getHighScores() throws IOException {
        String input;
        ArrayList<Player> scores = new ArrayList<Player>();
        BufferedReader in = new BufferedReader(new FileReader("highscores.txt"));
        while ((input = in.readLine()) != null) {
            String[] inputSplit = input.split(" ");
            scores.add(new Player(inputSplit[0], Integer.parseInt(inputSplit[1])));



        }
        in.close();

//        Collections.sort(scores, new Comparator<> {
//            public int compareTo(Player one, Player two) {
//               return new Integer(one.highscore).compareTo(new Integer(two.highscore));
//            }
//    });
        player1.highscore = scores.get(0).score;


        return scores;



    }
}
