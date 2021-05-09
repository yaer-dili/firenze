package com.train.firenze;

public class Bet implements Action {
    @Override
    public void execute(PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.awaitingList.poll();
        final var pot = pokerGame.pot;
        pot.chips = pot.chips + pot.potMinWager;
        pokerGame.actionCompletedPlayerWithWager.put(actionCompletedPlayer, pot.potMinWager);
        pokerGame.awaitingList.offer(actionCompletedPlayer);
    }
}
