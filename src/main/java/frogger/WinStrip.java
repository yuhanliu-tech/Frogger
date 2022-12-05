package org.cis120.frogger;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.*;

/**
 * Lillypad strip
 */

public class WinStrip extends River {

    private static final String IMG_FILE = "files/Lillypad.png";
    private static final String IMG_FILE2 = "files/froggypad.png";

    private String bin;
    private LinkedList<Lillypad> pads = new LinkedList<Lillypad>();
    private int finishedFrogs;

    public WinStrip(int y, int speed, boolean goLeft) {

        super(y,speed, goLeft);
        finishedFrogs = 0;
        bin = "000";

    }

    public void checkFinishedFrog(Frog frog) {
        for (Lillypad pad : pads) {
            if (pad.intersects(frog)) {
                pad.setFinishedImage();
                finishedFrogs++;
            }
        }
    }

    public String convertToBin() {
        bin = "";

        for (Lillypad pad : pads) {
            if (pad.getFinished()) {
                bin += "1";
            } else {
                bin += "0";
            }
        }

        return bin;
    }

    public int getFinishedFrogs() {
        return finishedFrogs;
    }

    public void setFinishedFrogs(int f) {
        finishedFrogs = f;
    }

    @Override
    public boolean checkCollision(Frog frog) {
        boolean collision = false;
        for (Lillypad pad : pads) {
            if (!pad.intersects(frog) && inWater(frog)) {
                collision = true;
            }
        }
        return collision;
    }

    @Override
    public void drawObs(Graphics g) {
        for (Lillypad pad : pads) {
            pad.draw(g);
        }
    }

    public void setBin(String b) {
        bin = b;
    }

    @Override
    public void addNewObs(int courtWidth, int courtHeight) {

        BufferedImage img1 = null;
        BufferedImage img2 = null;

        try {
            img1 = ImageIO.read(new File(IMG_FILE));
            img2 = ImageIO.read(new File(IMG_FILE2));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        Lillypad lilly1;
        Lillypad lilly2;
        Lillypad lilly3;

        if (bin.charAt(0) == '0') {
            lilly1 = new Lillypad(courtWidth, courtHeight, 0, 0, img1, 120);
        } else {
            lilly1 = new Lillypad(courtWidth, courtHeight, 0, 0, img2, 120);
        }

        if (bin.charAt(1) == '0') {
            lilly2 = new Lillypad(courtWidth, courtHeight, 0, 0, img1, 220);
        } else {
            lilly2 = new Lillypad(courtWidth, courtHeight, 0, 0, img2, 220);
        }

        if (bin.charAt(2) == '0') {
            lilly3 = new Lillypad(courtWidth, courtHeight, 0, 0, img1, 320);
        } else {
            lilly3 = new Lillypad(courtWidth, courtHeight, 0, 0, img2, 320);
        }

        pads.add(lilly1);
        pads.add(lilly2);
        pads.add(lilly3);
    }

}
