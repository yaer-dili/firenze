package com.train.firenze;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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

    LinkedList<Player> next(PokerGame pokerGame) {
        final var awaitingList = pokerGame.retrieveAwaitingList();

        if (actionCompletedPlayerWithWager.size() >= awaitingList.size()
                && actionCompletedPlayerWithWager.values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == pokerGame.retrievePotDetails().getPotMinWager() || wager == 0)) {

            final var newRoundName = RoundName.values()[currentRoundName.ordinal() + 1];
            currentRoundName = newRoundName;
            actionCompletedPlayerWithWager.clear();

            if (newRoundName != RoundName.SHOWDOWN) {
                pokerGame.retrieveGamePublicCards().add(pokerGame.retrieveGameCards().poll());
            }

            return awaitingList.stream()
                    .sorted(Comparator.comparing(Player::getPosition))
                    .collect(Collectors.toCollection(LinkedList::new));
        }

        return (LinkedList<Player>) awaitingList;
    }

    public void updatePlayerWager(final Player activePlayer, final Integer wager) {
        actionCompletedPlayerWithWager.put(activePlayer, wager);
    }
}