package combat.actors;

import combat.items.RoomUpgrade;
import combat.items.Weapon;
import combat.ship.Room;
import combat.ship.RoomFunction;
import combat.ship.Ship;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import testing_tools.SampleObjects;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CombatActorTest {
    private CombatEnemy enemyTest;
    private CombatPlayer playerTest;

    @Before
    public void setUp() {
        enemyTest = new CombatEnemy(SampleObjects.createSampleShip());
        playerTest = new CombatPlayer(SampleObjects.createSampleShip());
    }

    private List<Pair<Room, Integer>> createSampleDamageReport(CombatActor actor) {
        List<Pair<Room, Integer>> report = new ArrayList<Pair<Room, Integer>>();
        try {
            report.add(new Pair<Room, Integer>(actor.getShip().getRoom(RoomFunction.HELM), 5));
            report.add(new Pair<Room, Integer>(actor.getShip().getRoom(RoomFunction.GUN_DECK), 10));
        } catch (IllegalArgumentException ex) {
            fail("Failed before test could be run due to error in setup. A room you tried to damage in the damage " +
                    "report does not exist.");
        }
        return report;
    }

    public void testActorTakeTurn(CombatActor actor) {
        //Initial room health
        int helmHP = actor.getShip().getRoom(RoomFunction.HELM).getHp();
        int gunHP = actor.getShip().getRoom(RoomFunction.GUN_DECK).getHp();
        int crowHP = actor.getShip().getRoom(RoomFunction.CROWS_NEST).getHp();
        int crewHP = actor.getShip().getRoom(RoomFunction.CREW_QUARTERS).getHp();

        List<Pair<Room, Weapon>> turnReport = actor.takeTurn(createSampleDamageReport(actor));
        //TODO Test that it outputs the correct thing using turnReport (above)

        // Rooms take damage
        assertEquals("Hit rooms should be damaged", helmHP - 5, actor.getShip().getRoom(
                RoomFunction.HELM).getHp());

        assertEquals("Hit rooms should be damaged", gunHP - 10, actor.getShip().getRoom(
                RoomFunction.GUN_DECK).getHp());

        assertEquals("Non-Hit rooms should not be damaged", crowHP, actor.getShip().getRoom(
                RoomFunction.CROWS_NEST).getHp());

        assertEquals("Non-Hit rooms should not be damaged", crewHP, actor.getShip().getRoom(
                RoomFunction.CREW_QUARTERS).getHp());


    }

    @Test
    @Ignore
    public void enemyTakeTurn() {
        testActorTakeTurn(enemyTest);
    }

    @Test
    @Ignore
    public void playerTakeTurn() {
        testActorTakeTurn(playerTest);
    }
}
