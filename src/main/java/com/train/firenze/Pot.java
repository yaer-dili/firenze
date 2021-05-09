package com.train.firenze;

public class Pot {
    public static final int MIN_WAGER_SIZE = 2;
    public static final int DEFAULT_POT_CHIPS = 0;

    public int chips;
    public int potMinWager;

    public Pot() {
        this.chips = DEFAULT_POT_CHIPS;
        this.potMinWager = Pot.MIN_WAGER_SIZE;
    }
}