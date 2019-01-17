package location;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Ship;
import game_manager.GameManager;
import other.Constants;
import other.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static other.Constants.*;

public class Department {

    private List<Weapon> weaponStock;
    private List<RoomUpgrade> upgradeStock;
    private Map<Resource, Integer> resourceStock;
    private GameManager gameManager;

    public Department(List<Weapon> weaponStock, List<RoomUpgrade> upgradeStock, GameManager gameManager) {
        this.weaponStock = weaponStock;
        this.upgradeStock = upgradeStock;
        this.gameManager = gameManager;
        this.resourceStock = new HashMap<Resource, Integer>();
        resourceStock.put(Resource.REPAIR, REPAIR_COST);
        resourceStock.put(Resource.CREW, CREW_COST);
        resourceStock.put(Resource.FOOD, FOOD_COST);
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

    public void buyWeapon(Weapon weapon) {
        if (gameManager.getGold() < weapon.getCost()) {
            throw new IllegalArgumentException("Not enough gold");
        } else if (!weaponStock.contains(weapon)) {
            throw new IllegalArgumentException("Weapon does not exist");
        } else {
            gameManager.getPlayerShip().addWeapon(weapon);
            weaponStock.remove(weapon);
            gameManager.deductGold(weapon.getCost());
        }
    }

    public void buyRoomUpgrade(RoomUpgrade upgrade) {
        if (gameManager.getGold() < upgrade.getCost()) {
            throw new IllegalArgumentException("Not enough gold");
        } else if (!upgradeStock.contains(upgrade)) {
            throw new IllegalArgumentException("Upgrade does not exist");
        } else {
            gameManager.getPlayerShip().addUpgrade(upgrade);
            upgradeStock.remove(upgrade);
            gameManager.deductGold(upgrade.getCost());
        }
    }

    public void buyResource(Resource resource, int amount) {
        int price;
        if (!resourceStock.keySet().contains(resource)) {
            throw new IllegalArgumentException("Resource not valid");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Not allowed");
        } else {
            price = resourceStock.get(resource);
        }
        if (resource == Resource.FOOD) {
            if (price * amount > gameManager.getGold()) {
                throw new IllegalStateException("Not enough gold");
            } else {
                gameManager.addFood(amount);
                gameManager.deductGold(price * amount);
            }
        }
        if (resource == Resource.CREW) {
            if (price * amount > gameManager.getGold()) {
                throw new IllegalStateException("Not enough gold");
            } else {
                gameManager.getPlayerShip().addCrew(amount);
                gameManager.deductGold(price * amount);
            }
        }
        if (resource == Resource.REPAIR) {
            if (price * amount > gameManager.getGold()) {
                throw new IllegalStateException("Not enough gold");
            } else {
                gameManager.getPlayerShip().repair(amount);
                gameManager.deductGold(price * amount);
            }
        }
    }

    public void sellWeapon(Weapon weapon) {
        if (!gameManager.getPlayerShip().getWeapons().contains(weapon)) {
            throw new IllegalArgumentException("You do not own this weapon");
        } else {
            gameManager.getPlayerShip().getWeapons().remove(weapon);
            weaponStock.add(weapon);
            gameManager.addGold((int) (weapon.getCost() * Constants.STORE_SELL_PRICE_MULTIPLIER));
        }

    }

    public void sellUpgrade(RoomUpgrade upgrade) {
        if (!gameManager.getPlayerShip().hasUpgrade(upgrade)) {
            throw new IllegalArgumentException("You do not own this upgrade");
        } else {
            gameManager.getPlayerShip().delUpgrade(upgrade);
            upgradeStock.add(upgrade);
            gameManager.addGold((int) (upgrade.getCost() * Constants.STORE_SELL_PRICE_MULTIPLIER));
        }
    }

}
