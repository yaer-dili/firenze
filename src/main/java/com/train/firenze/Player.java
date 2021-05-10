package com.train.firenze;

import java.util.List;

public class Player {
    private final String name;
    private final int position;
    private int chips;
    private List<Card> cards;

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

    public List<Card> getCards() {
        return cards;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(final int chips) {
        this.chips = chips;
    }

    public void setCards(final List<Card> cards) {
        this.cards = cards;
    }
}
