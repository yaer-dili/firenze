package com.train.firenze;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.stream.Collectors;

public class PokerGame {
    private final Map<Player, Integer> actionCompletedPlayerWithWager;
    private final Pot pot;
    private Round currentRound;
    private Queue<Player> awaitingList;

    public PokerGame(final Player... players) {
        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.currentRound = Round.PRE_FLOP;
        this.actionCompletedPlayerWithWager = new HashMap<>();
    }

    public void play(final Action action) {
        action.execute(this);
        nextRound();
    }

    public Player checkActivePlayer() {
        return retrieveAwaitingList().peek();
    }

    public Queue<Player> retrieveAwaitingList() {
        return awaitingList;
    }

    public void resetGameState() {
        actionCompletedPlayerWithWager.clear();
        this.awaitingList = this.awaitingList.stream()
                .sorted(Comparator.comparing(Player::getPosition))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    void nextRound() {
        if (actionCompletedPlayerWithWager.size() >= retrieveAwaitingList().size()
                && actionCompletedPlayerWithWager.values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == retrievePotDetails().potMinWager || wager == 0)) {

            this.currentRound = Round.values()[currentRound.ordinal() + 1];
            resetGameState();
        }
    }

    public Round retrieveRoundDetails() {
        return currentRound;
    }

    public Pot retrievePotDetails() {
        return pot;
    }

    public void updatePlayerWager(final Player activePlayer, final Integer wager) {
        actionCompletedPlayerWithWager.put(activePlayer, wager);
    }
}
