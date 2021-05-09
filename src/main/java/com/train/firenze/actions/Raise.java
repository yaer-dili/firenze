package com.train.firenze.actions;

import static com.train.firenze.Pot.MIN_WAGER_SIZE;

import com.train.firenze.PokerGame;

public class Raise implements Action {
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.retrieveAwaitingList().poll();
        final var pot = pokerGame.retrievePotDetails();
        pot.updatePot(2 * MIN_WAGER_SIZE);
        pokerGame.updatePlayerWager(actionCompletedPlayer, pot.getPotMinWager());
        pokerGame.retrieveAwaitingList().offer(actionCompletedPlayer);
    }

}
