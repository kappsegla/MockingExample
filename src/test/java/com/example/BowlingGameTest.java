package com.example;


import BowlingGame.Frame;
import BowlingGame.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BowlingGameTest {

    List<Frame> frames;
    Game game = new Game(frames = new ArrayList<>());



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

    @Test
    void fullGameWithTwoSparesThenFiveThenRestMiss() {
        game.roll(5);
        game.roll(5);
        game.roll(5);
        game.roll(5);
        game.roll(5);
        rollMany(15, 0);
        assertEquals(35, game.score());
    }

    @Test
    void framesShouldHaveCorrectSizeAfterStrikeAndOneExtraRoll(){
        //Second roll should add and create one extra frame.
        game.roll(10);
        game.roll(1);
        frames.size();
        assertEquals(2,frames.size());

    }

    @Test
    void OneStrikeThenToFivesThenRestMiss(){
        game.roll(10);
        game.roll(5);
        game.roll(5);
        rollMany(16, 0);
        assertEquals(30, game.score());

    }


    private void rollMany(int amountOfRolls, int pins) {
        for (int i = 0; i < amountOfRolls; i++)
            game.roll(pins);
    }
}