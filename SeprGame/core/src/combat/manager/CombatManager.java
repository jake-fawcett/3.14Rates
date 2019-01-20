package combat.manager;

import com.badlogic.gdx.math.Interpolation;
import combat.actors.CombatActor;
import combat.actors.CombatEnemy;
import combat.actors.CombatPlayer;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static other.Constants.NON_FUNCTIONAL_ROOM_MULTIPLIER;
import static other.Constants.SHIP_BOARD_PERCENTAGE;

@SuppressWarnings("FieldCanBeLocal")
/**
 * The class that controls the flow of combat and decides if shots hit or not.
 */
public class CombatManager {
    private CombatPlayer player;
    private CombatEnemy enemy;
    private Boolean shotHit = true;

    public CombatManager(CombatPlayer player, CombatEnemy enemy) {
        this.player = player;
        this.enemy = enemy;
    }

    /**
     * Controls the whole process of combat from start to entering minigame.
     */
    public void combatLoop(CombatPlayer shooter, CombatEnemy receiver, Room roomSelected, Weapon weaponSelected) {
        List<Pair<Room, Integer>> damageReport = new ArrayList<Pair<Room, Integer>>();
        List<Pair<Room, Weapon>> turnReport = new ArrayList<Pair<Room, Weapon>>();

//        Get targets and weapons
        shooter.takeTurn(weaponSelected);
        turnReport.add(new Pair<Room, Weapon>(roomSelected, weaponSelected));
//        Figure out what hit or missed
        damageReport = calculateDamage(turnReport, shooter.getShip(), receiver.getShip());
//        Damage the target
        applyTurn(damageReport, receiver.getShip());
    }

    public void enemyCombatLoop(CombatEnemy shooter, CombatPlayer receiver){
        List<Pair<Room, Integer>> damageReport = new ArrayList<Pair<Room, Integer>>();
        List<Pair<Room, Weapon>> turnReport = new ArrayList<Pair<Room, Weapon>>();

        //        Get targets and weapons
        turnReport = shooter.takeTurn(receiver.getShip());
        //        Figure out what hit or missed
        damageReport = calculateDamage(turnReport, shooter.getShip(), receiver.getShip());
        //        Damage the target
        applyTurn(damageReport, receiver.getShip());
    }

    /**
     * Decides which shots hit and which missed. Also decides how much damage was done by a hit.
     *
     * @param turnReport  The list of weapons fired and their targets from the shooter.
     * @param shipFiring  The ship who is firing.
     * @param shipFiredAt The ship who is targeted.
     * @return A list of pairs of rooms and integers, denoting the rooms hit and how much damage was done ot them.
     */
    private List<Pair<Room, Integer>> calculateDamage(List<Pair<Room, Weapon>> turnReport, Ship shipFiring,
                                                      Ship shipFiredAt) {

        List<Pair<Room, Integer>> damageReport = new ArrayList<Pair<Room, Integer>>();

//        For each weapon fired
        for (Pair<Room, Weapon> shot : turnReport) {
            Room target = shot.getKey();
            Weapon weapon = shot.getValue();
            int damage;

//            Roll to see if the shot was not on target
            if (pickRandom() > (weapon.getAccuracy() * shipFiring.calculateShipAccuracy())) {
                damage = 0;
                shotHit = false;
                //            Roll to see if the shot was dodged
            } else if (pickRandom() <= shipFiredAt.calculateShipEvade()) {
                damage = 0;
                shotHit = false;
                //            The shot hit, get damage
            } else {
                damage = weapon.getBaseDamage();
                shotHit = true;
            }

//            Hitting non-functional rooms doubles damage
            if (target.getFunction() == RoomFunction.NON_FUNCTIONAL) {
                damage = (int)(damage * NON_FUNCTIONAL_ROOM_MULTIPLIER);
            }

//            Apply a modifier to the damage of the shot depending on the GUN_DECKs health and upgrades
            damage = (int) (damage * shipFiring.getRoom(RoomFunction.GUN_DECK).getMultiplier());

            damageReport.add(new Pair<Room, Integer>(target, damage));
        }

        return damageReport;
    }

    /**
     * Find out if the fight is finished either by a ship being destroyed or the minigame being started.
     *
     * @return Boolean - Fight ended or fight still going.
     */
    public boolean checkFightEnd() {
        if (player.getShip().getHullHP() <= 0) {
            return true;

        } else if (enemy.getShip().getHullHP() <= 0) {
            return true;

        } else if (enemy.getShip().getHullHP() < (enemy.getShip().getBaseHullHP() * SHIP_BOARD_PERCENTAGE)) {
            //TODO Minigame option to start here. The below is just a placeholder.
            return false;
        }
        return false;
    }

    public Boolean getShotHit(){
        return shotHit;
    }

    /**
     * @return A random value between 0 and 1
     */
    private float pickRandom() {
        Random rand = new Random();
        return rand.nextFloat();
    }

    /**
     * Applies damage to the ship and the rooms hit.
     *
     * @param damageReport The list of rooms and the damage they took
     * @param targeted     The ship that will be receiving the damage
     */
    private void applyTurn(List<Pair<Room, Integer>> damageReport, Ship targeted) {
        for (Pair<Room, Integer> shot : damageReport) {
            shot.getKey().damage(shot.getValue());
            targeted.damage(shot.getValue());
        }
    }
}