package com.train.firenze;

public class Pot {
    public static final int MIN_WAGER_SIZE = 2;
    public static final int DEFAULT_POT_CHIPS = 0;
    private int potMinWager;
    private int chips;

    public Pot() {
        this.chips = DEFAULT_POT_CHIPS;
        this.potMinWager = Pot.MIN_WAGER_SIZE;
    }

    public void updatePot(final int wager) {
        if (wager > getPotMinWager()) {
            potMinWager = wager;
        }
        this.chips += potMinWager;
    }

    public int getChips() {
        return chips;
    }

    public int getPotMinWager() {
        return potMinWager;
    }
}