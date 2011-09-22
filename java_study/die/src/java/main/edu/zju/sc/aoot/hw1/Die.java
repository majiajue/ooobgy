package edu.zju.sc.aoot.hw1;

import java.util.Random;

/**
 * A n-sided die.
 * <p/>
 * You can create a default 6-sided die with {@link #Die()}.<br/>
 * You can also create a die of specified side count with {@link #Die(int)}.<br/>
 * Get the count of die side with {@link #numOfSides()}<br/>
 * {@link #roll()} count roll the die and return the top current.<br/>
 * {@link #topFace()} return the current top without rolling the die
 * 
 * @author Frogcherry ÖÜÏþÁú frogcherry@gmail.com
 * @since 1.0.0
 * @version 1.0.0
 */
public class Die {
    /** side count of the die */
    private final int sideCnt;
    /** current top of the die */
    private int top;
    /** a simple random factory used {@link Random} */
    private Random randFatory = new Random();

    /**
     * Constructor without arguments default appoints the die with 6 sides.
     */
    public Die() {
        this.sideCnt = 6;
        roll();
    }

    /**
     * This Constructor create a die with specified side count.
     * 
     * @param sideCnt
     */
    public Die(int sideCnt) {
        if (sideCnt < 1) {
            throw new IllegalArgumentException();
        }
        this.sideCnt = sideCnt;
        roll();
    }

    /**
     * Get the number of side of this die.
     * 
     * @return {@link #sideCnt} (side count of the die)
     */
    public int numOfSides() {
        return sideCnt;
    }

    /**
     * Roll the die and return the current top.
     * 
     * @return {@link #top} (the current top)
     */
    public int roll() {
        this.top = randFatory.nextInt(sideCnt) + 1;

        return this.top;
    }

    /**
     * Return the current top without rolling the die.
     * 
     * @return {@link #top} (the current top)
     */
    public int topFace() {
        return this.top;
    }
}
