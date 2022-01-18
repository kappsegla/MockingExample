package com.example.bowling;

public class Game {

    private int score;
    private int rolls;

    public int score() {
        return score;
    }

    public void roll(int value) {
        if(rolls < 20) {
            score += value;
            rolls++;
        }
    }

}
