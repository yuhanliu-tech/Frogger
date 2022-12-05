package org.cis120.frogger;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Street extends Strip {


    private static final String IMG_FILE1 = "files/Car1.png";
    private static final String IMG_FILE2 = "files/Car2.png";
    private static final String IMG_FILE3 = "files/Car3.png";
    private static final String IMG_FILE4 = "files/Car4.png";
    private static final String IMG_FILE5 = "files/Truck.png";

    /**
     *
     * @param y
     * @param speed
     * @param goLeft
     *
     * street strip objects
     */

    public Street(int y, int speed, boolean goLeft) {

        super(y,speed, goLeft);

    }

    @Override
    public Obstacle makeNewObs(int cw, int ch, int sp, int yPos, int init, int size) {

        BufferedImage img = null;

        try {
            if (yPos == 300) {
                img = ImageIO.read(new File(IMG_FILE5));
            } else if (yPos == 350) {
                img = ImageIO.read(new File(IMG_FILE4));
            } else if (yPos == 400) {
                img = ImageIO.read(new File(IMG_FILE2));
            } else if (yPos == 450) {
                img = ImageIO.read(new File(IMG_FILE1));
            } else {
                img = ImageIO.read(new File(IMG_FILE3));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        Obstacle newC = new Car(cw, ch, sp, yPos, img, init);
        return newC;
    }

}
