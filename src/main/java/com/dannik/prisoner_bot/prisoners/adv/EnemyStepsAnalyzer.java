package com.dannik.prisoner_bot.prisoners.adv;

import com.dannik.prisoner_bot.Player;
import com.dannik.prisoner_bot.prisoners.simple.CooperativePlayer;
import com.dannik.prisoner_bot.prisoners.simple.MimicPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Created by nikita.sokeran@gmail.com
 */
public class EnemyStepsAnalyzer {
    private static final int STEPS_TO_IDENTIFY = 10;

    private final Random random = new Random();
    private final String defaultStrategyName;
    private final double terminationProbability;
    private final List<Integer> enemiesMoves = new ArrayList<>();

    public EnemyStepsAnalyzer(final String defaultStrategyName, final double terminationProbability) {
        this.defaultStrategyName = defaultStrategyName;
        this.terminationProbability = terminationProbability;
    }

    public Player getDefaultStrategy() {
        return new CooperativePlayer(defaultStrategyName);
    }

    private Optional<Player> analyzeEnemyMoves(final int previousMove) {
        if (enemiesMoves.size() <= STEPS_TO_IDENTIFY) {
            // Add until we get 'full pack' of moves, prevent from extensive memory usage
            // last one will exceed our 'pack size' and won't be longer used
            enemiesMoves.add(previousMove);
        }

        if (enemiesMoves.size() == STEPS_TO_IDENTIFY) {
            final long badMovesCount = enemiesMoves.stream().filter(move -> move == 1).count();
            if (badMovesCount > 2) {
//                return Optional.of(new GreedyPlayer(defaultStrategyName));
                return Optional.of(new MimicPlayer(defaultStrategyName));
//                return Optional.of(new SimpleNeshPlayer(defaultStrategyName));
            } else {
                return Optional.of(new MimicPlayer(defaultStrategyName));
            }
        }

        return Optional.empty();
    }

    public Optional<Player> addEnemyMove(final int previousMove) {
        final Optional<Player> firstMovesPlayer = analyzeEnemyMoves(previousMove);

        if (firstMovesPlayer.isPresent()) {
            return firstMovesPlayer;
        }

//        if (enemiesMoves.size() > STEPS_TO_IDENTIFY) {
//            return random.nextDouble() <= terminationProbability
//                    ? Optional.of(new GreedyPlayer(defaultStrategyName))
//                    : Optional.empty();
//        }

        return Optional.empty();
    }
}
