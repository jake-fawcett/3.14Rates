package com.rear_admirals.york_pirates.screen;

import com.rear_admirals.york_pirates.Department;
import com.rear_admirals.york_pirates.PirateGame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinigameScreenTest {
    private MinigameScreen tester;

    @Before
    public void setUp() throws Exception {
        PirateGame game = new PirateGame();
        tester = new MinigameScreen(game, new Department("test", "Attack", game));
    }

    @Test
    public void betHeads() {
    }

    @Test
    public void betTails() {
    }
}