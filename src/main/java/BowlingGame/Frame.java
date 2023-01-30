package BowlingGame;

public class Frame {

    @Override
    public String toString() {
        return "Frame{" +
                "isSpare=" + isSpare +
                ", isStrike=" + isStrike +
                ", scoreRoundOne=" + scoreRoundOne +
                ", scoreRoundTwo=" + scoreRoundTwo +
                ", roundOnePins=" + roundOnePins +
                ", roundTwoPins=" + roundTwoPins +
                '}';
    }

    private boolean isSpare;
    private boolean isStrike;
    private int scoreRoundOne;
    private int scoreRoundTwo;
    private int roundOnePins;

    public void setScoreRoundOne(int scoreRoundOne) {
        this.scoreRoundOne = scoreRoundOne;
    }

    public int getRoundOnePins() {
        return roundOnePins;
    }

    public void setRoundOnePins(int roundOnePins) {
        this.roundOnePins = roundOnePins;
    }

    public int getRoundTwoPins() {
        return roundTwoPins;
    }

    public void setRoundTwoPins(int roundTwoPins) {
        this.roundTwoPins = roundTwoPins;
    }

    private int roundTwoPins;

    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }



    public int getScoreRoundOne() {
        return scoreRoundOne;
    }

    public void setScoreRoundOne(int scoreRoundOne, boolean previousFrameWasSpareOrStrike) {

        if (previousFrameWasSpareOrStrike)
            this.scoreRoundOne = scoreRoundOne*2;
        else
            this.scoreRoundOne = scoreRoundOne;

    }

    public int getScoreRoundTwo() {
        return scoreRoundTwo;
    }

    public void setScoreRoundTwo(int scoreRoundTwo, boolean previousFrameWasStrike) {
        if (previousFrameWasStrike)
            this.scoreRoundTwo = scoreRoundTwo*2;
        else
            this.scoreRoundTwo = scoreRoundTwo;
    }



    public int getTotalScore() {
        return scoreRoundOne+scoreRoundTwo;
    }


    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(boolean strike) {
        isStrike = strike;
    }
}
