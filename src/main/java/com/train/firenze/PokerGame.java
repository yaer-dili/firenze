package com.train.firenze;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class PokerGame {
    public final Map<Player, Integer> actionCompletedPlayerWithWager = new HashMap<>();
    public final Pot pot;
    public Round round;
    private Queue<Player> awaitingList;

    public PokerGame(final Player... players) {
        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.round = Round.PRE_FLOP;
    }

    public void play(final Action action) {
        action.execute(this);
        round.nextRound(this);
    }

    public Player activePlayer() {
        return retrieveAwaitingList().peek();
    }

    public Queue<Player> retrieveAwaitingList() {
        return awaitingList;
    }

    public void resetGameState() {
        this.actionCompletedPlayerWithWager.clear();
        this.awaitingList = this.awaitingList.stream()
                .sorted(Comparator.comparing(Player::getPosition))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
