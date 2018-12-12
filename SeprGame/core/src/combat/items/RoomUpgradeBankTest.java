package combat.items;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomUpgradeBankTest {

    @Before
    public void testNotEmpty(){
        assertNotEquals("These tests will not work with an empty RoomUpgradeBank",
                RoomUpgradeBank.values().length == 0);
    }

    @Test
    public void getRoomUpgradeGetsRoomUpgrade(){
        RoomUpgrade myRoomUpgrade = RoomUpgradeBank.A.getRoomUpgrade();
        assertEquals("You should be able to get a RoomUpgrade from the RoomUpgradeBank", RoomUpgrade.class,
                myRoomUpgrade.getClass());
    }
}