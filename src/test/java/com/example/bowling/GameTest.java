package com.example.bowling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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

}
