//Added For Assessment 3
package com.rear_admirals.york_pirates;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollegeTest {
    private College tester;

    @Before
    /**
     * Set up college
     */
    public void setUp() throws Exception {
        tester = College.Derwent;
    }

    @Test
    /**
     * Test that you can add allies to colleges after they are instantiated
     */
    public void addAlly() {
        College adding = new College("Test");
        tester.addAlly(adding);
        assertTrue(tester.getAlly().contains(adding));
    }

    @Test
    /**
     * Test that you can check if the boss of that college is dead
     */
    public void isBossDead() {
        boolean before = tester.isBossDead();
        tester.setBossDead(!before);
        assertNotEquals(before, tester.isBossDead());
    }
}
//End Added