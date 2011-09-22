package edu.zju.sc.aoot.hw1;

import java.util.Random;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * JUnit test class of {@link Die}
 * 
 * @author Frogcherry ÖÜÏþÁú frogcherry@gmail.com
 * @since 1.0.0
 * @version 1.0.0
 */
public class TestDie extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test the constructor without arguments of {@link Die} and the side count
     * getter {@link Die#numOfSides()}
     * 
     * @throws Exception
     */
    @Test
    public void testConstructorNoArgs() throws Exception {
        Die die = new Die();
        assertEquals(6, die.numOfSides());
    }

    /**
     * Test the constructor with legal argument of {@link Die} and the side
     * count getter {@link Die#numOfSides()}
     * 
     * @throws Exception
     */
    @Test
    public void testConstructorWithArgs() throws Exception {
        int sideCnt = 12;
        Die die = new Die(12);
        assertEquals(sideCnt, die.numOfSides());

        sideCnt = 1200;
        die = new Die(1200);
        assertEquals(sideCnt, die.numOfSides());
    }

    /**
     * Test the constructor with illegal argument of {@link Die}
     * 
     * @throws Exception
     */
    @Test
    public void testConstructorWithIllArgs() throws Exception {
        int sideCnt = 0;
        try {
            @SuppressWarnings("unused")
            Die die = new Die(sideCnt);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        sideCnt = -12;
        try {
            @SuppressWarnings("unused")
            Die die = new Die(sideCnt);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * Test {@link Die#roll()} of the default {@link Die}.
     * 
     * @throws Exception
     */
    @Test
    public void testRollDef() throws Exception {
        Die die;
        int sideCnt = 6;
        int top;

        // test default die
        die = new Die();
        top = die.roll();
        assertTrue(0 < top && top <= sideCnt);
        top = die.roll();
        assertTrue(0 < top && top <= sideCnt);
    }

    /**
     * Test {@link Die#roll()} of the specified {@link Die}.
     * 
     * @throws Exception
     */
    @Test
    public void testRollSep() throws Exception {
        Die die;
        int sideCnt = 6;
        int top;

        // test specified die
        sideCnt = 12;
        die = new Die(sideCnt);
        top = die.roll();
        assertTrue(0 < top && top <= sideCnt);
        top = die.roll();
        assertTrue(0 < top && top <= sideCnt);
    }

    /**
     * Test {@link Die#roll()} of the random {@link Die}.
     * 
     * @throws Exception
     */
    @Test
    public void testRollRand() throws Exception {
        Die die;
        int sideCnt = 6;
        int top;

        // test random specified die
        sideCnt = new Random().nextInt(Integer.MAX_VALUE) + 1;
        die = new Die(sideCnt);
        top = die.roll();
        assertTrue(0 < top && top <= sideCnt);
        top = die.roll();
        assertTrue(0 < top && top <= sideCnt);
    }

    /**
     * Test {@link Die#topFace()} of a rolled default {@link Die}
     * 
     * @throws Exception
     */
    @Test
    public void testTopFaceDef() throws Exception {
        Die die;
        int top, topValue;

        // test default die
        die = new Die();
        top = die.roll();
        topValue = die.topFace();
        assertEquals(top, topValue);
        top = die.roll();
        topValue = die.topFace();
        assertEquals(top, topValue);
    }

    /**
     * Test {@link Die#topFace()} of a rolled specified {@link Die}
     * 
     * @throws Exception
     */
    @Test
    public void testTopFaceSep() throws Exception {
        Die die;
        int sideCnt = 16;
        int top, topValue;

        // test specified die
        die = new Die(sideCnt);
        top = die.roll();
        topValue = die.topFace();
        assertEquals(top, topValue);
        top = die.roll();
        topValue = die.topFace();
        assertEquals(top, topValue);
    }

    /**
     * Test {@link Die#topFace()} of a rolled random {@link Die}
     * 
     * @throws Exception
     */
    @Test
    public void testTopFaceRand() throws Exception {
        Die die;
        int sideCnt = new Random().nextInt(Integer.MAX_VALUE) + 1;
        int top, topValue;

        // test random die
        die = new Die(sideCnt);
        top = die.roll();
        topValue = die.topFace();
        assertEquals(top, topValue);
        top = die.roll();
        topValue = die.topFace();
        assertEquals(top, topValue);
    }
}
