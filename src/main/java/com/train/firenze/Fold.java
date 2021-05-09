package com.train.firenze;

public class Fold implements Action{
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.awaitingList.poll();
        pokerGame.actionCompletedPlayerWithWager.put(actionCompletedPlayer, null);
    }
}
