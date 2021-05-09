package com.train.firenze;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

public class PokerGame {
    public static final int MIN_WAGER_SIZE = 2;
    private final Queue<Player> players;
    public Queue<Player> awaitingList;
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
        if (actionCompletePlyerWithWager.size() >= awaitingList.size()
                && actionCompletePlyerWithWager.values().stream().filter(Objects::nonNull).allMatch(wager -> wager == roundWager)) {
            round = Round.values()[round.ordinal() + 1];

            actionCompletePlyerWithWager.clear();
            awaitingList = awaitingList.stream()
                    .sorted(Comparator.comparing(Player::getPosition))
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }

    public Player activePlayer() {
        return awaitingList.peek();
    }

    public void fold() {
        actionCompletePlyerWithWager.put(awaitingList.poll(), null);
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
        actionCompletePlyerWithWager.put(actionCompletedPlayer, roundWager);
        awaitingList.offer(actionCompletedPlayer);
        pot += roundWager;
        nextRound();
    }

    public void raise() {
        final var actionCompletedPlayer = awaitingList.poll();
        final int raiseWager = 2 * MIN_WAGER_SIZE;
        roundWager = raiseWager;
        actionCompletePlyerWithWager.put(actionCompletedPlayer, raiseWager);
        awaitingList.offer(actionCompletedPlayer);
        pot += raiseWager;
        nextRound();
    }
}
