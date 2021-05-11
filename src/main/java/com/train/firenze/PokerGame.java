package com.train.firenze;

import com.train.firenze.actions.Action;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class PokerGame {
    private final Round round;
    private final Pot pot;
    private final List<Card> publicCards;
    private final Queue<Card> allCards;
    private Queue<Player> awaitingList;

    public Queue<Card> retrieveGameCards() {
        return allCards;
    }

    public PokerGame(final Player... players) {
        this.allCards = new LinkedList<>(Arrays.asList(Card.values()));
        checkEligibility(players);
        setupDefaultCardsForPlayer(players);

        this.awaitingList = new LinkedList<>(Arrays.asList(players));
        this.pot = new Pot();
        this.round = new Round();
        this.publicCards = new LinkedList<>();
    }

    private void setupDefaultCardsForPlayer(final Player[] players) {
        Arrays.stream(players)
                .forEach(player -> player.setCards(Arrays.asList(allCards.poll(), allCards.poll())));
    }

    public void play(final Action action) {
        action.execute(this);
        this.awaitingList = round.next(this);
    }

    private void checkEligibility(final Player[] players) {
        if (players.length < 2) {
            throw new IllegalArgumentException("at least require two player");
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

    public List<Player> showdown() {
        final var orderedPlayers = awaitingList.stream()
                .sorted((player1, player2) -> compareCards(player1.getCards(), player2.getCards()))
                .collect(Collectors.toList());

        final var firstWinner = orderedPlayers.get(0);

        final var winners = orderedPlayers.stream()
                .filter(player -> player.getCards().equals(firstWinner.getCards()))
                .collect(Collectors.toList());

        winners.forEach(player -> player.setChips(pot.getChips() / winners.size()));

        return winners;
    }

    private int compareCards(final List<Card> player1Cards, final List<Card> player2Cards) {

        //TODO(2021-05-10T13:06):  compare card algorithm
        final var player1Value = calculateValue(player1Cards);
        final var player2Value = calculateValue(player2Cards);

        return player1Value.compareTo(player2Value);
    }

    private String calculateValue(final List<Card> playerCards) {
        final List<Card> showDownCards = new ArrayList<>(5);
        showDownCards.addAll(playerCards);
        showDownCards.addAll(this.publicCards);
        return showDownCards.stream()
                .map(Enum::toString)
                .collect(Collectors.joining(","));
    }

    public List<Card> retrieveGamePublicCards() {
        return this.publicCards;
    }
}
