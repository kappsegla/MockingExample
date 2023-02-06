package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();

    @Test
    void spareIsToKnockDownAllPinsWithTwoRolls() {
        game.roll(2);
        game.roll(8);

        assertEquals(1, game.spare = 1);
    }

    @Test
    void strikeIsToKnockDownAllPinsWithOneRolls() {
        game.roll(10);

        assertEquals(1, game.strike = 1);
    }

    @Test
    void addScoreToLastFrameIfRollIsSpare() {
        game.rounds = 10;
        game.roll(3);
        game.roll(7);
        game.roll(8);

        assertEquals(26, game.frames[9]);
    }

    @Test
    void addScoreToLastFrameIfRollIsStrike() {
        game.rounds = 10;
        game.roll(10);
        game.roll(10);
        game.roll(10);

        assertEquals(30, game.frames[9]);
    }

    @Test
    void addingRollScore() {
        game.roll(3);
        assertEquals(3, game.frameScore());
    }
    @Test
    void returnTotalOfRandomBowlingScore() {

        game.roll(2);
        game.roll(2); // 4

        game.roll(10);// 14

        game.roll(3);
        game.roll(1); // 4

        game.roll(3);
        game.roll(3); // 6

        game.roll(1);
        game.roll(1); // 2


        assertEquals(30, game.score());

    }

}