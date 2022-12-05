package org.cis120.frogger;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.*;

/**
 * River strip objects
 */

public class River extends Strip {

    public static final int SIZE = 60;
    private static final String IMG_FILE = "files/log.png";

    public River(int y, int speed, boolean goLeft) {

        super(y,speed, goLeft);

    }

    public boolean inWater(Frog frog) {
        int frogY = frog.getPy();
        int y = getY();
        return ((frogY >= y) && (frogY <= SIZE + y - 11));
    }

    public void floatFrog(Frog f) {
        LinkedList<Obstacle> logs = getObs();
        for (Obstacle log : logs) {

            if (f.intersects(log) && !f.getIsFrozen()) {
                f.setVx(getSpeed());
            }

        }
    }

    @Override
    public boolean checkCollision(Frog frog) {
        boolean collision = false;
        LinkedList<Obstacle> logs = getObs();
        for (Obstacle log : logs) {
            if ((frog.getVx() != getSpeed()) && (!frog.intersects(log) && inWater(frog))) {
                collision = true;
            }
        }
        return collision;
    }

    @Override
    public Obstacle makeNewObs(int cw, int ch, int sp, int yPos, int init, int size) {
        BufferedImage img = null;

        try {

            img = ImageIO.read(new File(IMG_FILE));

        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        return new Log(cw, ch, sp, yPos, img, init);
    }



    @Override
    public void drawStrip(Graphics g) {
        int y = getY();
        int width = getWidth();
        g.setColor(new Color(0, 4, 117));
        g.fillRect(0, y, width, SIZE);
    }

}