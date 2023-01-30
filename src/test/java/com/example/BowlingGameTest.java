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
        assertEquals(15, frames.get(0).getTotalScore());
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
        game.roll(10);
        game.roll(1);
        assertEquals(2,frames.size());

    }

    @Test
    void oneStrikeThenToFivesThenRestMiss(){
        game.roll(10);
        game.roll(5);
        game.roll(5);
        rollMany(16, 0);
       assertEquals(30, game.score());
    }

    @Test
    void twoStrikesThanFiveThanZeroThanFiveRestZero(){
        game.roll(10);
        game.roll(10);
        game.roll(5);
        game.roll(0);
        System.out.println(frames.get(0));
        System.out.println(frames.get(1));
        System.out.println(frames.get(2));
        rollMany(13, 0);
        assertEquals(45, game.score());
    }


    @Test
    public void testTenStrikes(){
        rollMany(10,10);
        assertEquals(270, game.score());
    }

//    @Test
//    public void PerfectGame(){
//        rollMany(12,10);
//        assertEquals(300, game.score());
//    }

    @Test
    void
    oneStrikeShouldMarkFrameAsStrike(){
        game.roll(10);
        rollMany(16, 0);
        assertEquals(true, frames.get(0).isStrike());
    }

    @Test
    void
    threeStrikesThanSpareRestZero(){
        game.roll(10);
        game.roll(10);
        game.roll(10);
        game.roll(5);
        game.roll(5);
        System.out.println(frames.get(0));
        System.out.println(frames.get(1));
        System.out.println(frames.get(2));
        System.out.println(frames.get(3));

        rollMany(12, 0);
        assertEquals(85, game.score());
    }


    private void rollMany(int amountOfRolls, int pins) {
        for (int i = 0; i < amountOfRolls; i++)
            game.roll(pins);
    }



}