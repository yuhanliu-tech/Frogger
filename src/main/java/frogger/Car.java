package org.cis120.frogger;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Car Obstacles
 */

public class Car extends Obstacle {

    public static final int SIZE = 70;
    private BufferedImage img;


    public Car(int courtWidth, int courtHeight, int speed, int y, BufferedImage img, int initX) {
        super(courtWidth, courtHeight, speed, y, img, initX, SIZE, SIZE);

        this.img = img;

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
}
