package uk.ac.york.sepr4.ahod2.util;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.york.sepr4.ahod2.node.Node;
import uk.ac.york.sepr4.ahod2.object.entity.Ship;

import java.util.List;

public class MixedTests {

    @Test
    public void testShipConstr() {
        Ship ship = new Ship();

        Assert.assertSame(ship.getHealth(), 4);
        Assert.assertSame(ship.getMaxHealth(), 4);
    }

    @Test
    public void testShipMana() {
        Ship ship = new Ship();

        ship.deductMana(1);
        Assert.assertSame(0, ship.getMana());
    }

    @Test
    public void testShipDamage() {
        Ship ship = new Ship();
        ship.addDamage(1, 0);
        Assert.assertSame(1, ship.getDelayedDamage().get(0));

        ship.addDamage(1, 0);
        Assert.assertSame(2, ship.getDelayedDamage().get(0));

        ship.addDamage(2, 1);
        Assert.assertSame(2, ship.getDelayedDamage().get(1));

        ship.applyDelayedDamage(null, null);
        Assert.assertSame(2, ship.getHealth());
        ship.applyDelayedDamage(null, null);
        Assert.assertSame(0, ship.getHealth());
    }

    @Test
    public void testShipHeal() {
        Ship ship = new Ship();
        ship.setHealth(1);

        ship.addHeal(1, 0);
        Assert.assertSame(1, ship.getDelayedHeal().get(0));

        ship.addHeal(1, 0);
        Assert.assertSame(2, ship.getDelayedHeal().get(0));

        ship.addHeal(2, 1);
        Assert.assertSame(2, ship.getDelayedHeal().get(1));

        ship.applyDelayedHeal(null, null);
        Assert.assertSame(3, ship.getHealth());
        ship.applyDelayedHeal(null, null);
        Assert.assertSame(4, ship.getHealth());
    }

    @Test
    public void generateNodeMapTest() {
        Integer targetDepth = 10, actualDepth = 0;
        List<Node> nodeList = NodeUtil.generateNodeMap(10);

        for(Node node: nodeList) {
            if(node.getRow()> actualDepth) {
                actualDepth = node.getRow();
            }
        }
        Assert.assertSame(targetDepth+1, actualDepth);
    }
}
