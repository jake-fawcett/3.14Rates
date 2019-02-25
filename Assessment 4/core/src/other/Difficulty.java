package other;

import static other.Constants.EASY_SCORE_MULTIPLIER;
import static other.Constants.MED_SCORE_MULTIPLIER;
import static other.Constants.DIFF_SCORE_MULTIPLIER;

/**
 * The difficulty settings avalible for the game and the score multipliers that go with them. Changing the difficulty
 * will change which ships are picked for the player to face.
 */
public enum Difficulty implements java.io.Serializable  {
    EASY(EASY_SCORE_MULTIPLIER), MEDIUM(MED_SCORE_MULTIPLIER), HARD(DIFF_SCORE_MULTIPLIER);

    private double multiplier;

    Difficulty(Double multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * @return The score multiplier associated with a given difficulty
     */
    public double getMultiplier() {
        return multiplier;
    }
}
