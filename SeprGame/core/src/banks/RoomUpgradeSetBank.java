package banks;

import combat.items.RoomUpgrade;

import java.util.ArrayList;
import java.util.List;

import static banks.RoomUpgradeBank.*;

//TODO Tests Scott Pls
public enum RoomUpgradeSetBank {
    COMP_SCI_UPGRADES(REF_GUN, LOOK, STITCH, null),
    LMB_UPGRADES(BED, SIGHT, ORANGES, null);

    private RoomUpgradeBank r1;
    private RoomUpgradeBank r2;
    private RoomUpgradeBank r3;
    private RoomUpgradeBank r4;

    RoomUpgradeSetBank(RoomUpgradeBank r1, RoomUpgradeBank r2, RoomUpgradeBank r3, RoomUpgradeBank r4){
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.r4 = r4;
    }

    public List<RoomUpgrade> getRoomList() {
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
    }
}