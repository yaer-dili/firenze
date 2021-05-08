package com.train.firenze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PokerGame {
    public static final int MIN_WAGER_SIZE = 2;
    public final Queue<Player> awaitingList;
    private final Queue<Player> players;
    public int pot;
    public Round round;
    private List<Player> actionFinished = new ArrayList<>();

    public PokerGame(final Player... players) {
        this.players = new LinkedList<>(Arrays.asList(players));
        this.awaitingList = this.players;
        round = Round.PRE_FLOP;
    }

    public void bet() {
        pot += MIN_WAGER_SIZE;
        final var actionCompletedPlayer = awaitingList.poll();
        actionFinished.add(actionCompletedPlayer);
        awaitingList.offer(actionCompletedPlayer);
        nextRound();
    }

    private void nextRound() {
        if (actionFinished.size() == players.size()) {
            round = Round.values()[round.ordinal() + 1];
        }
    }

    public Player activePlayer() {
        return awaitingList.peek();
    }

    public void fold() {
        awaitingList.poll();
        nextRound();
    }

    public void check() {
        awaitingList.offer(awaitingList.poll());
    }
}
