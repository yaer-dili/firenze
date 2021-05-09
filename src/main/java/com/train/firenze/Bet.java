package com.train.firenze;

public class Bet implements Action {
    @Override
    public void execute(PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.retrieveAwaitingList().poll();
        final var pot = pokerGame.retrievePotDetails();
        pot.chips = pot.chips + pot.potMinWager;
        pokerGame.updatePlayerWager(actionCompletedPlayer, pot.potMinWager);
        pokerGame.retrieveAwaitingList().offer(actionCompletedPlayer);
    }
}
