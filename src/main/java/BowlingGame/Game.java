package BowlingGame;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int rollCounter;

    private final List<Frame> frames = new ArrayList<>();

    public void roll(int pins) {
        rollCounter++;
        if (rollCounter % 2 == 1) {
            prepareRoundOneScore(pins);
        }
        else if (rollCounter/2-1 < frames.size())
            frames.get(rollCounter / 2 - 1).setScoreRoundTwo(pins);

        if(rollCounter > 20) {
            throw new IllegalArgumentException("Exceeded the maximum number of rolls in a game of bowling.");
        }
    }

    private void prepareRoundOneScore(int pins) {
        Frame frame = new Frame();

        if(frames.size() > 0) {
            Frame previousFrame = frames.get(frames.size()-1);
            boolean previousFrameWasSpare = previousFrame.getScoreRoundOne() + previousFrame.getScoreRoundTwo() == 10;
            frame.setScoreRoundOne(pins,previousFrameWasSpare);
            frames.add(frame);
        } else {

            frame.setScoreRoundOne(pins,false);
            frames.add(frame);
        }
    }

    public int score() {
        return frames.stream().mapToInt(Frame::getTotalScore).sum();
    }
}
