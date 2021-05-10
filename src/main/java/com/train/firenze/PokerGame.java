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
        checkEligibility(players);

        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.round = new Round();
    }

    public void play(final Action action) {
        action.execute(this);
        this.awaitingList = this.round.next(this.awaitingList, this.pot.getPotMinWager());
    }

    private void checkEligibility(final Player[] players) {
        if (players.length < 2) {
            throw new IllegalArgumentException("at lease require two player");
        }
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

    public Round retrieveRound() {
        return round;
    }

    public Player retrieveActivePlayer() {
        return awaitingList.poll();
    }

    public void updateWaitingList(final Player actionCompletedPlayer) {
        awaitingList.offer(actionCompletedPlayer);
    }
}
