package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class ShipType {
    private int attack;
    private int defence;
    private int accuracy;
    private int goldValue;
    private int pointValue;
    private String name;
    private Texture texture;

    public ShipType(String name, int attack, int defence, int accuracy, int health, int goldValue, int pointValue) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
        this.goldValue = goldValue;
        this.pointValue = pointValue;
        this.texture = new Texture("ship4.png"); //TESTING (without assets created)
    } // There is currently no way to give ships a custom texture. Do we need this?

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public int getGoldValue() { return goldValue; }

    public int getPointValue() { return pointValue; }

    public Texture getTexture() {
        return texture;
    }

    // Static Ship Types go here
    public static ShipType Enemy = new ShipType("Schooner", 4, 4, 5, 80, 40, 40);
    public static ShipType Player = new ShipType("Brig", 5, 5, 5, 100, 60, 60);
    public static ShipType James = new ShipType("Galleon", 6, 6, 5, 110, 80, 80);
    public static ShipType Van = new ShipType("Frigate", 9, 6, 5, 120, 100, 100);
    public static ShipType Good = new ShipType("Man o' War", 6, 9, 5, 130, 120, 120);
    public static ShipType Lan = new ShipType("Leviathan", 10, 10, 5, 150, 140, 140);

}