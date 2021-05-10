package com.train.firenze.actions;

import com.train.firenze.PokerGame;

public class Check implements Action {
    @Override
    public void execute(final PokerGame pokerGame) {
        final var activePlayer = pokerGame.retrieveActivePlayer();

        final var round = pokerGame.retrieveRound();
        round.updatePlayerWager(activePlayer, 0);

        pokerGame.updateWaitingList(activePlayer);
    }
}
