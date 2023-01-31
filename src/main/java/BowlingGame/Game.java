
package BowlingGame;
import java.util.List;

public class Game {

    private int rollCounter;

    public Game(List<Frame> frames) {
        this.frames = frames;
    }

    List<Frame> frames;

    public void roll(int pins) {
        rollCounter++;
        if (rollCounter == 1) {
            prepareRoundOneResult(pins);

        }
        else if (rollCounter == 2) {
            prepareRoundTwoResult(pins);
            rollCounter = 0;
        }

        if(frames.size() > 10) {
            throw new IllegalArgumentException("Exceeded the maximum number of rolls in a game of bowling.");
        }
    }

    private void prepareRoundOneResult(int pins) {


        Frame frame = new Frame();
        frame.setStrike(pins == 10);
        frame.setRoundOnePins(pins);
        frame.setScoreRoundOne(pins);

        if (!bonusShot(pins)) {
            if (frames.size() > 0) {
                Frame previousFrame = frames.get(frames.size() - 1);
                int previousFrameRoundTwoOldScore = previousFrame.getScoreRoundTwo();

                if (previousFrame.isStrike())
                    previousFrame.setScoreRoundOne(10 + pins);

                else if (previousFrame.isSpare()) {
                    previousFrame.setScoreRoundTwo(previousFrameRoundTwoOldScore + pins);
                }
            }

            updateOldFrameWhenTwoStrikesInRow(pins);
            frames.add(frame);

            if (pins == 10 && frames.size() != 10)
                rollCounter = 0;
        }
    }

    private boolean bonusShot(int pins) {

        if (frames.size() == 10)
            if (frames.get(9).isSpare() || frames.get(9).isStrike()) {
                frames.get(9).setExtraRoll(pins);
                return true;
            }

        return false;
    }

    private void updateOldFrameWhenTwoStrikesInRow(int pins) {
        if (frames.size() > 1) {

            Frame previousFrame = frames.get(frames.size() - 1);
            Frame previousSecondFrame = frames.get(frames.size() - 2);


            if (previousSecondFrame.isStrike() && previousFrame.isStrike()) {
                int temp = frames.get(frames.size()-2).getScoreRoundOne();
                frames.get(frames.size() - 2).setScoreRoundOne(temp + pins);
            }
        }
    }


    private void prepareRoundTwoResult(int pins) {
        int correctIndex = frames.size() - 1;
        Frame previousFrame;
        boolean previousFrameWasStrike = false;

        if (frames.size() > 1) {
            previousFrame = frames.get(frames.size()-2);
            previousFrameWasStrike = previousFrame.isStrike();
        }

        if (previousFrameWasStrike)
            frames.get(correctIndex-1).setScoreRoundTwo(pins);



        int roundTotalPins = frames.get(correctIndex).getRoundOnePins() + pins;
        boolean isSpare = roundTotalPins == 10;


        frames.get(correctIndex).setRoundTwoPins(pins);
        frames.get(correctIndex).setSpare(isSpare);
        frames.get(correctIndex).setScoreRoundTwo(pins);
    }


    public int score() {

        return frames.stream().mapToInt(Frame::getTotalScore).sum();
    }
}
