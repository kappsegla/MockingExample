package BowlingGame;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int rollCounter;

    public Game(List<Frame> frames) {
        this.frames = frames;
    }

    List<Frame> frames;

    public void roll(int pins) {
        rollCounter++;
        if (rollCounter % 2 == 1) {
            prepareRoundOneResult(pins);
        }
        else if (rollCounter/2-1 < frames.size()) {
            prepareRoundTwoResult(pins);

        }

        if(rollCounter > 20) {
            throw new IllegalArgumentException("Exceeded the maximum number of rolls in a game of bowling.");
        }
    }

    private void prepareRoundOneResult(int pins) {


        Frame frame = new Frame();
        frame.setStrike(pins == 10);
        frame.setRoundOnePins(pins);

        if(frames.size() > 0) {

            Frame previousFrame = frames.get(frames.size()-1);
            boolean previousFrameWasStrikeOrSpare = previousFrameWasStrikeOrSpare(previousFrame);
            frame.setScoreRoundOne(pins,previousFrameWasStrikeOrSpare);
            frames.add(frame);
        }
        else {
            frame.setRoundOnePins(pins);
            frame.setScoreRoundOne(pins,false);
            frames.add(frame);
        }
        if(pins == 10)
            rollCounter++;

    }


    private void prepareRoundTwoResult(int pins) {
        int correctIndex = frames.size() - 1;
        Frame previousFrame;
        boolean previousFrameWasStrike = false;

        if (frames.size() > 1) {
            previousFrame = frames.get(frames.size()-2);
            previousFrameWasStrike = previousFrame.isStrike();

        }

        int roundTotalPins = frames.get(correctIndex).getRoundOnePins() + pins;
        boolean isSpare = roundTotalPins >= 10;


        frames.get(correctIndex).setRoundTwoPins(pins);
        frames.get(correctIndex).setSpare(isSpare);
        frames.get(correctIndex).setScoreRoundTwo(pins, previousFrameWasStrike);
    }



    private boolean previousFrameWasStrikeOrSpare(Frame previousFrame) {
        boolean previousFrameWasStrike = previousFrame.isStrike();
        boolean previousFrameWasSpare = previousFrame.isSpare();
        return previousFrameWasStrike || previousFrameWasSpare;

    }

    public int score() {
        return frames.stream().mapToInt(Frame::getTotalScore).sum();
    }


}
