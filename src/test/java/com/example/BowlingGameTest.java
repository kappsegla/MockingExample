package com.example;


import BowlingGame.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    Game game = new Game();

    @Test
    public void fullGameOffMissedRolls(){
        for (int i = 0; i < 20; i++) {
            game.roll(0);
        }
        assertEquals(0, game.score());
    }
}
