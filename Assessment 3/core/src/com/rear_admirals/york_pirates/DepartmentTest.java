package com.rear_admirals.york_pirates;

import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {
    private Department tester;

    @Test
    public void purchase() {
        PirateGame testGame = new PirateGame();
        tester = new Department("test", "Attack", testGame);
        tester.purchase();
    }

    @Test
    public void getPrice() {
    }
}