package com.dannik.prisoner_bot;

import com.dannik.prisoner_bot.drivers.AggresiveDriver;
import com.dannik.prisoner_bot.drivers.CarefulDriver;
import com.dannik.prisoner_bot.drivers.FearfulDriver;
import com.dannik.prisoner_bot.drivers.FiftyFiftyDriver;
import com.dannik.prisoner_bot.drivers.RevengefulDriver;
import com.dannik.prisoner_bot.prisoners.adv.AdvancedPlayer;
import com.dannik.prisoner_bot.prisoners.simple.CooperativePlayer;
import com.dannik.prisoner_bot.prisoners.simple.DetectivePlayer;
import com.dannik.prisoner_bot.prisoners.simple.GreedyPlayer;
import com.dannik.prisoner_bot.prisoners.simple.MimicPlayer;
import com.dannik.prisoner_bot.prisoners.simple.ShiftyPlayer;
import com.dannik.prisoner_bot.prisoners.simple.SimpleNeshPlayer;
import com.dannik.prisoner_bot.prisoners.simple.SmartCooperativePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final double TERMINATION_PROBABILITY = 0.001;

    public static void main(String[] args) {
        new App();
    }

    App(){

//        RemoteGameSession remoteGameSession = new RemoteGameSession();
//        remoteGameSession.setUri("ws://dmc.alepoydes.com:3012");
//        remoteGameSession.setPrefix("----------");
//        remoteGameSession.setPlayer(GreedyPlayer.class);
//        remoteGameSession.start();
//
//
//        try {
//            TimeUnit.SECONDS.sleep(60);
//            remoteGameSession.stop();
//            remoteGameSession2.stop();
//            remoteGameSession3.stop();
//        } catch (InterruptedException e) {
//
//        }

        laba2Simulation();

    }


    private void laba1Simulation() {
        final AdvancedPlayer mainPlayer = new AdvancedPlayer("Main");
        final AdvancedPlayer mainPlayer2 = new AdvancedPlayer("Main");
        final AdvancedPlayer mainPlayer3 = new AdvancedPlayer("Main");
        mainPlayer.setTerminationProbability(TERMINATION_PROBABILITY);
        mainPlayer2.setTerminationProbability(TERMINATION_PROBABILITY);
        mainPlayer3.setTerminationProbability(TERMINATION_PROBABILITY);

        List<Player> players = new ArrayList<>();
        players.add(new GreedyPlayer("Ivan"));
        players.add(new GreedyPlayer("Semyon"));
        players.add(new CooperativePlayer("Petr"));
        players.add(new CooperativePlayer("Vasya"));
        players.add(new SimpleNeshPlayer("Sasha"));
        players.add(new SimpleNeshPlayer("Olya"));
        players.add(new SmartCooperativePlayer("Katya"));
        players.add(new SmartCooperativePlayer("Roma"));
        players.add(new MimicPlayer("Nikolay"));
        players.add(new MimicPlayer("Maria"));
        players.add(new ShiftyPlayer("Fedor"));
        players.add(new ShiftyPlayer("Boris"));
        players.add(new DetectivePlayer("Mr. Petrov"));
        players.add(new DetectivePlayer("Tolya"));

        startSimulation(players, MatrixUtils.createPrisonersMatrix());
    }

    private void laba2Simulation() {
        List<Player> players = new ArrayList<>();
        players.add(new CarefulDriver("Petya"));
        players.add(new CarefulDriver("Kolya"));
        players.add(new AggresiveDriver("Tolya"));
        players.add(new AggresiveDriver("Sasha"));
        players.add(new RevengefulDriver("Fedya"));
        players.add(new RevengefulDriver("Masha"));
        players.add(new FearfulDriver("Tamara"));
        players.add(new FearfulDriver("Grigory"));
        players.add(new FiftyFiftyDriver("ff1"));
        players.add(new FiftyFiftyDriver("ff2"));

        startSimulation(players, MatrixUtils.createChickenMatrix());
    }


    private void startSimulation(List<Player> players, Matrix matrix) {

        for(Player player : players){
            System.out.println(player.getName() + " is " + player.getStrategyName());
        }

        MatrixUtils.printMatrix(matrix);

        Map<Player, Double> totalResult = new HashMap<>();

        for(Player p1 : players){
            for (Player p2: players){

                if(p1 == p2) continue;

                GameSession gameSession = new GameSession();
                gameSession.setMatrix(matrix);
                gameSession.setP1(p1);
                gameSession.setP2(p2);
                gameSession.setTerminationProbability(TERMINATION_PROBABILITY);

                Map<Player, Double> result = gameSession.start();

                result.forEach((prisoner, integer) -> {
                    totalResult.put(prisoner, totalResult.getOrDefault(prisoner, (double) 0) + integer);
                });

            }
        }

        System.out.println(" Results: ");

        totalResult.forEach((prisoner, integer) -> {
            System.out.println(prisoner.getName() + " the " + prisoner.getStrategyName() + " : " + totalResult.get(prisoner));
        });
    }
}
