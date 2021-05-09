package com.train.firenze;

public class Check implements Action{
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.awaitingList.poll();
        pokerGame.actionCompletedPlayerWithWager.put(actionCompletedPlayer, 0);
        pokerGame.awaitingList.offer(actionCompletedPlayer);
    }
}
