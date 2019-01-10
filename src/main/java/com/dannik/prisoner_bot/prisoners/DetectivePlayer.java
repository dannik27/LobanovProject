package com.dannik.prisoner_bot.prisoners;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.PlayerUtils;

public class DetectivePlayer extends Player {

    public DetectivePlayer(String name) {
        super(name);
    }

    private int stepCounter;
    private int lastEnemyChoice;

    private boolean enemyIsCooperator = true;

    public int ask() {
        if (stepCounter == 1 || stepCounter == 3 || stepCounter == 4){
            return PlayerUtils.bestScoreForBoth(currentMatrix);
        } else if (stepCounter == 2) {
            return PlayerUtils.bestScore(currentMatrix);
        } else {

            if (enemyIsCooperator) {
                return PlayerUtils.bestScore(currentMatrix);
            } else {
                return lastEnemyChoice;
            }

        }
    }

    @Override
    public String getStrategyName() {
        return "Detective";
    }

    @Override
    public void addGameResult(Player enemy, int answer) {
        if (stepCounter <= 4 && answer == PlayerUtils.bestScore(currentMatrix)) { // Если противник обманул
            enemyIsCooperator = false;
        }
        lastEnemyChoice = answer;
        stepCounter += 1;
    }

    @Override
    public void setEnemy(Player enemy) {
        stepCounter = 1;
        enemyIsCooperator = true;
    }
}
