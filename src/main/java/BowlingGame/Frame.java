package BowlingGame;

public class Frame {


    private boolean isSpare;

    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }

    private int scoreRoundOne;

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

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    private int scoreRoundTwo;
    private int totalScore;

    public int getTotalScore() {
        return scoreRoundOne+scoreRoundTwo;
    }




}
