package uk.ac.york.sepr4.utils;


import uk.ac.york.sepr4.object.entity.Entity;

import com.badlogic.gdx.utils.Array;

public class AIUtil {

    /**
     * @param x1 The original x position
     * @param angle The angle you want to move the x by
     * @param distance The distance you want to move the x by on that angle
     * @return The x position once x1 has been moved along by that angle for a certain distance
     */
    public static float getXwithAngleandDistance(float x1, float angle, float distance) {
        return (float)(x1 + distance*Math.sin(angle));
    }

    //Same as before but Y
    public static float getYwithAngleandDistance(float y1, float angle, float distance) {
        return (float)(y1 - distance*Math.cos(angle));
    }

    /**
     * Takes any angle and converts it back down to the range 0 to 2PI
     * This is due to how LibGDX uses angles where you can have an actor with 10pi if rotated 5 times
     *
     * @param angle
     * @return angle restricted to 0 to 2PI
     */
    public static float normalizeAngle(float angle) {
        //Have to do it in this bad way due to angle mod 2PI not working
        return (float) (angle%(2*Math.PI));
    }

    /**
     * Refer to NPC Functions 4
     * @param thetaP
     * @param thetaTP
     * @return theta (Green angle)
     */
    public static float thetaForAngleDiffrence(double thetaP, double thetaTP){
        double theta;
        if (thetaP <= thetaTP && thetaTP <= Math.PI) {
            theta = thetaP + (Math.PI - thetaTP);
        } else if ((2 * Math.PI - thetaP) <= (2 * Math.PI - thetaTP) && (2 * Math.PI - thetaTP) <= Math.PI) {
            theta = (2 * Math.PI - thetaP) + (Math.PI - (2 * Math.PI - thetaTP));
        } else if (thetaTP <= Math.PI && thetaP > thetaTP && (2 * Math.PI - thetaP) >= (Math.PI - thetaTP)) {
            theta = (2 * Math.PI - thetaP) - (Math.PI - thetaTP);
        } else if ((2 * Math.PI - thetaTP) <= Math.PI && thetaTP > thetaP && thetaP >= (Math.PI - (2 * Math.PI - thetaTP))) {
            theta = thetaP - (Math.PI - (2 * Math.PI - thetaTP));
        } else if (thetaTP <= Math.PI && thetaP > thetaTP && (2 * Math.PI - thetaP) <= (Math.PI - thetaTP)) {
            theta = (Math.PI - thetaTP) - (2 * Math.PI - thetaP);
        } else if ((2 * Math.PI - thetaTP) <= Math.PI && thetaTP > thetaP && (Math.PI - (2 * Math.PI - thetaTP)) >= thetaP) {
            theta = (Math.PI - (2 * Math.PI - thetaTP)) - thetaP;
        } else {
            theta = 0;
        }
        return (float)theta;
    }

    /**
     * Refer to NPC Functions 4 but rather now just returns whether the angle is turning right or left
     * @param thetaP
     * @param thetaTP
     * @return right = true, left = false
     */
    public static boolean rightThetaForAngleDiffrence(double thetaP, double thetaTP){
        boolean right;
        if (thetaP <= thetaTP && thetaTP <= Math.PI) {
            right = true;
        } else if ((2 * Math.PI - thetaP) <= (2 * Math.PI - thetaTP) && (2 * Math.PI - thetaTP) <= Math.PI) {
            right = false;
        } else if (thetaTP <= Math.PI && thetaP > thetaTP && (2 * Math.PI - thetaP) >= (Math.PI - thetaTP)) {
            right = false;
        } else if ((2 * Math.PI - thetaTP) <= Math.PI && thetaTP > thetaP && thetaP >= (Math.PI - (2 * Math.PI - thetaTP))) {
            right = true;
        } else if (thetaTP <= Math.PI && thetaP > thetaTP && (2 * Math.PI - thetaP) <= (Math.PI - thetaTP)) {
            right = true;
        } else if ((2 * Math.PI - thetaTP) <= Math.PI && thetaTP > thetaP && (Math.PI - (2 * Math.PI - thetaTP)) >= thetaP) {
            right = false;
        } else {
            right = true;
        }
        return right;
    }

    /**
     * Refer to NPC Functions 2
     * @param source
     * @param target
     * @param addedSpeed - A value to increase the projectile's speed (usually by the source's speed)
     * @return - Returns the angle to shoot at or move in to hit the target at the right time going at the current speed
     */
    public static float perfectAngleToCollide(Entity source, Entity target, double addedSpeed) {
        double thetaP = normalizeAngle(target.getAngle());
        double thetaTP = source.getAngleTowardsEntity(target);

        boolean right = rightThetaForAngleDiffrence(thetaP, thetaTP);
        double theta = thetaForAngleDiffrence(thetaP, thetaTP);

        double b = timeForPerfectAngleToCollide(source, target, (float)theta, addedSpeed);
        double time = source.distanceFrom(target) / (source.getSpeed() + addedSpeed);

        double SE = (source.getSpeed() + addedSpeed);
        double SP = target.getSpeed();

        double PM = b * SP;
        double EP = time * SE;
        double ME = b * SE;

        double shotAngle = Math.acos(((ME * ME) + (EP * EP) - (PM * PM)) / (2 * ME * EP));

        if (right == true) {
            shotAngle = thetaTP - shotAngle;
        } else {
            shotAngle = thetaTP + shotAngle;
        }
        if (((ME * ME) + (EP * EP) - (PM * PM)) / (2 * ME * EP) > 1 || ((ME * ME) + (EP * EP) - (PM * PM)) / (2 * ME * EP) < -1) {
            shotAngle = thetaTP;
        }

        return normalizeAngle((float) shotAngle);
    }

