package location;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import other.Resource;

import java.util.List;
import java.util.Map;

public class Department {
//    We may find that we want to move this out to its own class eventually.
    public enum resources {
        CREW, FOOD
    }

    private List<Weapon> weaponStock;
    private List<RoomUpgrade> upgradeStock;
    /**
     * Map storing resources and their cost per unit.
     */
    private Map<Resource, Integer> resourceStock;

    public Department(List<Weapon> weaponStock, List<RoomUpgrade> upgradeStock, Map<Resource, Integer> resourceStock) {
        this.weaponStock = weaponStock;
        this.upgradeStock = upgradeStock;
        this.resourceStock = resourceStock;
    }

    public List<Weapon> getWeaponStock() {
        return weaponStock;
    }

    public List<RoomUpgrade> getUpgradeStock() {
        return upgradeStock;
    }

    public Map<Resource, Integer> getResourceStock() {
        return resourceStock;
    }

    public void buyWeapon(Integer index) {
    }

    public void buyRoomUpgrade(Integer index){
    }

    public void buyResource(Resource resource, int quantity) {
    }
}
