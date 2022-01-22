package com.example.bowling;

public class Game {

    /**
     * A strike occurs when a player knocks down all 10 pins on the first roll.
     * A spare occurs when a player kocks down all 10 pins in 2 rolls (which is in 1 frame).
     */

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
    private boolean strikeBonus;


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

        if(bonusHasBeenCalculated())
            resetBonus();

        calculateScore(value);
    }

    private void calculateScore(int value) {
        if(isStrike(value)) {
            strike(value);
        } else if (isSpare(value)) {
            spare(value);
        } else {
            regularRoll(value);
        }
    }

    private void regularRoll(int value) {
        calculateRegularRoll(value);
        nextRoll();
    }

    private void incrementBonusCount() {
        if(bonus)
            actualBonusCount++;
    }

    private void spare(int value) {
        if(bonusInTenthFrame()) {
            tenthFrameBonus(value);
        } else {
            regularSpare(value);
        }
    }

    private void regularSpare(int value) {
        if (bonus) {
            score += 2 * value;
        } else {
            bonus = true;
            score += value;
            maximumBonusCount = 1;
        }
        nextRoll();
    }

    private void tenthFrameBonus(int value) {
        actualRolls++;
        expectedRolls = 3;
        score += value;
    }

    private boolean bonusInTenthFrame() {
        return frames == MAXIMUM_FRAMES - 1;
    }

    private void strike(int value) {
        if (bonusInTenthFrame()) {
            tenthFrameBonus(value);
        } else {
            regularStrike(value);
        }
    }

    private void regularStrike(int value) {
        if (strikeBonus)
            doubleStrike = true;
        if (bonus) {
            score += 2 * value;
        } else {
            bonus = true;
            score += value;
        }
        strikeBonus = true;
        maximumBonusCount = 2;
        frames++;
    }

    private boolean isSpare(int value) {
        return actualRolls == 1 && currentFrameScore + value == 10;
    }

    private boolean rollIsDisallowed() {
        return frames >= MAXIMUM_FRAMES || actualRolls >= expectedRolls;
    }

    private boolean bonusHasBeenCalculated() {
        return bonus && actualBonusCount >= maximumBonusCount;
    }

    private void resetBonus() {
        bonus = false;
        strikeBonus = false;
        actualBonusCount = 0;
        maximumBonusCount = 0;
    }

    private boolean isStrike(int value) {
        return actualRolls == 0 && value == 10;
    }

    private void calculateRegularRoll(int value) {
        if((value + currentFrameScore) < maximumFrameScore) {
            calculateDoubleStrike(value);
            score += bonus ? 2 * value : value;
            currentFrameScore += value;
        } else {
            int difference = maximumFrameScore - currentFrameScore;
            score += bonus ? 2 * value : difference;
            currentFrameScore += difference;
        }
        incrementBonusCount();
    }

    private void nextRoll() {
        actualRolls++;

        if(actualRolls == expectedRolls) {
            frames++;
            actualRolls = 0;
            currentFrameScore = 0;
        }
    }

    private void calculateDoubleStrike(int value) {
        if(doubleStrike) {
            score += value;
            doubleStrike = false;
        }
    }

}
