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
    private boolean doubleStrike;

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
            if(bonus) {
                doubleStrike = true;
                score += 2 * value;
                maximumBonusCount = 3;
            } else {
                bonus = true;
                score += value;
                maximumBonusCount = 2;
            }
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
            handleDoubleStrike(value);
            score += bonus ? 2 * value : value;
            currentFrameScore += value;
        } else {
            int difference = maximumFrameScore - currentFrameScore;
            score += bonus ? 2 * value : difference;
            currentFrameScore += difference;
        }

    }

    private void handleDoubleStrike(int value) {
        if(doubleStrike) {
            score += value;
            doubleStrike = false;
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
