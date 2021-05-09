package com.train.firenze.actions;

import com.train.firenze.PokerGame;

public class Bet implements Action {
    @Override
    public void execute(PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.retrieveAwaitingList().poll();
        final var pot = pokerGame.retrievePotDetails();
        pot.updatePot(pot.getPotMinWager());
        pokerGame.updatePlayerWager(actionCompletedPlayer, pot.getPotMinWager());
        pokerGame.retrieveAwaitingList().offer(actionCompletedPlayer);
    }
}
