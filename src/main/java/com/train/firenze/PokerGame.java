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
    public final Map<Player, Integer> actionCompletedPlayerWithWager = new HashMap<>();
    public Queue<Player> awaitingList;
    public int pot;
    public Round round;
    public int potMinWager;

    public PokerGame(final Player... players) {
        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.potMinWager = MIN_WAGER_SIZE;
        round = Round.PRE_FLOP;
    }

    public void play(final Action action) {
        action.execute(this);

        nextRound();
    }

    private void nextRound() {
        if (isCurrentRoundFinished()) {
            round = Round.values()[round.ordinal() + 1];
            actionCompletedPlayerWithWager.clear();
            awaitingList = awaitingList.stream()
                    .sorted(Comparator.comparing(Player::getPosition))
                    .collect(Collectors.toCollection(LinkedList::new));

        }
    }

    public Player activePlayer() {
        return awaitingList.peek();
    }

    private boolean isCurrentRoundFinished() {
        return actionCompletedPlayerWithWager.size() >= awaitingList.size()
                && actionCompletedPlayerWithWager
                .values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == potMinWager || wager == 0);
    }
}
