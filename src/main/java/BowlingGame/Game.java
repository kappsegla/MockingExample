package BowlingGame;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private int rollCounter;

    private final List<Frame> frames = new ArrayList<>();

    public void roll(int pins) {
        rollCounter++;
        if (rollCounter % 2 == 1) {
            Frame frame = new Frame();
            frame.setScoreRoundOne(pins);
            frames.add(frame);
        }
        else if (rollCounter/2-1 < frames.size())
            frames.get(rollCounter / 2 - 1).setScoreRoundTwo(pins);



        if(rollCounter > 20) {
            throw new IllegalArgumentException("Exceeded the maximum number of rolls in a game of bowling.");
        }
    }

    public int score() {
        return frames.stream().mapToInt(Frame::getTotalScore).sum();
    }
}
