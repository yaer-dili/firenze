package com.train.firenze.actions;

import com.train.firenze.PokerGame;

public class Check implements Action {
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.retrieveAwaitingList().poll();
        pokerGame.updatePlayerWager(actionCompletedPlayer, 0);
        pokerGame.retrieveAwaitingList().offer(actionCompletedPlayer);
    }
}
