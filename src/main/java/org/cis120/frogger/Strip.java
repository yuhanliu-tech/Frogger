package org.cis120.frogger;

import javax.swing.*;
import java.util.LinkedList;
import java.awt.*;

/**
 * Abstract class for all strips, including street, river, and lillypads (winStrip)
 *
 * Includes methods to facilitate addition of obstacle objects into the linked lists of strips
 */

public abstract class Strip {

    private LinkedList<Obstacle> obs;
    private int y;
    private int width;
    private int speed;
    private boolean goLeft;
    private int initPos;


    public Strip(int y, int speed, boolean goLeft) {
        this.y = y;
        width = 500;
        obs = new LinkedList<>();
        this.goLeft = goLeft;

        if (goLeft) {
            this.speed = -1 * speed;
            initPos = 550;
        } else {
            this.speed = speed;
            initPos = -70;
        }

    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getWidth() {
        return width;
    }

    public void drawStrip(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, y, width, 60);
    }

    public void start() {
        for (int i = 0; i < obs.size(); i++) {
            Obstacle o = obs.get(i);
            o.move();
            if ((o.hitWall() == Direction.LEFT && goLeft) ||
                    (o.hitWall() == Direction.RIGHT && !goLeft)) {
                obs.remove(i);
                i--;
            }
        }
    }

    public LinkedList<Obstacle> getObs() {
        return obs;
    }

    public int[] getObsPos() {
        int[] pos = new int[obs.size()];
        for (int i = 0; i < obs.size(); i++) {
            pos[i] = obs.get(i).getPx();
        }
        return pos;
    }

    public Obstacle makeNewObs(int cw, int ch, int sp, int yPos, int init, int size) {
        Obstacle newO = new Obstacle(cw, ch, sp, yPos, null, init, size, size);
        return newO;
    }

    public void newObs(int courtWidth, int courtHeight, int delay) {

        Timer timer = new Timer(delay, e -> addNewObs(courtWidth, courtHeight));
        timer.start();

    }

    public void addNewObs(int courtWidth, int courtHeight) {
        obs.add(makeNewObs(courtWidth, courtHeight, speed, y, initPos,70));
    }

    public void forceNewObs(int cW, int cH,int[] pos) {
        for (int i = 0; i < pos.length; i++) {
            obs.add(makeNewObs(cW, cH, speed, y, pos[i], 70));
        }
    }

    public void addSpecificObs(Obstacle ob) {
        obs.add(ob);
    }

    public void drawObs(Graphics g) {
        for (Obstacle ob : obs) {
            ob.draw(g);
        }
    }

    public void floatFrog(Frog frog) {}

    public boolean checkCollision(Frog frog) {
        boolean collision = false;
        for (Obstacle ob : obs) {
            if (frog.intersects(ob)) {
                collision = true;
            }
        }
        return collision;
    }

}
