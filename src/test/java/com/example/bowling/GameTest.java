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
}
