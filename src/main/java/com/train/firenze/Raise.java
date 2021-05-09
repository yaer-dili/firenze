package com.train.firenze;

import static com.train.firenze.PokerGame.MIN_WAGER_SIZE;

public class Raise implements Action{
    @Override
    public void execute(final PokerGame pokerGame) {
        final var actionCompletedPlayer = pokerGame.awaitingList.poll();
        pokerGame.potMinWager = 2 * MIN_WAGER_SIZE;
        pokerGame.pot += pokerGame.potMinWager;
        pokerGame.actionCompletedPlayerWithWager.put(actionCompletedPlayer, pokerGame.potMinWager);
        pokerGame.awaitingList.offer(actionCompletedPlayer);
    }
}
