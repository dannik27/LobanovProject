package com.dannik.prisoner_bot.drivers;

import com.dannik.prisoner_bot.Player;

public class FiftyFiftyDriver extends Player {

    public FiftyFiftyDriver(String name) {
        super(name);
    }

    private int stepCounter = 0;

    @Override
    public int ask() {

        stepCounter += 1;

        return stepCounter % 2;
    }

    @Override
    public String getStrategyName() {
        return "FiftyFifty";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {
        stepCounter = 0;
    }
}
