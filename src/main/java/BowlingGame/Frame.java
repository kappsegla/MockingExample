package BowlingGame;

public class Frame {


    private int scoreRoundOne;

    public int getScoreRoundOne() {
        return scoreRoundOne;
    }

    public void setScoreRoundOne(int scoreRoundOne) {
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
