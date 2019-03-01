package uk.ac.york.sepr4;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.york.sepr4.utils.AIUtil;


public class AIUtilTest {

    @Test
    public void normalizeAngleTest() {
        Assert.assertEquals(-Math.PI, AIUtil.normalizeAngle((float) (-3 * Math.PI)), 0.01);

        Assert.assertEquals(Math.PI, AIUtil.normalizeAngle((float) (3 * Math.PI)), 0.01);
    }

    @Test
    public void thetaAngleTest() {
        Assert.assertEquals(Math.PI, AIUtil.thetaForAngleDiffrence(0, 0), 0.01);
        Assert.assertEquals(Math.PI - 1, AIUtil.thetaForAngleDiffrence(0, 1), 0.01);
        Assert.assertEquals(Math.PI, AIUtil.thetaForAngleDiffrence(1, 1), 0.01);
        Assert.assertEquals(Math.PI - 1, AIUtil.thetaForAngleDiffrence(1, 0), 0.01);
    }

    @Test
    public void rightThetaAngleTest() {
        Assert.assertTrue(AIUtil.rightThetaForAngleDiffrence(0, 0));
        Assert.assertTrue(AIUtil.rightThetaForAngleDiffrence(0, 1));
        Assert.assertTrue(AIUtil.rightThetaForAngleDiffrence(1, 1));
        Assert.assertFalse(AIUtil.rightThetaForAngleDiffrence(1, 0));
    }

    @Test
    public void normalDistTest() {
        Assert.assertEquals(1, AIUtil.normalDistFromMean(200, 50, 200), 0.01);
        Assert.assertEquals(1, AIUtil.normalDistFromMean(200, 25, 200), 0.01);
        Assert.assertEquals(0.6, AIUtil.normalDistFromMean(150, 50, 200), 0.01);
        Assert.assertEquals(0.13, AIUtil.normalDistFromMean(100, 50, 200), 0.01);
    }

    @Test
    public void angleDifferenceTest() {
        Assert.assertEquals(0, AIUtil.angleDiffrenceBetweenTwoAngles(0, 0), 0.01);
        Assert.assertEquals(1, AIUtil.angleDiffrenceBetweenTwoAngles(0, 1), 0.01);
        Assert.assertEquals(-1, AIUtil.angleDiffrenceBetweenTwoAngles(1, 0), 0.01);
        Assert.assertEquals(-3.5, AIUtil.angleDiffrenceBetweenTwoAngles(3.5f, 0), 0.01);
        Assert.assertEquals(0.1, AIUtil.angleDiffrenceBetweenTwoAngles(3.5f, 3.6f), 0.01);
    }

    @Test
    public void rightAngleDifferenceTest() {

        Assert.assertTrue(AIUtil.rightForAngleDiffrenceBetweenTwoAngles(0, 0));
        Assert.assertTrue(AIUtil.rightForAngleDiffrenceBetweenTwoAngles(0, 1));
        Assert.assertFalse(AIUtil.rightForAngleDiffrenceBetweenTwoAngles(1, 0));
        Assert.assertFalse(AIUtil.rightForAngleDiffrenceBetweenTwoAngles(3.5f, 0));
        Assert.assertTrue(AIUtil.rightForAngleDiffrenceBetweenTwoAngles(3.5f, 3.6f));
    }
}