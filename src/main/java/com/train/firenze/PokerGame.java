package com.train.firenze;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PokerGame {
    public static final int MIN_WAGER_SIZE = 2;
    public final Queue<Player> awaitingList;
    private final Queue<Player> players;
    public int pot;
    public Round round;
    private Map<Player, Integer> actionCompletePlyerWithWager = new HashMap<>();
    private int roundWager;

    public PokerGame(final Player... players) {
        this.players = new LinkedList<>(Arrays.asList(players));
        this.awaitingList = this.players;
        round = Round.PRE_FLOP;
    }

    public void bet() {
        pot += MIN_WAGER_SIZE;
        roundWager = MIN_WAGER_SIZE;
        final var actionCompletedPlayer = awaitingList.poll();
        actionCompletePlyerWithWager.put(actionCompletedPlayer, MIN_WAGER_SIZE);
        awaitingList.offer(actionCompletedPlayer);
        nextRound();
    }

    private void nextRound() {
        if (actionCompletePlyerWithWager.size() == awaitingList.size()
                && actionCompletePlyerWithWager.values().stream().allMatch(wager -> wager == roundWager)) {
            round = Round.values()[round.ordinal() + 1];

            actionCompletePlyerWithWager.clear();
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
        final var actionCompletedPlayer = awaitingList.poll();

        awaitingList.offer(actionCompletedPlayer);
        actionCompletePlyerWithWager.put(actionCompletedPlayer, MIN_WAGER_SIZE);

        nextRound();
    }

    public void call() {
        final var actionCompletedPlayer = awaitingList.poll();
        actionCompletePlyerWithWager.put(actionCompletedPlayer, MIN_WAGER_SIZE);
        awaitingList.offer(actionCompletedPlayer);
        pot += MIN_WAGER_SIZE;
        nextRound();
    }
}
