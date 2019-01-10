package com.dannik.prisoner_bot.prisoners;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class ShiftyPlayer extends Player {

    public ShiftyPlayer(String name) {
        super(name);
    }

    private int stepCounter;


    public int ask() {
        if(stepCounter % 3 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String getStrategyName() {
        return "Shifty";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {
        stepCounter += 1;
    }

    @Override
    public void setEnemy(Player enemy) {
        stepCounter = 1;
    }
}
