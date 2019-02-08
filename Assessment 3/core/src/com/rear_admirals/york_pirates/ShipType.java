package com.rear_admirals.york_pirates;

import com.badlogic.gdx.graphics.Texture;

public class ShipType {
    private int attack;
    private int defence;
    private int accuracy;
    private String name;
    private Texture texture;

    public ShipType(String name, int attack, int defence, int accuracy, int health) {
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.accuracy = accuracy;
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

    public Texture getTexture() {
        return texture;
    }

    // Static Ship Types go here
	public static ShipType Enemy = new ShipType("Sloop", 4, 4, 7, 80);
    public static ShipType Player = new ShipType("Brig", 5, 5, 5, 100);
	public static ShipType James = new ShipType("James", 5, 5, 5, 110);
	public static ShipType Van = new ShipType("Vanbrugh", 7, 5, 5, 120);
	public static ShipType Good = new ShipType("Goodricke", 5, 7, 5, 130);
	public static ShipType Lan = new ShipType("Langwith", 8, 8, 5, 150);

}
