package com.example.bowling;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void scoreShouldReturnZeroWhenGameStarts() {
        Game game = new Game();

        int result = game.score();

        assertThat(result).isZero();
    }
}
