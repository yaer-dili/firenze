package com.train.firenze.actions;

import static com.train.firenze.Pot.MIN_WAGER_SIZE;

import com.train.firenze.PokerGame;

public class Raise implements Action {
    @Override
    public void execute(final PokerGame pokerGame) {
        final var activePlayer = pokerGame.retrieveActivePlayer();
        final var pot = pokerGame.retrievePotDetails();
        final var round = pokerGame.retrieveRound();

        pot.updatePot(2 * MIN_WAGER_SIZE);
        round.updatePlayerWager(activePlayer, pot.getPotMinWager());

        pokerGame.updateWaitingList(activePlayer);
    }

}
