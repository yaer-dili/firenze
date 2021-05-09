package com.train.firenze;

import static com.train.firenze.Round.FLOP;
import static com.train.firenze.Round.PRE_FLOP;
import static com.train.firenze.Round.TURN;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PokerGameTest {

    @Test
    void should_return_active_player_as_A_when_initiate_the_game_with_A_and_B_two_players() {
        final Player playerA = new Player("A");

        final PokerGame pokerGame = new PokerGame(playerA, new Player("B"));

        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
    }

    @Test
    void should_change_status_accordingly_when_player_one_bet() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final PokerGame pokerGame = new PokerGame(playerA, playerB);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());

        assertThat(pokerGame.pot.chips).isEqualTo(2);
        assertThat(pokerGame.activePlayer()).isEqualTo(playerB);
        assertThat(pokerGame.awaitingList).contains(playerA);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
    }

    @Test
    void should_start_next_round_when_all_player_bet() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(6);
        assertThat(pokerGame.round).isEqualTo(FLOP);
    }

    @Test
    void should_not_go_to_next_round_when_all_player_did_not_finish_bet() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        assertThat(pokerGame.pot.chips).isEqualTo(4);
    }

    @Test
    void should_remove_player_when_they_choose_fold() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        assertThat(pokerGame.pot.chips).isEqualTo(4);

        pokerGame.play(new Fold());
        assertThat(pokerGame.round).isEqualTo(FLOP);
        assertThat(pokerGame.pot.chips).isEqualTo(4);
        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
    }

    @Test
    void should_go_into_next_round_if_all_user_check() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(6);

        assertThat(pokerGame.round).isEqualTo(FLOP);
        pokerGame.play(new Check());
        pokerGame.play(new Check());
        assertThat(pokerGame.round).isEqualTo(FLOP);

        pokerGame.play(new Check());
        assertThat(pokerGame.pot.chips).isEqualTo(6);
        assertThat(pokerGame.round).isEqualTo(TURN);
    }

    @Test
    void should_go_into_next_round_when_only_one_player_check() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(6);

        assertThat(pokerGame.round).isEqualTo(FLOP);
        pokerGame.play(new Check());
        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(FLOP);
        assertThat(pokerGame.pot.chips).isEqualTo(8);

        assertThat(pokerGame.activePlayer()).isEqualTo(playerC);
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(10);

        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
        pokerGame.play(new Bet());
        assertThat(pokerGame.round).isEqualTo(TURN);
    }

    @Test
    void should_all_player_needs_to_match_the_raise_amount_when_someone_raise() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(6);

        assertThat(pokerGame.round).isEqualTo(FLOP);
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(8);

        pokerGame.play(new Raise());
        assertThat(pokerGame.pot.chips).isEqualTo(12);

        assertThat(pokerGame.activePlayer()).isEqualTo(playerC);
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(16);
        assertThat(pokerGame.round).isEqualTo(FLOP);

        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(20);
        assertThat(pokerGame.round).isEqualTo(TURN);
    }

    @Test
    void should_able_to_fold_in_any_round() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(6);

        assertThat(pokerGame.round).isEqualTo(FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Raise());
        pokerGame.play(new Bet());

        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
        pokerGame.play(new Fold());
        assertThat(pokerGame.awaitingList.size()).isEqualTo(2);
        assertThat(pokerGame.pot.chips).isEqualTo(16);
        assertThat(pokerGame.round).isEqualTo(TURN);
    }

    @Test
    void should_A_start_first_when_go_into_next_round() {
        final Player playerA = new Player("A", 1);
        final Player playerB = new Player("B", 2);
        final Player playerC = new Player("C", 3);
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Raise());
        assertThat(pokerGame.pot.chips).isEqualTo(8);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
        pokerGame.play(new Bet());
        assertThat(pokerGame.activePlayer()).isEqualTo(playerB);
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(16);

        assertThat(pokerGame.round).isEqualTo(FLOP);
        assertThat(pokerGame.activePlayer()).isEqualTo(playerA);
        pokerGame.play(new Check());
        assertThat(pokerGame.pot.chips).isEqualTo(16);
        assertThat(pokerGame.round).isEqualTo(FLOP);
    }

    @Test
    void should_start_forth_round_when_three_round_finished() {
        final Player playerA = new Player("A", 1);
        final Player playerB = new Player("B", 2);
        final Player playerC = new Player("C", 3);
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);

        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Raise());
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(16);

        assertThat(pokerGame.round).isEqualTo(FLOP);
        pokerGame.play(new Check());
        pokerGame.play(new Check());
        pokerGame.play(new Check());
        assertThat(pokerGame.awaitingList.size()).isEqualTo(3);
        assertThat(pokerGame.pot.chips).isEqualTo(16);

        assertThat(pokerGame.round).isEqualTo(TURN);
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        pokerGame.play(new Bet());
        assertThat(pokerGame.pot.chips).isEqualTo(28);

        assertThat(pokerGame.round).isEqualTo(Round.RIVER);
    }
}