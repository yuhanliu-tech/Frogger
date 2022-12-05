package org.cis120.frogger;

import org.junit.jupiter.api.*;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.StringReader;


/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 600;


    @Test
    public void testFrogHitsCar() {

        Frog f = new Frog(COURT_WIDTH,COURT_HEIGHT);
        Strip street = new Street(250, 2, true);

        Obstacle car = new Car(COURT_WIDTH, COURT_HEIGHT, 2, 250, null, 250);
        street.addSpecificObs(car);

        f.setPx(250);
        f.setPy(250);

        assertTrue(street.checkCollision(f));
    }

    @Test
    public void testFrogHitsWater() {

        Frog f = new Frog(COURT_WIDTH,COURT_HEIGHT);
        River river = new River(250, 2, true);

        Obstacle log = new Log(COURT_WIDTH, COURT_HEIGHT, 2, 250, null, 0);
        river.addSpecificObs(log);

        river.start();
        river.floatFrog(f);

        f.setPx(250);
        f.setPy(250);

        assertTrue(river.checkCollision(f));

    }

    @Test
    public void testFrogMovesWithLog() {

        int speed = 2;
        Frog f = new Frog(COURT_WIDTH,COURT_HEIGHT);
        River river = new River(250, speed, false);

        Obstacle log = new Log(COURT_WIDTH, COURT_HEIGHT, speed, 250, null, 250);
        river.addSpecificObs(log);

        f.setPx(250);
        f.setPy(250);

        river.start();
        river.floatFrog(f);

        assertTrue(river.inWater(f));
        assertTrue(f.intersects(log));
        assertFalse(river.checkCollision(f));
        assertEquals(speed, f.getVx());

    }

    @Test
    public void testFrogMovesOutOfBounds() {

        Frog f = new Frog(COURT_WIDTH,COURT_HEIGHT);
        f.hop(0,-1);
        assertEquals(549, f.getPy());
        assertFalse(f.outtaBounds());
        f.hop(-300,0);
        assertTrue(f.outtaBounds());
        f.hop(800,0);
        assertTrue(f.outtaBounds());
    }

    @Test
    public void testFrogLosesLives() {

        final JLabel status = new JLabel("Testing...");
        GameCourt game = new GameCourt(status);

        Frog f = new Frog(COURT_WIDTH,COURT_HEIGHT);

        game.reset(true);
        f.die();

        assertEquals(2,f.getLives());
        assertTrue(f.getIsFrozen());

    }

    @Test
    public void testGameCourt() {

        final JLabel status = new JLabel("Testing...");
        GameCourt game = new GameCourt(status);

        game.reset(true);

        game.tick();

        assertTrue(game.getPlaying());

        assertFalse(game.getWon());

    }

    @Test
    public void testFrogWins() {

        final JLabel status = new JLabel("Testing...");
        GameCourt game = new GameCourt(status);
        Frog f = new Frog(COURT_WIDTH,COURT_HEIGHT);

        game.reset(true);

        WinStrip lillypads = new WinStrip(0, 0, true);
        lillypads.addNewObs(COURT_WIDTH, COURT_HEIGHT);

        lillypads.checkFinishedFrog(f);

        assertEquals(0,lillypads.getFinishedFrogs());

        f.hop(0,-520);
        lillypads.checkFinishedFrog(f);

        assertEquals(1,lillypads.getFinishedFrogs());

        f.hop(100,0);
        lillypads.checkFinishedFrog(f);

        assertEquals(2,lillypads.getFinishedFrogs());

        f.hop(-200,0);
        lillypads.checkFinishedFrog(f);

        assertEquals(3,lillypads.getFinishedFrogs());
    }

    @Test
    public void testFileLineIteratorSingleChars() {
        String hi = "h\n" + "i\n" + "hi";
        StringReader sr = new StringReader(hi);
        BufferedReader br = new BufferedReader(sr);
        FileLineIterator li = new FileLineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("h", li.next());
        assertTrue(li.hasNext());
        assertEquals("i", li.next());
        assertTrue(li.hasNext());
        assertEquals("hi", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testNullReader() {
        BufferedReader br = null;

        assertThrows(IllegalArgumentException.class, () -> {
            FileLineIterator li = new FileLineIterator(br);
        });
    }

}
