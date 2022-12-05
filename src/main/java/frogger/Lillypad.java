package org.cis120.frogger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Floating lillypad obstacles:
 *
 * This class contains more methods than its superclasses and siblings because it must
 * change its appearance as the user brings frogs to the top of the screen.
 */

public class Lillypad extends Obstacle {

    private BufferedImage img;
    private boolean finished;
    public static final int SIZE = 50;

    public Lillypad(int courtWidth, int courtHeight, int speed,
                    int y, BufferedImage img, int initX) {
        super(courtWidth, courtHeight, speed, y, img, initX, SIZE, SIZE);
        this.img = img;
        finished = false;
    }

    public boolean getFinished() {
        return finished;
    }

    public void setFinishedImage() {
        finished = true;
        try {
            img = ImageIO.read(new File("files/froggypad.png"));
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }

}
