package com.dannik.prisoner_bot.prisoners.adv;

import com.dannik.prisoner_bot.Matrix;
import com.dannik.prisoner_bot.Player;

import java.util.Optional;

/**
 * Created by nikita.sokeran@gmail.com
 */
public class AdvancedPlayer extends Player {
    private Player strategy;
    private EnemyStepsAnalyzer analyzer;
    private double terminationProbability;

    public AdvancedPlayer(final String name) {
        super(name);
    }

    public void setTerminationProbability(final double terminationProbability) {
        this.terminationProbability = terminationProbability;
        reset();
    }

    @Override
    public int ask() {
        return strategy.ask();
    }

    @Override
    public void setMatrix(final Matrix matrix) {
        super.setMatrix(matrix);
        strategy.setMatrix(matrix);
    }

    @Override
    public String getStrategyName() {
        return "Advanced strategy wrapping '" + strategy.getStrategyName() + '\'';
    }

    @Override
    public void addGameResult(final Player enemy, final int answer) {
        final Optional<Player> optionalNewStrategy = getAnalyzerChecked().addEnemyMove(answer);
        optionalNewStrategy.ifPresent(newStrategy -> {
            strategy = newStrategy;
            strategy.setMatrix(currentMatrix);
        });
        strategy.addGameResult(enemy, answer);
    }

    @Override
    public void setEnemy(final Player enemy) {
        reset();
    }

    private void reset() {
        this.analyzer = new EnemyStepsAnalyzer(getName(), terminationProbability);
        this.strategy = analyzer.getDefaultStrategy();
        this.strategy.setMatrix(currentMatrix);
    }

    private EnemyStepsAnalyzer getAnalyzerChecked() {
        if (analyzer == null) {
            throw new IllegalStateException("Analyzer must be initialized.");
        }
        return analyzer;
    }
}