    /**
     * Refer to NPC Functions 2
     * @param source
     * @param target
     * @param theta
     * @param addedSpeed
     * @return b = the time taken for the source object or shot to connect with the target
     */
    public static float timeForPerfectAngleToCollide(Entity source, Entity target, float theta, double addedSpeed) {
        double time = source.distanceFrom(target) / (source.getSpeed() + addedSpeed);

        double b = time / (2 * Math.cos(theta));
        if (b < 0) {
            b = -b;
        }
        return (float) b;
    }

    //Functions for knowing the force due the distance
    public static float normalDistFromMean(float dist, float standardDeviation, float mean) {
        //Formula for a normal distbution to find the height
        double fx = (1 / (Math.sqrt(2 * Math.PI) * (double)standardDeviation)) * Math.pow(Math.E, -(Math.pow((dist - (double) mean), 2) / (2 * Math.pow((double)standardDeviation, 2))));
        //Just incase it goes over 1 for error stops
        if(fx/(1 / (Math.sqrt(2 * Math.PI) * (double)standardDeviation)) * Math.pow(Math.E, -(1 / (2 * Math.pow((double)standardDeviation, 2)))) > 1){
            return 1f;
        }
        //Is fx/by max fx when the dist = mean
        return (float) (fx/(1 / (Math.sqrt(2 * Math.PI) * (double)standardDeviation)) * Math.pow(Math.E, -(1 / (2 * Math.pow((double)standardDeviation, 2)))));
    }
    public static float straightLineGraphOneIfCloser(float dist, float lowestdist, float startdist) {
        if(dist <= lowestdist){
            return 1f;
        } else if (dist<= startdist){
            return 0.01f*(dist-lowestdist); //possible problem
        } else {
            return 0f;
        }
    }
    //********************

    //Returns the diffrence between 2 angles where angle 1 is the one with the respect (Same as doing a dot product of 2 vectors basically)
    public static float angleDiffrenceBetweenTwoAngles(float angle1, float angle2){
        angle1 = normalizeAngle(angle1);
        angle2 = normalizeAngle(angle2);
        if (normalizeAngle(angle2 - angle1) > Math.PI){
            return (float)(2* Math.PI - normalizeAngle(angle2 - angle1));
        } else {
            return normalizeAngle(angle2 - angle1);
        }
    }

    //Returns true if angle2 is right of angle1 (meaning if I travel along angle1 then turn to angle2 will it be left or right)
    public static boolean rightForAngleDiffrenceBetweenTwoAngles(float angle1, float angle2){
        angle1 = normalizeAngle(angle1);
        angle2 = normalizeAngle(angle2);
        if (angleDiffrenceBetweenTwoAngles(angle1, angle2) >= 0){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Refer to NPC Functions 5 - Same as adding together a series of vectors
     * @param angles
     * @param forces
     * @return An array acting as a pair (Resultant force magnitude, Resultant force angle) [Basically a vector]
     */
    public static Array<Float> resultantForce(Array<Float> angles, Array<Float> forces){
        Array<Float> force_angle = new Array<Float>();
        float N = 0, E = 0;
        double sigma;
        for (int i = 0; i<angles.size; i++){
            if (normalizeAngle(angles.get(i)) <= Math.PI/2){
                E += forces.get(i)*Math.sin(angles.get(i));
                N -= forces.get(i)*Math.cos(angles.get(i));
            } else if (normalizeAngle(angles.get(i)) <= Math.PI){
                E += forces.get(i)*Math.cos(angles.get(i) - Math.PI/2);
                N += forces.get(i)*Math.sin(angles.get(i) - Math.PI/2);
            } else if (normalizeAngle(angles.get(i)) <= 3*Math.PI/2){
                E -= forces.get(i)*Math.sin(angles.get(i) - Math.PI);
                N += forces.get(i)*Math.cos(angles.get(i) - Math.PI);
            } else {
                E -= forces.get(i)*Math.cos(angles.get(i) - 3*Math.PI/2);
                N -= forces.get(i)*Math.sin(angles.get(i) - 3*Math.PI/2);
            }
        }
        if (N >= 0 && E <= 0) {
            sigma = Math.atan(-E / N);
        } else if (N <= 0 && E <= 0) {
            sigma = (Math.PI / 2 + Math.atan(-N / -E));
        } else if (N <= 0 && E >= 0) {
            sigma = (Math.PI + Math.atan(E / -N));
        } else {
            sigma = ((3 * Math.PI) / 2 + Math.atan(N / E));
        }

        force_angle.add((float)Math.sqrt(N*N + E*E));
        force_angle.add((float)sigma);
        return force_angle;
    }
}
