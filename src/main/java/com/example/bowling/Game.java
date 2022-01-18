package com.example.bowling;

public class Game {

    private int score;

    public int score() {
        return score;
    }

    public void roll(int value) {
        score += value;
    }

}
