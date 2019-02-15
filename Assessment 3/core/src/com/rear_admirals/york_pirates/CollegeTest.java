//Added For Assessment 3
package com.rear_admirals.york_pirates;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollegeTest {
    private College tester;

    @Before
    public void setUp() throws Exception {
        tester = College.Derwent;
    }

    @Test
    public void addAlly() {
        College adding = new College("Test");
        tester.addAlly(adding);
        assertTrue(tester.getAlly().contains(adding));
    }

    @Test
    public void isBossDead() {
        boolean before = tester.isBossDead();
        tester.setBossDead(!before);
        assertNotEquals(before, tester.isBossDead());
    }
}
//End Added