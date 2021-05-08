package com.train.firenze;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PokerGame {
    public static final int MIN_WAGER_SIZE = 2;
    public final Queue<Player> awaitingList = new LinkedList<>();
    private final Queue<Player> players;
    public int pot;
    public Round round;

    public PokerGame(final Player... players) {
        this.players = new LinkedList<>(Arrays.asList(players));
        round = Round.PRE_FLOP;
    }

    public void bet() {
        pot += MIN_WAGER_SIZE;
        final var actionCompletedPlayer = players.poll();
        players.offer(actionCompletedPlayer);
        awaitingList.offer(actionCompletedPlayer);
        nextRound();
    }

    private void nextRound() {
        if (awaitingList.size() == players.size()) {
            round = Round.values()[round.ordinal()+1];
        }
    }

    public Player activePlayer() {
        return players.peek();

    }

    public void fold() {
        players.poll();
        nextRound();
    }
}
