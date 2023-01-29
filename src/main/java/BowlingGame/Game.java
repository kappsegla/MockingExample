package BowlingGame;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int rollCounter;

    private final List<Frame> frames = new ArrayList<>();

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

    private void prepareRoundTwoResult(int pins) {
        int correctIndex = rollCounter / 2 - 1;
        int roundTotalPins = frames.get(correctIndex).getRoundOnePins()+pins;
        boolean isSpare = roundTotalPins >= 10;

        frames.get(correctIndex).setRoundTwoPins(pins);
        frames.get(correctIndex).setSpare(isSpare);
        frames.get(correctIndex).setScoreRoundTwo(pins);
    }

    private void prepareRoundOneResult(int pins) {
        Frame frame = new Frame();



        if(frames.size() > 0) {
            Frame previousFrame = frames.get(frames.size()-1);
            frame.setRoundOnePins(pins);
            boolean previousFrameWasSpare = previousFrame.isSpare();
            frame.setScoreRoundOne(pins,previousFrameWasSpare);
            frames.add(frame);
        } else {
            frame.setRoundOnePins(pins);
            frame.setScoreRoundOne(pins,false);
            frames.add(frame);
        }
    }

    public int score() {
        return frames.stream().mapToInt(Frame::getTotalScore).sum();
    }
}
