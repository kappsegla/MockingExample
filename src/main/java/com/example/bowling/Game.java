package com.example.bowling;

public class Game {

    private int score;
    private int frames;
    private final int MAXIMUM_FRAMES = 10;
    private int actualRolls;
    private int expectedRolls;

    public Game() {
        expectedRolls = 2;
    }

    public int score() {
        return score;
    }

    public void roll(int value) {
        if(frames >= MAXIMUM_FRAMES || actualRolls >= expectedRolls)
           return;

        score += value;
        actualRolls++;

        if(actualRolls == expectedRolls) {
            frames++;
            actualRolls = 0;
        }
    }

}
