package banks;

import combat.items.RoomUpgrade;

import java.util.ArrayList;
import java.util.List;

import static banks.RoomUpgradeBank.*;

/**
 * A bank of possible sets of room upgrades to be used in rooms or departments.
 */
public enum RoomUpgradeSetBank {
    COMP_SCI_UPGRADES(REF_GUN, LOOK, STITCH, null),
    //NEW_ROOM_UPGRADE_SET_TEMPLATE(up1, up2, up3, up4)
    LMB_UPGRADES(BED, SIGHT, ORANGES, null);

    // Internal workings of the enum
    private RoomUpgradeBank r1;
    private RoomUpgradeBank r2;
    private RoomUpgradeBank r3;
    private RoomUpgradeBank r4;

    // Internal workings of the enum
    RoomUpgradeSetBank(RoomUpgradeBank r1, RoomUpgradeBank r2, RoomUpgradeBank r3, RoomUpgradeBank r4) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
    }

    /**
     * @return An instance of a list of room upgrades;
     */
    public List<RoomUpgrade> getRoomUpgradeList() {
        List<RoomUpgrade> out = new ArrayList();
        RoomUpgradeBank[] roomUpgrades = {r1, r2, r3, r4};

        for (RoomUpgradeBank r : roomUpgrades) {
            if (r != null) {
                RoomUpgrade copying = r.getRoomUpgrade();
                out.add(r.getRoomUpgrade());
            } else {
                out.add(null);
            }
        }

        return out;
    }
}