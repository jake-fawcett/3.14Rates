package combat.items;

import combat.ship.Room;
import combat.ship.RoomFunction;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoomUpgradeTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructorThrowsIllegalArgument() {
        RoomUpgrade tester = new RoomUpgrade("test upgrade", 100, 1.0,
                RoomFunction.NON_FUNCTIONAL);
    }

    @Test
    public void constructorCorrectlyAppliesFunction() {
        RoomUpgrade tester = new RoomUpgrade("test upgrade", 100, 1.0,
                RoomFunction.GUN_DECK);
        assertEquals("Valid rooms should be allowed in constructor. (As opposed to exception thrown)",
                RoomFunction.GUN_DECK, tester.getAffectsRoom());
    }
}
