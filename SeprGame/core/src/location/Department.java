package location;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Ship;
import game_manager.GameManager;
import other.Resource;

import java.util.List;
import java.util.Map;

public class Department {

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

    public void buyWeapon(GameManager gmBuyingFor, Integer index) {
    }

    public void buyRoomUpgrade(GameManager gmBuyingFor, Integer index){
    }

    public void buyResource(GameManager gmBuyingFor, Resource resource, int quantity) {
    }
}
