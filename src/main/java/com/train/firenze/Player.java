package com.train.firenze;

public class Player {
    private final String name;
    private final int position;

    public Player(final String name) {
        this.name = name;
        this.position = 0;
    }

    public Player(final String name, final int position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }

    public int getPosition() {
        return position;
    }
}
