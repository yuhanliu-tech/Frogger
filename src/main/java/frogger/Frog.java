package org.cis120.frogger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Frog sprite
 *
 * Contains methods to check frog's life and death status.
 */

public class Frog extends GameObj {

    public static final int SIZE = 50;
    public static final int INIT_POS_X = 220;
    public static final int INIT_POS_Y = 550;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private static BufferedImage img;
    private static final String IMG_FILE_UP = "files/Up_frog.png";
    private static final String IMG_FILE_DOWN = "files/Down_frog.png";
    private static final String IMG_FILE_LEFT = "files/Left_frog.png";
    private static final String IMG_FILE_RIGHT = "files/Right_frog.png";
    private static final String IMG_FILE_DEAD = "files/death.png";

    private int lives;
    private boolean isFrozen;

    public Frog(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        lives = 3;
        isFrozen = false;

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE_UP));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public boolean outtaBounds() {
        return (getPx() < -20 || getPx() > 520);
    }

    public void hop(int x, int y) {
        int currY = getPy();
        int currX = getPx();
        setPx(currX + x);
        setPy(currY + y);
    }

    public void revive() {
        isFrozen = false;
        setPx(INIT_POS_X);
        setPy(INIT_POS_Y);
        setImage("up");
    }

    public void die() {
        lives--;
        isFrozen = true;
        try {
            img = ImageIO.read(new File(IMG_FILE_DEAD));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    public int getLives() {
        return lives;
    }


    public void setImage(String code) {
        try {
            if (code.equals("left")) {
                img = ImageIO.read(new File(IMG_FILE_LEFT));
            } else if (code.equals("right")) {
                img = ImageIO.read(new File(IMG_FILE_RIGHT));
            } else if (code.equals("down")) {
                img = ImageIO.read(new File(IMG_FILE_DOWN));
            } else {
                img = ImageIO.read(new File(IMG_FILE_UP));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }


}
