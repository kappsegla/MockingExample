package com.example.bowling;

public class Game {

    private int score;
    private int frames;
    private static final int MAXIMUM_FRAMES = 10;
    private int actualRolls;
    private int expectedRolls;
    private int currentFrameScore;
    private int maximumFrameScore;
    private boolean bonus;
    private int actualBonusCount;
    private int maximumBonusCount;

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

        resetBonus();

        if(isStrike(value)) {
            bonus = true;
            maximumBonusCount = 2;
            score += value;
            frames++;
        } else {
            calculateScore(value);
            nextRoll();
        }
    }

    private void resetBonus() {
        if(bonus && actualBonusCount >= maximumBonusCount) {
            bonus = false;
            actualBonusCount = 0;
            maximumBonusCount = 0;
        }
    }

    private boolean isStrike(int value) {
        return actualRolls == 0 && value == 10;
    }

    private void calculateScore(int value) {
//        if(currentFrameScore < maximumFrameScore) {
//            // strike handled further up
//        }
        if((value + currentFrameScore) < maximumFrameScore) {
            score += bonus ? 2 * value : value;
            currentFrameScore += value;
        } else {
            int difference = maximumFrameScore - currentFrameScore;
            score += bonus ? 2 * value : difference;
            currentFrameScore += difference;
        }


    }

    private void nextRoll() {
        actualRolls++;

        if(actualRolls == expectedRolls) {
            frames++;
            actualRolls = 0;
            currentFrameScore = 0;
        }

        if(bonus)
            actualBonusCount++;
    }

    private boolean rollIsDisallowed() {
        return frames >= MAXIMUM_FRAMES || actualRolls >= expectedRolls;
    }

}
