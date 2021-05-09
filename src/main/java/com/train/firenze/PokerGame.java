package com.train.firenze;

import com.train.firenze.actions.Action;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PokerGame {
    private final Round round;
    private final Pot pot;
    private Queue<Player> awaitingList;

    public PokerGame(final Player... players) {
        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.round = new Round();
    }

    public void play(final Action action) {
        action.execute(this);
        this.awaitingList = round.next(this.awaitingList, this.pot.potMinWager);
    }

    public Player checkActivePlayer() {
        return retrieveAwaitingList().peek();
    }

    public Queue<Player> retrieveAwaitingList() {
        return awaitingList;
    }

    public RoundName retrieveCurrentRoundName() {
        return round.currentRoundName;
    }

    public Pot retrievePotDetails() {
        return pot;
    }

    public void updatePlayerWager(final Player activePlayer, final Integer wager) {
        round.actionCompletedPlayerWithWager.put(activePlayer, wager);
    }
}
