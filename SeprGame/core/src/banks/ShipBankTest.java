package banks;

import combat.ship.Ship;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ShipBankTest {

    @Before
    public void testNotEmpty(){
        assertNotEquals("These tests will not work with an empty ShipBank",
                ShipBank.values().length == 0);
    }

    @Test
    public void getShipGetsShip(){
        Ship myShip = ShipBank.STARTER_SHIP.getShip();
        assertEquals("You should be able to get a ship from the ShipBank", Ship.class,
                myShip.getClass());
    }

    @Test
    public void getShipCreatesNewInstances() {
        Ship s1 = ShipBank.STARTER_SHIP.getShip();
        Ship s2 = ShipBank.STARTER_SHIP.getShip();
        s1.damage(1);
        assertNotEquals("Ships created from the same id should still be independent.", s1.getHullHP(),
                s2.getHullHP());
    }
}