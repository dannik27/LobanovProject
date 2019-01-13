package com.dannik.prisoner_bot.prisoners.simple;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class GreedyPlayer extends Player {


    public GreedyPlayer(String name) {
        super(name);
    }

    public int ask() {
        return PlayerUtils.bestScore(currentMatrix);
    }

    @Override
    public String getStrategyName() {
        return "Greedy";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {

    }


}
