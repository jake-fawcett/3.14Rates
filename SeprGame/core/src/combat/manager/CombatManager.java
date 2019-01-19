package combat.manager;

import com.badlogic.gdx.math.Interpolation;
import combat.actors.CombatActor;
import combat.actors.CombatActorTest;
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

import static other.Constants.SHIP_BOARD_PERCENTAGE;

@SuppressWarnings("FieldCanBeLocal")
public class CombatManager {
    private CombatPlayer player;
    private CombatEnemy enemy;

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

        shooter.takeTurn(weaponSelected);
        turnReport.add(new Pair<Room, Weapon>(roomSelected, weaponSelected));
        damageReport = calculateDamage(turnReport, shooter.getShip(), receiver.getShip());
        applyTurn(damageReport, receiver.getShip());
    }

    public void enemyCombatLoop(CombatEnemy shooter, CombatPlayer receiver){
        List<Pair<Room, Integer>> damageReport = new ArrayList<Pair<Room, Integer>>();
        List<Pair<Room, Weapon>> turnReport = new ArrayList<Pair<Room, Weapon>>();

        turnReport = shooter.takeTurn(receiver.getShip());
        damageReport = calculateDamage(turnReport, shooter.getShip(), receiver.getShip());
        applyTurn(damageReport, receiver.getShip());
    }

    private List<Pair<Room, Integer>> calculateDamage(List<Pair<Room, Weapon>> turnReport, Ship shipFiring,
                                                      Ship shipFiredAt) {

        List<Pair<Room, Integer>> damageReport = new ArrayList<Pair<Room, Integer>>();

        for (Pair<Room, Weapon> shot : turnReport) {
            Room target = shot.getKey();
            Weapon weapon = shot.getValue();
            int damage;

            if (pickRandom() > (weapon.getAccuracy() * shipFiring.calculateShipAccuracy())) {
                damage = 0;
                System.out.println("Miss!");
            } else if (pickRandom() <= shipFiredAt.calculateShipEvade()) {
                damage = 0;
                System.out.println("Miss!");
            } else {
                damage = weapon.getBaseDamage();
            }

            if (target.getFunction() == RoomFunction.NON_FUNCTIONAL) {
                damage = damage * 2;
            }

            damage = (int) (damage * shipFiring.getRoom(RoomFunction.GUN_DECK).getMultiplier());

            damageReport.add(new Pair<Room, Integer>(target, damage));
        }

        return damageReport;
    }

    public boolean checkFightEnd() {
        if (player.getShip().getHullHP() <= 0) {
            return true;
        } else if (enemy.getShip().getHullHP() <= 0) {
            return true;
        } else if (enemy.getShip().getHullHP() < (enemy.getShip().getBaseHullHP() * SHIP_BOARD_PERCENTAGE)) {
            //TODO Minigame option to start here. The below is just a placeholder.
            boolean playerWantsToStartMinigame = false;
            //noinspection StatementWithEmptyBody,ConstantConditions
            if (playerWantsToStartMinigame) {
                //begin minigame
                return true;
            }
        }
        return false;

    }

    private float pickRandom() {
        Random rand = new Random();
        return rand.nextFloat();
    }

    private void applyTurn(List<Pair<Room, Integer>> damageReport, Ship targeted) {
        for (Pair<Room, Integer> shot : damageReport) {
            shot.getKey().damage(shot.getValue());
            targeted.damage(shot.getValue());
        }
    }

    /**
     * Ends the fight and starts minigame.
     */
    private void endCombat() {
    }
}