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
    public int roundWager;
    private int potMinWager;

    public PokerGame(final Player... players) {
        this.players = new LinkedList<>(Arrays.asList(players));
        this.awaitingList = this.players;
        round = Round.PRE_FLOP;
    }

    public void bet() {
        if (potMinWager == 0) {
            pot += MIN_WAGER_SIZE;
            potMinWager = MIN_WAGER_SIZE;
            roundWager = MIN_WAGER_SIZE;
        } else {
            pot += potMinWager;
            roundWager = potMinWager;
        }
        final var actionCompletedPlayer = awaitingList.poll();
        actionCompletePlyerWithWager.put(actionCompletedPlayer, roundWager);
        awaitingList.offer(actionCompletedPlayer);
        nextRound();
    }

    private void nextRound() {
        if (isCurrentRoundFinished()) {
            round = Round.values()[round.ordinal() + 1];
            actionCompletePlyerWithWager.clear();
            awaitingList = awaitingList.stream()
                    .sorted(Comparator.comparing(Player::getPosition))
                    .collect(Collectors.toCollection(LinkedList::new));

        }
    }

    private boolean isCurrentRoundFinished() {
        return actionCompletePlyerWithWager.size() >= awaitingList.size()
                && actionCompletePlyerWithWager
                .values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == roundWager);
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
        actionCompletePlyerWithWager.put(actionCompletedPlayer, 0);
        roundWager = 0;

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
        potMinWager = raiseWager;
        actionCompletePlyerWithWager.put(actionCompletedPlayer, raiseWager);
        awaitingList.offer(actionCompletedPlayer);
        pot += raiseWager;
        nextRound();
    }
}
