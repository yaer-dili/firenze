package com.train.firenze;

public class Bet implements Action {
    @Override
    public void execute(PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.awaitingList.poll();
        pokerGame.pot += pokerGame.potMinWager;
        pokerGame.actionCompletedPlayerWithWager.put(actionCompletedPlayer, pokerGame.potMinWager);
        pokerGame.awaitingList.offer(actionCompletedPlayer);
    }
}
