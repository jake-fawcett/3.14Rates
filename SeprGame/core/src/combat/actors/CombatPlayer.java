package combat.actors;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static other.Constants.COOLDOWN_TICKS_PER_TURN;

public class CombatPlayer extends CombatActor {
    public CombatPlayer(Ship ship) {
        super(ship);
    }

    private Boolean firePressed = false;
    private Weapon weaponSelected;
    private Room roomSelected;
    private TextButton fireButton;

    @Override
    /**
     * Takes a damage report telling you what has been attacked, decrements cooldowns of weapons, repairs the ship
     * and fires on enemy by returning an attack report.
     */
    public List<Pair<Room, Weapon>> takeTurn(List<Pair<Room, Integer>> damageReport, Ship enemy) {
        List<Pair<Room, Weapon>> attackReport = new ArrayList<Pair<Room, Weapon>>();
        for (Weapon weapon : getShip().getWeapons()) {
            weapon.decrementCooldown(COOLDOWN_TICKS_PER_TURN);
        }

        getShip().combatRepair();

        //List<Pair<Room, Weapon>> attackReport = new ArrayList<Pair<Room, Weapon>>();
        attackReport.add(new Pair<Room, Weapon>(roomSelected, weaponSelected));
        return attackReport;

    }

    public Boolean getFirePressed(){
        return firePressed;
    }

    public void setFirePressed(Boolean pressed){
        firePressed = pressed;
    }

    public void setRoomSelected(Room room){
        roomSelected = room;
    }

    public void setWeaponSelected(Weapon weapon){
        weaponSelected = weapon;
    }

    public void setFireButton(TextButton button) {
        fireButton = button;
        fireButtonListener();
    }

    private void fireButtonListener() {
        //fireButton.addListener();
    }
}
