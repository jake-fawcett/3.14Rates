package game_manager;

import org.junit.Before;
import org.junit.Test;
import other.Constants;
import other.Difficulty;

import static org.junit.Assert.*;

public class GameManagerTest {

    private GameManager tester;

    @Before
    public void setUp() {
        tester = new GameManager("Test", Difficulty.EASY);
    }

    @Test
    public void addGold() {
        int goldBefore = tester.getGold();
        tester.addGold(100);
        assertEquals("Gold should be added to the players gold value", goldBefore + 100,
                tester.getGold());
    }

    @Test
    public void deductGold() {
        tester.deductGold(Constants.STARTING_GOLD - 5);
        assertEquals("Gold should be removed from the players gold value", 5, tester.getGold());

        tester.deductGold(Constants.STARTING_GOLD + 10);
        assertEquals("Gold should not be allowed to go below 0", 0, tester.getGold());
    }

    @Test
    public void addFood() {
        tester.addFood(50);
        assertEquals("Food should be added to the players food value", Constants.STARTING_FOOD + 50,
                tester.getFood());
    }

    @Test
    public void deductFood() {
        tester.deductFood(Constants.STARTING_FOOD - 5);
        assertEquals("Food should be removed from the players food value", 5, tester.getFood());

        tester.deductFood(Constants.STARTING_FOOD + 10);
        assertEquals("Food should not be allowed to go below 0", 0, tester.getFood());
    }

    @Test
    public void addPoints() {
        int pointsBefore = tester.getPoints();
        tester.addPoints(100);
        assertEquals("Points should be added to the players points value", pointsBefore + 100,
                tester.getPoints());
    }
}