package org.cis120.frogger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Floating log obstacles.
 */

public class Log extends Obstacle {

    public static final int SIZEX = 200;
    public static final int SIZEY = 59;
    private BufferedImage img;

    public Log(int courtWidth, int courtHeight, int speed, int y, BufferedImage img, int initX) {
        super(courtWidth, courtHeight, speed, y, img, initX, SIZEX, SIZEY);
        this.img = img;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
