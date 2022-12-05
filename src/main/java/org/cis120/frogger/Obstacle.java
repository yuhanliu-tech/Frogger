package org.cis120.frogger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Parent class for obstacles in the game.
 *
 * Although this implements inheritance and subtyping, the strip classes may be a better example,
 * this exists mostly for convenience and overriding draw functions.
 *
 */

public class Obstacle extends GameObj {

    private BufferedImage img;

    public Obstacle(int courtWidth, int courtHeight, int speed, int y, BufferedImage img, int initX,
                    int sizeX, int sizeY) {
        super(speed, 0, initX, y, sizeX, sizeY, courtWidth, courtHeight);
        this.img = img;
    }

    @Override
    public void draw(Graphics g) {
        if (img == null) {
            g.setColor(Color.YELLOW);
            g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        }
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}

