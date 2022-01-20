package com.example.bowling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    Game game = new Game();

    @Test
    void scoreShouldReturnZeroWhenGameStarts() {
        int result = game.score();

        assertThat(result).isZero();
    }

    @Test
    void scoreShouldReturnArgumentGivenToRoll() {
        game.roll(5);

        int result = game.score();

        assertThat(result).isEqualTo(5);
    }

    @Test
    void maximumOf10FramesEquivalentToTwentyStandardRolls() {
        int frames = 12;
        for (int i = 0; i < frames; i++) {
            game.roll(1);
            game.roll(1);
        }

        int result = game.score();

        assertThat(result).isEqualTo(20);
    }

    @Test
    void maximum10PointsPerStandardFrame() {
        game.roll(7);
        game.roll(8);

        int result = game.score();

        assertThat(result).isEqualTo(10);
    }

    @Test
    void scoreIncreasesInMoreThanOneStandardFrame() {
        game.roll(7);
        game.roll(8);
        game.roll(5);

        int result = game.score();

        assertThat(result).isEqualTo(15);
    }

    @Test
    void strikeShouldReturnOneRollInAFrame() {
        game.roll(10);  // 1st frame
        game.roll(8);   // 2nd frame

        int result = game.score();

        assertThat(result).isEqualTo(26);
    }

    @Test
    void strikeShouldReturnBonusOfNext2RollsInNextFrame() {
        game.roll(10);
        game.roll(3);
        game.roll(3);

        int result = game.score();

        assertThat(result).isEqualTo(22);
    }


    @Test
    void successiveStrikesShouldReturnBonusOfNext2RollsForEachStrike() {
        game.roll(10);
        game.roll(10);
        game.roll(3);
        game.roll(4);

        int result = game.score();

        assertThat(result).isEqualTo(47);
    }

    @Test
    void spareShouldReturnBonusOfNextRoll() {
        game.roll(7);
        game.roll(3);
        game.roll(4);

        int result = game.score();

        assertThat(result).isEqualTo(18);
    }

    @Test
    void strikeAfterASpareShouldReturnCorrectScore() {
        game.roll(2);
        game.roll(8);
        game.roll(4);
        game.roll(5);
        game.roll(3);
        game.roll(7);
        game.roll(10);
        game.roll(10);
        game.roll(6);
        game.roll(2);

        int result = game.score();

        assertThat(result).isEqualTo(95);
    }

    @Test
    void twoSparesInSuccessionShouldReturnBonuses() {
        game.roll(2);
        game.roll(8);
        game.roll(4);
        game.roll(6);
        game.roll(5);

        int result = game.score();

        assertThat(result).isEqualTo(34);
    }

}
