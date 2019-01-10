package com.dannik.prisoner_bot;

import com.dannik.prisoner_bot.prisoners.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        new App();
    }

    App(){

        RemoteGameSession remoteGameSession = new RemoteGameSession();
        remoteGameSession.setUri("ws://dmc.alepoydes.com:3012");
        remoteGameSession.setPrefix("----------");
        remoteGameSession.setPlayer(GreedyPlayer.class);
        remoteGameSession.start();

        RemoteGameSession remoteGameSession2 = new RemoteGameSession();
        remoteGameSession2.setUri("ws://dmc.alepoydes.com:3012");
        remoteGameSession2.setPrefix("||||||||||");
        remoteGameSession2.setPlayer(NaivePlayer.class);
        remoteGameSession2.start();

        RemoteGameSession remoteGameSession3 = new RemoteGameSession();
        remoteGameSession3.setUri("ws://dmc.alepoydes.com:3012");
        remoteGameSession3.setPrefix("++++++++++");
        remoteGameSession3.setPlayer(ShiftyPlayer.class);
        remoteGameSession3.start();


        try {
            TimeUnit.SECONDS.sleep(60);
            remoteGameSession.stop();
            remoteGameSession2.stop();
            remoteGameSession3.stop();
        } catch (InterruptedException e) {

        }
//        List<Player> players = new ArrayList<>();
//        players.add(new GreedyPlayer("Ivan"));
//        players.add(new GreedyPlayer("Semyon"));
//        players.add(new CooperativePlayer("Petr"));
//        players.add(new CooperativePlayer("Vasya"));
//        players.add(new SimpleNeshPlayer("Sasha"));
//        players.add(new SimpleNeshPlayer("Olya"));
//        players.add(new SmartCooperativePlayer("Katya"));
//        players.add(new SmartCooperativePlayer("Roma"));
//        players.add(new MimicPlayer("Nikolay"));
//        players.add(new MimicPlayer("Maria"));
//        players.add(new ShiftyPlayer("Fedor"));
//        players.add(new ShiftyPlayer("Boris"));
//        players.add(new DetectivePlayer("Mr. Petrov"));
//        players.add(new DetectivePlayer("Tolya"));
//
//        for(Player player : players){
//            System.out.println(player.getName() + " is " + player.getStrategyName());
//        }
//
//
//        Matrix randomMatrix = createPrisonersMatrix();
//
//        printMatrix(randomMatrix);
//
//        Map<Player, Integer> totalResult = new HashMap<>();
//
//        for(Player p1 : players){
//            for (Player p2: players){
//
//                if(p1 == p2) continue;
//
//                GameSession gameSession = new GameSession();
//                gameSession.setMatrix(randomMatrix);
//                gameSession.setP1(p1);
//                gameSession.setP2(p2);
//                gameSession.setGamesCount(100);
//
//                Map<Player, Integer> result = gameSession.start();
//
//                result.forEach((prisoner, integer) -> {
//                    totalResult.put(prisoner, totalResult.getOrDefault(prisoner, 0) + integer);
//                });
//
//            }
//        }
//
//        System.out.println(" Results: ");
//
//        totalResult.forEach((prisoner, integer) -> {
//            System.out.println(prisoner.getName() + " the " + prisoner.getStrategyName() + " : " + totalResult.get(prisoner));
//        });


    }



}
