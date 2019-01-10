package com.dannik.prisoner_bot.prisoners;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class SimpleNeshPlayer extends Player {


    public SimpleNeshPlayer(String name) {
        super(name);
    }

    public int ask() {
        return PlayerUtils.simpleNesh(currentMatrix);
    }

    @Override
    public String getStrategyName() {
        return "Simple Nesh user";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {

    }


}
