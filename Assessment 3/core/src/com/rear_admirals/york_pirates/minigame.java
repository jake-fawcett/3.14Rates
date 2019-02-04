package com.rear_admirals.york_pirates;

import java.util.Random;

public class minigame {
    private Player player;

    /**
     * Play the minigame by choosing heads or tails. Every time you flip the coin it is double or nothing.
     */
    public void play() {
        int bet = getBet();
        String decision;

        while (true) {
            decision = getDecision();
            if (decision == flipCoin()) {
                bet *= 2;
                if (endMinigame()) {
                    player.setGold(player.getGold() + bet);
                    return;
                }
            } else {
//              TODO front-end please implement lose message/screen
                return;
            }
        }
    }

    //TODO, front-end implement this please

    /**
     * Find out if the player wants to end the minigame and take their money
     *
     * @return true (player wants to end) or false (player wants to keep playing)
     */
    private boolean endMinigame() {
        //If player wants to end minigame
        //  return true
        //else
        return false;
    }

    /**
     * Simulate flipping a coin
     *
     * @return "h" or "t" relating to heads or tails
     */
    private String flipCoin() {
        Random rand = new Random();
        if (rand.nextBoolean()) {
            return "h";
        } else {
            return "t";
        }
    }

    // Todo, front-end implement this please

    /**
     * Get the player's guess as to whether the coin will land on heads or tails
     *
     * @return "h" or "t" relating to heads or tails
     */
    private String getDecision() {
        // If player wants heads:
        //    Return "h"
        // Else
        //    return "t"
        return new String("h");
    }

    // Todo, front-end implement this please

    /**
     * Find out how much the player wants to bet
     *
     * @return The amount of gold the player wants to bet
     */
    private int getBet() {
        int bet = 0;
        //  TODO Get the amount of gold the player wants to bet

        if (bet > player.getGold()) {
            throw new IllegalArgumentException("Trying to bet more money than you have!");
        } else {
            player.setGold(player.getGold() - bet);
            return bet;
        }
    }

}
