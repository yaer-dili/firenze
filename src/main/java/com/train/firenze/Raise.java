package com.train.firenze;

import static com.train.firenze.Pot.MIN_WAGER_SIZE;

public class Raise implements Action {
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.awaitingList.poll();
        final var pot = pokerGame.pot;
        pot.potMinWager = 2 * MIN_WAGER_SIZE;
        pot.chips = pot.chips + pot.potMinWager;
        pokerGame.actionCompletedPlayerWithWager.put(actionCompletedPlayer, pot.potMinWager);
        pokerGame.awaitingList.offer(actionCompletedPlayer);
    }
}