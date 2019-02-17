//Added For Assessment 3
package com.rear_admirals.york_pirates.screen.combat.attacks;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for attack
 */
public class AttackTest {
    private Attack tester = new Attack();

    /**
     * Tests that the constructor instantiates an object
     */
    @Test
    public void constructor() {
        assertEquals(tester.name, "Broadside");
    }

    /**
     * Tests that you can set the move to be skipped
     */
    @Test
    public void setSkipMoveStatus() {
        boolean before = tester.skipMoveStatus;
        tester.setSkipMoveStatus(!before);
        assertNotEquals(before, tester.skipMoveStatus);
    }

    /**
     * Tests that accuracy can be increased
     */
    @Test
    public void addAccuracy() {
        int before = tester.accPercent;
        tester.addAccuracy(2);
        assertEquals(before + 2, tester.accPercent);
    }
}
//End Added