package banks;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RoomUpgradeSetBankTest {

    @Before
    public void testNotEmpty() {
        assertFalse("These tests will not work with an empty RoomUpgradeSetBank",
                RoomUpgradeSetBank.values().length == 0);
    }

    @Test
    public void getRoomUpgradeListGetsRoomUpgradeList() {
        List<RoomUpgrade> upgradeList = RoomUpgradeSetBank.COMP_SCI_UPGRADES.getRoomUpgradeList();
        assertEquals("You should be returned a list", java.util.ArrayList.class, upgradeList.getClass());
        assertFalse("The list should not be empty", upgradeList.size() == 0);
        assertEquals("You should be returned a list of upgrades", RoomUpgrade.class,
                upgradeList.get(0).getClass());
    }
}