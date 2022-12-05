package org.cis120.frogger;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.NoSuchElementException;


/**
 * GameCourt
 *
 * This class holds the primary game logic for how different objects interact
 * with one another.
 *
 */
public class GameCourt extends JPanel {

    // the state of the game logic
    private Frog frog; // frog, keyboard control

    //streets
    private Strip street1;
    private Strip street2;
    private Strip street3;
    private Strip street4;
    private Strip street5;

    //rivers
    private Strip river1;
    private Strip river2;
    private Strip river3;
    private Strip river4;

    //file i/o implementation that saves state
    GameSaver saver = new GameSaver();

    //storage for all strips in game
    LinkedList<Strip> strips;

    //line of lillypads at top of screen
    private WinStrip end;

    private boolean won;
    private boolean playing = false; // whether the game is running

    private final JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 600;
    public static final int FROG_VELOCITY = 50;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);
        won = false;
        strips = new LinkedList<Strip>();

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int posX = frog.getPx();
                int posY = frog.getPy();

                //hitting space after dying will activate a remaining life
                if (e.getKeyCode() == KeyEvent.VK_SPACE && frog.getIsFrozen()) {
                    if (frog.getLives() > 0) {
                        frog.revive();
                    }
                } else if (!frog.getIsFrozen()) {

                    //frog keyboard controls
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        frog.setPx(posX - FROG_VELOCITY);
                        frog.setImage("left");
                    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        frog.setPx(posX + FROG_VELOCITY);
                        frog.setImage("right");
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        frog.setPy(posY + FROG_VELOCITY);
                        frog.setImage("down");
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        frog.setPy(posY - FROG_VELOCITY);
                        frog.setImage("up");
                    }
                }

            }

            public void keyReleased(KeyEvent e) {
                frog.setVx(0);
                frog.setVy(0);
            }
        });

        this.status = status;
    }

    //helpful functions for testing

    public boolean getWon() {
        return won;
    }

    public boolean getPlaying() {
        return playing;
    }

    private int[] getPosFromFile(FileLineIterator it) {

        try {
            String str = it.next();
            int[] arr = saver.toArr(str);
            return arr;
        } catch (NoSuchElementException e) {
            return new int[0];
        }

    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset(boolean newGame) {

        frog = new Frog(COURT_WIDTH, COURT_HEIGHT);

        //instantiate all strips

        end = new WinStrip(0, 0, false);

        street1 = new Street(300, 2, true);
        street2 = new Street(350, 5, false);
        street3 = new Street(400, 6, true);
        street4 = new Street(450, 4, false);
        street5 = new Street(500, 3, true);

        river1 = new River(50, 7, false);
        river2 = new River(100, 5, true);
        river3 = new River(150, 2, false);
        river4 = new River(200, 6, true);

        int lives = 3;

        if (!newGame) {

            // this code runs if the file is meant to restore an exiting game state

            FileReader r = null;

            try {
                r = new FileReader(new File("files/state.txt"));
            } catch (IOException e) {
                final JOptionPane err = new JOptionPane();
                err.showMessageDialog(new JFrame("ERROR MESSAGE"), "FILE ERROR", "ERROR",
                        JOptionPane.ERROR_MESSAGE);

            }

            BufferedReader br = new BufferedReader(r);

            FileLineIterator it = new FileLineIterator(br);

            while (it.hasNext()) {

                try {

                    end.setFinishedFrogs(Integer.parseInt(it.next()));

                    frog.setPx(Integer.parseInt(it.next()));
                    frog.setPy(Integer.parseInt(it.next()));

                    lives = Integer.parseInt(it.next());

                    end.setBin(it.next());

                    street1.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    street2.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    street3.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    street4.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    street5.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));

                    river1.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    river2.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    river3.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));
                    river4.forceNewObs(COURT_WIDTH, COURT_HEIGHT, getPosFromFile(it));


                } catch (IllegalArgumentException e) {

                    System.out.println("No More Lines");

                }
            }

        } else {

            //this code runs to create a fresh game

            end.setBin("000");

            int[] empty = new int[0];

            saver.updateFile(end.getFinishedFrogs(), frog.getPx(), frog.getPy(), lives, "000",
                    empty, empty, empty, empty, empty, empty, empty, empty, empty);
            frog.setImage("up");

            street1.addNewObs(COURT_WIDTH,COURT_HEIGHT);
            street2.addNewObs(COURT_WIDTH,COURT_HEIGHT);
            street3.addNewObs(COURT_WIDTH,COURT_HEIGHT);
            street4.addNewObs(COURT_WIDTH,COURT_HEIGHT);
            street5.addNewObs(COURT_WIDTH,COURT_HEIGHT);
            river1.addNewObs(COURT_WIDTH, COURT_HEIGHT);
            river2.addNewObs(COURT_WIDTH, COURT_HEIGHT);
            river3.addNewObs(COURT_WIDTH, COURT_HEIGHT);
            river4.addNewObs(COURT_WIDTH, COURT_HEIGHT);

        }

        end.addNewObs(COURT_WIDTH, COURT_HEIGHT);

        street1.newObs(COURT_WIDTH,COURT_HEIGHT, 5000);
        street2.newObs(COURT_WIDTH,COURT_HEIGHT, 2000);
        street3.newObs(COURT_WIDTH,COURT_HEIGHT, 3000);
        street4.newObs(COURT_WIDTH,COURT_HEIGHT, 5000);
        street5.newObs(COURT_WIDTH,COURT_HEIGHT, 4500);

        river1.newObs(COURT_WIDTH,COURT_HEIGHT, 4000);
        river2.newObs(COURT_WIDTH,COURT_HEIGHT, 5000);
        river3.newObs(COURT_WIDTH,COURT_HEIGHT, 8000);
        river4.newObs(COURT_WIDTH,COURT_HEIGHT, 6000);


        strips.add(street1);
        strips.add(street2);
        strips.add(street3);
        strips.add(street4);
        strips.add(street5);
        strips.add(river1);
        strips.add(river2);
        strips.add(river3);
        strips.add(river4);

        playing = true;
        status.setText("Lives: " + frog.getLives() + " | Progress: " + (550 - frog.getPy()) +
                " | Frogs Delivered: " + end.getFinishedFrogs());

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    void tick() {
        if (playing) {

            status.setText("Lives: " + frog.getLives() + " | Progress: " + (550 - frog.getPy()) +
                    " | Frogs Delivered: " + end.getFinishedFrogs());

            frog.move();

            street1.start();
            street2.start();
            street3.start();
            street4.start();
            street5.start();

            river1.start();
            river1.floatFrog(frog);

            river2.start();
            river2.floatFrog(frog);

            river3.start();
            river3.floatFrog(frog);

            river4.start();
            river4.floatFrog(frog);

            end.checkFinishedFrog(frog);

            //check death

            if (!frog.getIsFrozen() && frog.getPy() != 0 && (street1.checkCollision(frog)
                    || street2.checkCollision(frog)
                    || street3.checkCollision(frog) || street4.checkCollision(frog)
                    || street5.checkCollision(frog) || frog.outtaBounds()
                    || river1.checkCollision(frog) || river2.checkCollision(frog)
                    || river3.checkCollision(frog) || river4.checkCollision(frog)
                    || end.checkCollision(frog))) {
                frog.die();
                if (frog.getLives() == 0) {
                    playing = false;
                    status.setText("Game Over!");
                }

                //check victory

            } else if (end.getFinishedFrogs() == 3) {
                playing = false;
                won = true;
                status.setText("You win!");
            } else if (frog.getPy() == 0) {
                frog.setPy(550);
                frog.setPx(220);
            }

            //save file state

            saver.updateFile(end.getFinishedFrogs(), frog.getPx(),
                    frog.getPy(), frog.getLives(),
                    end.convertToBin(), street1.getObsPos(),
                    street2.getObsPos(),
                    street3.getObsPos(), street4.getObsPos(),
                    street5.getObsPos(),
                    river1.getObsPos(), river2.getObsPos(),
                    river3.getObsPos(), river4.getObsPos());

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(179, 0, 255));
        g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);

        street1.drawStrip(g);
        street1.drawObs(g);

        street2.drawStrip(g);
        street2.drawObs(g);

        street3.drawStrip(g);
        street3.drawObs(g);

        street4.drawStrip(g);
        street4.drawObs(g);

        street5.drawStrip(g);
        street5.drawObs(g);

        river1.drawStrip(g);
        river1.drawObs(g);

        river2.drawStrip(g);
        river2.drawObs(g);

        river3.drawStrip(g);
        river3.drawObs(g);

        river4.drawStrip(g);
        river4.drawObs(g);

        end.drawStrip(g);
        end.drawObs(g);

        frog.draw(g);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}