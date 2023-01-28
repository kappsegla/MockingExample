package com.example;


import BowlingGame.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    Game game = new Game();

    @Test
    public void fullGameOffMissedRolls(){
        rollMany(20,0);
        assertEquals(0, game.score());
    }
    @Test
    public void fullGameOffOnes(){
        rollMany(20,1);
        assertEquals(20, game.score());

    }
    @Test
    public void oneSpareThenFiveThenRestMiss(){
        game.roll(5);
        game.roll(5);
        game.roll(5);
        rollMany(17, 0);
        assertEquals(20,game.score());


    }

    private void rollMany(int amountOfRolls, int pins) {
        for (int i = 0; i < amountOfRolls; i++)
            game.roll(pins);
    }

}
