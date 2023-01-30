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

        if (frames.size() > 0) {
            Frame previousFrame = frames.get(frames.size() - 1);
            int previousFrameRoundTwoOldScore = previousFrame.getScoreRoundTwo();

            if (previousFrame.isStrike())
                previousFrame.setScoreRoundTwo(10 + pins);

            else if (previousFrame.isSpare()) {
                previousFrame.setScoreRoundTwo(previousFrameRoundTwoOldScore + pins);
            }
        }
        frames.add(frame);

        if(pins == 10)
            rollCounter = 0;


    }


    private void prepareRoundTwoResult(int pins) {
        int correctIndex = frames.size() - 1;
        Frame previousFrame;
        boolean previousFrameWasStrike = false;

        if (frames.size() > 1) {
            previousFrame = frames.get(frames.size()-2);
            previousFrameWasStrike = previousFrame.isStrike();

        }

        if (frames.size() > 1 && frames.get(correctIndex-1).isStrike())
            frames.get(correctIndex-1).setScoreRoundOne(10+pins);



        int roundTotalPins = frames.get(correctIndex).getRoundOnePins() + pins;
        boolean isSpare = roundTotalPins == 10;


        frames.get(correctIndex).setRoundTwoPins(pins);
        frames.get(correctIndex).setSpare(isSpare);
        frames.get(correctIndex).setScoreRoundTwo(pins);
    }



//    private boolean previousFrameWasStrikeOrSpare(Frame previousFrame) {
//        boolean previousFrameWasStrike = previousFrame.isStrike();
//        boolean previousFrameWasSpare = previousFrame.isSpare();
//        return previousFrameWasStrike || previousFrameWasSpare;
//
//    }

    public int score() {
        int i = frames.stream().mapToInt(Frame::getScoreRoundTwo).sum()+
                frames.stream().mapToInt(Frame::getScoreRoundOne).sum();

        return i;
    }


}
