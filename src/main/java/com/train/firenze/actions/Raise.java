package com.train.firenze.actions;

import static com.train.firenze.Pot.MIN_WAGER_SIZE;

import com.train.firenze.PokerGame;

public class Raise implements Action {
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.retrieveAwaitingList().poll();
        final var pot = pokerGame.retrievePotDetails();
        pot.potMinWager = 2 * MIN_WAGER_SIZE;
        pot.chips = pot.chips + pot.potMinWager;
        pokerGame.updatePlayerWager(actionCompletedPlayer, pot.potMinWager);
        pokerGame.retrieveAwaitingList().offer(actionCompletedPlayer);
    }
}
