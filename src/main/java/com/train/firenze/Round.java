package com.train.firenze;

import java.util.Objects;

public enum Round {
    PRE_FLOP, FLOP, TURN, RIVER;

    void nextRound(final PokerGame pokerGame) {
        if (isCurrentRoundFinished(pokerGame)) {

            pokerGame.round = values()[ordinal() + 1];
            pokerGame.resetGameState();

        }
    }

    private boolean isCurrentRoundFinished(final PokerGame pokerGame) {
        return pokerGame.actionCompletedPlayerWithWager.size() >= pokerGame.retrieveAwaitingList().size()
                && pokerGame.actionCompletedPlayerWithWager
                .values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == pokerGame.pot.potMinWager || wager == 0);
    }
}
