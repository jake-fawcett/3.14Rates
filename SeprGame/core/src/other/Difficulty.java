package other;

import static other.Constants.EASY_SCORE_MULTIPLIER;
import static other.Constants.MED_SCORE_MULTIPLIER;
import static other.Constants.DIFF_SCORE_MULTIPLIER;

public enum Difficulty {
    EASY(EASY_SCORE_MULTIPLIER), MEDIUM(MED_SCORE_MULTIPLIER), HARD(DIFF_SCORE_MULTIPLIER);

    private double multiplier;

    Difficulty(Double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}
