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
    public final Map<Player, Integer> actionCompletedPlayerWithWager = new HashMap<>();
    public final Pot pot;
    private Round currentRound;
    private Queue<Player> awaitingList;

    public PokerGame(final Player... players) {
        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.currentRound = Round.PRE_FLOP;
    }

    public void play(final Action action) {
        action.execute(this);
        nextRound();
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

    void nextRound() {
        if (actionCompletedPlayerWithWager.size() >= retrieveAwaitingList().size()
                && actionCompletedPlayerWithWager
                .values()
                .stream()
                .filter(Objects::nonNull)
                .allMatch(wager -> wager == pot.potMinWager || wager == 0)) {

            this.currentRound = Round.values()[currentRound.ordinal() + 1];
            resetGameState();
        }
    }

    public Round retrieveRoundDetails() {
        return currentRound;
    }
}
