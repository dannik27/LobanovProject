package com.dannik.prisoner_bot.drivers;

import com.dannik.prisoner_bot.Player;

public class RevengefulDriver extends Player {

    public RevengefulDriver(String name) {
        super(name);
    }

    private boolean wasDeceived;

    @Override
    public int ask() {
        return wasDeceived ? 1 : 0;
    }

    @Override
    public String getStrategyName() {
        return "Revengeful";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {
        if (answer == 1) {
            wasDeceived = true;
        }
    }

    @Override
    public void setEnemy(Player enemy) {
        wasDeceived = false;
    }
}
