package com.train.firenze.actions;

import com.train.firenze.PokerGame;

public class Bet implements Action {
    @Override
    public void execute(PokerGame pokerGame) {
        final var activePlayer = pokerGame.retrieveActivePlayer();

        final var pot = pokerGame.retrievePotDetails();
        final var round = pokerGame.retrieveRound();
        pot.updatePot(pot.getPotMinWager());
        round.updatePlayerWager(activePlayer, pot.getPotMinWager());

        pokerGame.updateWaitingList(activePlayer);
    }
}
