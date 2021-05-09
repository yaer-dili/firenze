package com.train.firenze;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public enum Round {
    PRE_FLOP, FLOP, TURN, RIVER;

    void nextRound(final PokerGame pokerGame) {
        if (isCurrentRoundFinished(pokerGame)) {

            pokerGame.round = values()[ordinal() + 1];
            pokerGame.actionCompletedPlayerWithWager.clear();
            pokerGame.awaitingList = pokerGame.awaitingList.stream()
                    .sorted(Comparator.comparing(Player::getPosition))
                    .collect(Collectors.toCollection(LinkedList::new));

        }
    }

    private boolean isCurrentRoundFinished(final PokerGame pokerGame) {
        return pokerGame.actionCompletedPlayerWithWager.size() >= pokerGame.awaitingList.size()
                && pokerGame.actionCompletedPlayerWithWager
                .values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == pokerGame.pot.potMinWager || wager == 0);
    }
}
