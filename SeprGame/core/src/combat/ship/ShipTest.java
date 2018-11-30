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

    }

    @Test
    public void repair() {
    }
}