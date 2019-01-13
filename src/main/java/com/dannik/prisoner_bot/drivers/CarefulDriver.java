package com.dannik.prisoner_bot.drivers;

import com.dannik.prisoner_bot.Player;

public class CarefulDriver extends Player {

    public CarefulDriver(String name) {
        super(name);
    }

    @Override
    public int ask() {
        return 0;
    }

    @Override
    public String getStrategyName() {
        return "Careful";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {

    }
}
