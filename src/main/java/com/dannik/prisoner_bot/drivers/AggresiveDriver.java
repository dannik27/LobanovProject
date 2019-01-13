package com.dannik.prisoner_bot.drivers;

import com.dannik.prisoner_bot.Player;

public class AggresiveDriver extends Player {

    public AggresiveDriver(String name) {
        super(name);
    }

    @Override
    public int ask() {
        return 1;
    }

    @Override
    public String getStrategyName() {
        return "Aggresive";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {

    }
}
