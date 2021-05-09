package com.train.firenze;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PokerGame {
    public final Map<Player, Integer> actionCompletedPlayerWithWager = new HashMap<>();
    public final Pot pot;
    public Queue<Player> awaitingList;
    public Round round;

    public PokerGame(final Player... players) {
        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.round = Round.PRE_FLOP;
    }

    public void play(final Action action) {
        action.execute(this);
        round.nextRound(this);
    }

    public Player activePlayer() {
        return awaitingList.peek();
    }

}
