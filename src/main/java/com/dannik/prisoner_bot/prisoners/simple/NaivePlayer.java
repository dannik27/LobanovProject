package com.dannik.prisoner_bot.prisoners.simple;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class NaivePlayer extends Player {

    public NaivePlayer(String name) {
        super(name);
    }

    public int ask() {
        return PlayerUtils.bestScore(currentMatrix) == 1 ? 0 : 1;
    }

    @Override
    public String getStrategyName() {
        return "Naive";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {

    }
}
