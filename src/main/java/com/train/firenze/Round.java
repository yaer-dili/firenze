package com.train.firenze;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

public class Round {
    final Map<Player, Integer> actionCompletedPlayerWithWager;
    RoundName currentRoundName;

    public Round() {
        this.currentRoundName = RoundName.PRE_FLOP;
        this.actionCompletedPlayerWithWager = new HashMap<>();
    }

    LinkedList<Player> next(Queue<Player> awaitingList, final int potMinWager) {
        if (actionCompletedPlayerWithWager.size() >= awaitingList.size()
                && actionCompletedPlayerWithWager.values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == potMinWager || wager == 0)) {

            currentRoundName = RoundName.values()[currentRoundName.ordinal() + 1];
            actionCompletedPlayerWithWager.clear();

            return awaitingList.stream()
                    .sorted(Comparator.comparing(Player::getPosition))
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        return (LinkedList<Player>) awaitingList;
    }
}