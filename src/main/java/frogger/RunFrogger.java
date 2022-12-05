package org.cis120.frogger;

import javax.swing.*;
import java.awt.*;


/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunFrogger implements Runnable {
    public void run() {

        final JFrame frame = new JFrame("FROGGER");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("New Game");
        reset.addActionListener(e -> court.reset(true));
        control_panel.add(reset);


        final JOptionPane instructions = new JOptionPane();

        String words =
                "What's Frogger? \n" +
                "\n " +
                "Frogger is a classic arcade game in which the " +
                        "player takes frogs from the bottom of the screen \n" +
                "to the top while navigating obstacles. \n" +
                "\n " +
                "How do I play? \n" +
                "\n" +
                "Use the arrow keys to guide your " +
                        "frog from the starting position to the three lilly pads. \n" +
                "In order to win, you must guide one frog to each of the three lilly pads. \n" +
                "Avoid cars, trucks, and other vehicles on the streets. \n" +
                "Float on the logs to avoid drowning in the river. \n" +
                "Stay within the boundaries of the screen. \n" +
                "You have three lives, to which " +
                        "you can keep track of at the bottom of the screen.  \n" +
                "If you lose a life, use the space bar to activate " +
                "your next one and return to the starting position.  \n" +
                "\n " +
                "Because the game anticipates rage quits and saves its state, \n" +
                "you must initiate a new game by using the button at the top.\n" +
                "\n " +
                "Happy Hopping!\n" ;

        instructions.showMessageDialog(frame, words, "Welcome to Frogger!"
                , JOptionPane.INFORMATION_MESSAGE);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset(false);
    }
}