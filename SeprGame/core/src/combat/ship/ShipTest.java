package combat.ship;

import org.junit.Before;
import org.junit.Test;
import testing_tools.SampleObjects;

import static org.junit.Assert.*;

public class ShipTest {
    private Ship tester;

    @Before
    public void setUp() {
        tester = SampleObjects.createSampleShip();
    }

    @Test
    public void damage() {
        tester.damage(50);
        assertEquals("HP should be reduced by 50", tester.getBaseHullHP() - 50,
                tester.getHullHP());
        tester.damage(tester.getBaseHullHP());
        assertEquals("HP should not be allowed to go below 0", 0, tester.getHullHP());
    }

    @Test
    public void repair() {
        tester.damage(tester.getBaseHullHP() - 1);
        assertEquals("HP should be 1 before these tests run", 1, tester.getHullHP());
        tester.repair(50);
        assertEquals("HP should be raised to 51", 51, tester.getHullHP());
        tester.repair(tester.getBaseHullHP());
        assertEquals("HP should not be allowed to go above max", tester.getBaseHullHP(),
                tester.getHullHP());
    }
}