package com.dannik.prisoner_bot.prisoners;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class SmartCooperativePlayer extends Player {

    private boolean cooperative = true;
    private Player lastPlayer;

    public SmartCooperativePlayer(String name) {
        super(name);
    }

    public int ask() {
        if(cooperative){
            return PlayerUtils.bestScoreForBoth(currentMatrix);
        } else {
            return PlayerUtils.bestScore(currentMatrix);
        }
    }

    @Override
    public String getStrategyName() {
        return "Smart cooperator";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {
        if(cooperative && answer != PlayerUtils.bestScoreForBoth(currentMatrix)){
            cooperative = false;
        }
    }

    @Override
    public void setEnemy(Player enemy) {
        if(lastPlayer == null || lastPlayer != enemy){
            lastPlayer = enemy;
            cooperative = true;
        }
    }
}
