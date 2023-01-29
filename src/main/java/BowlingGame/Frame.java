package BowlingGame;

public class Frame {


    private boolean isSpare;
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

    public void setScoreRoundOne(int scoreRoundOne, boolean previousFrameWasSpare) {

        if (previousFrameWasSpare)
            this.scoreRoundOne = scoreRoundOne*2;
        else
            this.scoreRoundOne = scoreRoundOne;

    }

    public int getScoreRoundTwo() {
        return scoreRoundTwo;
    }

    public void setScoreRoundTwo(int scoreRoundTwo) {
        this.scoreRoundTwo = scoreRoundTwo;
    }



    public int getTotalScore() {
        return scoreRoundOne+scoreRoundTwo;
    }




}
