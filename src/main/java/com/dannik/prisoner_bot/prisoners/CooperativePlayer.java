package com.dannik.prisoner_bot.prisoners;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class CooperativePlayer extends Player {

    public CooperativePlayer(String name) {
        super(name);
    }

    public int ask() {
        return PlayerUtils.bestScoreForBoth(currentMatrix);
    }

    @Override
    public String getStrategyName() {
        return "Cooperator";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {

    }

    @Override
    public void setEnemy(Player enemy) {

    }
}
