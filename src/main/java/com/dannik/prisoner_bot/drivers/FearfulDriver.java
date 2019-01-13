package com.dannik.prisoner_bot.drivers;

import com.dannik.prisoner_bot.Player;

public class FearfulDriver extends Player {

    public FearfulDriver(String name) {
        super(name);
    }

    private int stepCounter = 0;
    private boolean wasScared;

    @Override
    public int ask() {

        stepCounter += 1;

        if (stepCounter < 10)
            return 0;

        if (stepCounter == 11)
            return 1;

        return wasScared ? 0 : 1;
    }

    @Override
    public String getStrategyName() {
        return "Fearful";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {
        if (answer == 1)
            wasScared = true;
    }

    @Override
    public void setEnemy(Player enemy) {
        wasScared = false;
        stepCounter = 0;
    }
}
