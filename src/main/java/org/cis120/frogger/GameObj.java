package org.cis120.frogger;

import java.awt.*;

public abstract class GameObj {

/**
 * An object in the game.
 *
 * Game objects exist in the game court. They have a position, velocity, size
 * and bounds. Their velocity controls how they move; their position should
 * always be within their bounds.
 */
    /*
     * Current position of the object (in terms of graphics coordinates)
     *
     * Coordinates are given by the upper-left hand corner of the object. This
     * position should always be within bounds:
     * 0 <= px <= maxX 0 <= py <= maxY
     */
    private int px;
    private int py;

    /* Size of object, in pixels. */
    private final int width;
    private final int height;

    /* Velocity: number of pixels to move every time move() is called. */
    private int vx;
    private int vy;

    /*
     * Upper bounds of the area in which the object can be positioned. Maximum
     * permissible x, y positions for the upper-left hand corner of the object.
     */
    private final int maxX;
    private final int maxY;

    /**
     * Constructor
     */
    public GameObj(
            int vx, int vy, int px, int py, int width, int height, int courtWidth,
            int courtHeight
    ) {
        this.vx = vx;
        this.vy = vy;
        this.px = px;
        this.py = py;
        this.width = width;
        this.height = height;

        // take the width and height into account when setting the bounds for
        // the upper left corner of the object.
        this.maxX = courtWidth - width;
        this.maxY = courtHeight - height;
    }

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public int getPx() {
        return this.px;
    }

    public int getPy() {
        return this.py;
    }

    public int getVx() {
        return this.vx;
    }

    public int getVy() {
        return this.vy;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    // **************************************************************************
    // * UPDATES AND OTHER METHODS
    // **************************************************************************

    /**
     * Moves the object by its velocity. Ensures that the object does not go
     * outside its bounds by clipping.
     */
    public void move() {
        this.px += this.vx;
        this.py += this.vy;

    }

    /**
     * Determine whether this game object is currently intersecting another
     * object.
     *
     * Intersection is determined by comparing bounding boxes. If the bounding
     * boxes overlap, then an intersection is considered to occur.
     *
     * @param that The other object
     * @return Whether this object intersects the other object.
     */
    public boolean intersects(org.cis120.frogger.GameObj that) {
        return ((this.px + this.width) >= that.px
                && 0.94 * (this.py + this.height) >= that.py
                && (that.px + that.width) >= this.px
                && 0.94 * (that.py + that.height) >= this.py);
    }

    /**
     * Determine whether this game object will intersect another in the next
     * time step, assuming that both objects continue with their current
     * velocity.
     *
     * Intersection is determined by comparing bounding boxes. If the bounding
     * boxes (for the next time step) overlap, then an intersection is
     * considered to occur.
     *
     * @param that The other object
     * @return Whether an intersection will occur.
     */
    public boolean willIntersect(org.cis120.frogger.GameObj that) {
        int thisNextX = this.px + this.vx;
        int thisNextY = this.py + this.vy;
        int thatNextX = that.px + that.vx;
        int thatNextY = that.py + that.vy;

        return (thisNextX + this.width >= thatNextX
                && thisNextY + this.height >= thatNextY
                && thatNextX + that.width >= thisNextX
                && thatNextY + that.height >= thisNextY);
    }

    /**
     * Update the velocity of the object in response to hitting an obstacle in
     * the given direction. If the direction is null, this method has no effect
     * on the object.
     *
     * @param d The direction in which this object hit an obstacle
     */
    public void bounce(Direction d) {
        if (d == null) {
            return;
        }

        switch (d) {
            case UP:
                this.vy = Math.abs(this.vy);
                break;
            case DOWN:
                this.vy = -Math.abs(this.vy);
                break;
            case LEFT:
                this.vx = Math.abs(this.vx);
                break;
            case RIGHT:
                this.vx = -Math.abs(this.vx);
                break;
            default:
                break;
        }
    }

    /**
     * Determine whether the game object will hit a wall in the next time step.
     * If so, return the direction of the wall in relation to this game object.
     *
     * @return Direction of impending wall, null if all clear.
     */
    public Direction hitWall() {
        if (this.px + this.vx < -150) {
            return Direction.LEFT;
        } else if (this.px + this.vx > 600) {
            return Direction.RIGHT;
        }
        if (this.py + this.vy > this.maxY) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }

    /**
     * Determine whether the game object will hit another object in the next
     * time step. If so, return the direction of the other object in relation to
     * this game object.
     *
     * @param that The other object
     * @return Direction of impending object, null if all clear.
     */
    public Direction hitObj(org.cis120.frogger.GameObj that) {
        if (this.willIntersect(that)) {
            double halfThisWidth = (double) this.width / 2;
            double halfThatWidth = (double) that.width / 2;
            double halfThisHeight = (double) this.height / 2;
            double halfThatHeight = (double) that.height / 2;
            double dx = that.px + halfThatWidth - (this.px + halfThisWidth);
            double dy = that.py + halfThatHeight - (this.py + halfThisHeight);

            double theta = Math.acos(dx / (Math.sqrt(dx * dx + dy * dy)));
            double diagTheta = Math.atan2(halfThisWidth, halfThisWidth);

            if (theta <= diagTheta) {
                return Direction.RIGHT;
            } else if (theta <= Math.PI - diagTheta) {
                // Coordinate system for GUIs is switched
                if (dy > 0) {
                    return Direction.DOWN;
                } else {
                    return Direction.UP;
                }
            } else {
                return Direction.LEFT;
            }
        } else {
            return null;
        }
    }

    /**
     * Default draw method that provides how the object should be drawn in the
     * GUI. This method does not draw anything. Subclass should override this
     * method based on how their object should appear.
     *
     * @param g The <code>Graphics</code> context used for drawing the object.
     *          Remember graphics contexts that we used in OCaml, it gives the
     *          context in which the object should be drawn (a canvas, a frame,
     *          etc.)
     */
    public abstract void draw(Graphics g);
}