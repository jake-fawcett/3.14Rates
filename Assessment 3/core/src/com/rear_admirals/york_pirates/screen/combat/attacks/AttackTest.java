package com.rear_admirals.york_pirates.screen.combat.attacks;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AttackTest {
    private Attack tester = new Attack();

    @Before

    @Test
    public void constructor() {
        assertEquals(tester.name, "Broadside");
    }

    @Test
    public void setSkipMoveStatus() {
        boolean before = tester.skipMoveStatus;
        tester.setSkipMoveStatus(!before);
        assertNotEquals(before, tester.skipMoveStatus);
    }

    @Test
    public void addAccuracy() {
        int before = tester.accPercent;
        tester.addAccuracy(2);
        assertEquals(before + 2, tester.accPercent);
    }
}