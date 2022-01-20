package com.example.bowling;

public class Game {

    private int score;
    private int frames;
    private static final int MAXIMUM_FRAMES = 10;
    private int actualRolls;
    private int expectedRolls;
    private int currentFrameScore;
    private int maximumFrameScore;

    public Game() {
        expectedRolls = 2;
        maximumFrameScore = 10;
    }

    public int score() {
        return score;
    }

    public void roll(int value) {
        if(rollIsDisallowed())
           return;

        if(isStrike(value)) {
            score += value;
            frames++;
        } else {
            calculateScore(value);
            actualRolls++;
            startNextFrame();
        }
    }

    private boolean isStrike(int value) {
        return actualRolls == 0 && value == 10;
    }

    private void calculateScore(int value) {
        if(currentFrameScore < maximumFrameScore) {
            if((value + currentFrameScore) < maximumFrameScore) {
                score += value;
                currentFrameScore += value;
            } else {
                int difference = maximumFrameScore - currentFrameScore;
                score += difference;
                currentFrameScore += difference;
            }
        }
    }

    private void startNextFrame() {
        if(actualRolls == expectedRolls) {
            frames++;
            actualRolls = 0;
            currentFrameScore = 0;
        }
    }

    private boolean rollIsDisallowed() {
        return frames >= MAXIMUM_FRAMES || actualRolls >= expectedRolls;
    }

}
