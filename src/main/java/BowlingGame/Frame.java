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
                ", extraRoll=" + extraRoll +
                ", roundTwoPins=" + roundTwoPins +
                '}';
    }

    private boolean isSpare;
    private boolean isStrike;
    private int scoreRoundOne;
    private int scoreRoundTwo;
    private int roundOnePins;
    private int extraRoll;
    private int roundTwoPins;
    private int roundPins;


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



    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }



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



    public int getTotalScore() {
        return scoreRoundOne+scoreRoundTwo+extraRoll;
    }


    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(boolean strike) {
        isStrike = strike;
    }

    public int getExtraRoll() {
        return extraRoll;
    }

    public void setExtraRoll(int extraRoll) {
        this.extraRoll = extraRoll;
    }




}
