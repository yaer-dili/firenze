package com.train.firenze;

import static com.train.firenze.Round.FLOP;
import static com.train.firenze.Round.PRE_FLOP;
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

        pokerGame.bet();

        assertThat(pokerGame.pot).isEqualTo(2);
        assertThat(pokerGame.activePlayer()).isEqualTo(playerB);
        assertThat(pokerGame.awaitingList).contains(playerA);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
    }

    @Test
    void should_start_next_round_when_all_player_bet() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final PokerGame pokerGame = new PokerGame(playerA, playerB);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.bet();
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.bet();
        assertThat(pokerGame.round).isEqualTo(FLOP);
        assertThat(pokerGame.pot).isEqualTo(4);
    }

    @Test
    void should_not_go_to_next_round_when_all_player_did_not_finish_bet() {
        final Player playerA = new Player("A");
        final Player playerB = new Player("B");
        final Player playerC = new Player("C");
        final PokerGame pokerGame = new PokerGame(playerA, playerB, playerC);
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.bet();
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);

        pokerGame.bet();
        assertThat(pokerGame.round).isEqualTo(PRE_FLOP);
        assertThat(pokerGame.pot).isEqualTo(4);
    }
}