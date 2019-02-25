package location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Allows the creation of a College object, which stores whether a particular college has been beaten
 * Had to implement Serializable due to encoding needed for saving game data.
 */
public class College implements java.io.Serializable {
    String name;
    private boolean bossAlive;

    public College(String name) {
        this.name = name;
        this.bossAlive = true;
    }

    public String getName() {
        return name;
    }

    public boolean isBossAlive() {
        return bossAlive;
    }
    public void setBossAlive(boolean bossAlive) {
        this.bossAlive = bossAlive;
    }

    /**
     * Static instances of colleges to be accessed from anywhere within the code base
     */
    public static College Derwent = new College("Derwent");
    public static College Vanbrugh = new College("Vanbrugh");
    public static College James = new College("James");
    public static College Alcuin = new College("Alcuin");
    public static College Langwith = new College("Langwith");
    public static College Goodricke = new College("Goodricke");

    /**
     * List of colleges used to check for game win condition
     * No Derwent as that is home college
     */
    public static ArrayList<College> colleges = new ArrayList<College>(
            Arrays.asList(
                    Vanbrugh, James, Alcuin, Langwith, Goodricke
            )
    );
}
