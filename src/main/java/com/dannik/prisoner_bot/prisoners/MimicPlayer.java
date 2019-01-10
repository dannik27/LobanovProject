package com.dannik.prisoner_bot.prisoners;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class MimicPlayer extends Player {

    public MimicPlayer(String name) {
        super(name);
    }

    private int lastEnemyChoice;


    public int ask() {
        if(lastEnemyChoice == -1) {
            return PlayerUtils.bestScoreForBoth(currentMatrix);
        } else {
            return lastEnemyChoice;
        }
    }

    @Override
    public String getStrategyName() {
        return "Mimic";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {
        lastEnemyChoice = answer;
    }

    @Override
    public void setEnemy(Player enemy) {
        lastEnemyChoice = -1;
    }
}
