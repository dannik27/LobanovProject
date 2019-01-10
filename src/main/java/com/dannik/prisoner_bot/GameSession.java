package com.dannik.prisoner_bot;

import java.util.HashMap;
import java.util.Map;

public class GameSession {

    private Player p1;
    private Player p2;
    private Matrix matrix;
    private int gamesCount;

    private Map<Player, Double> result;

    public Map<Player, Double> start(){
        result = new HashMap<>();

        System.out.println("------------------");
        System.out.println("New game has began");
        System.out.println(p1.getName() + " the " + p1.getStrategyName() +
                " VS " + p2.getName() + " the " + p2.getStrategyName());

        p1.setMatrix(matrix);
        p2.setMatrix(matrix);

        p1.setEnemy(p2);
        p2.setEnemy(p1);

        for(int i = 0; i < gamesCount; i++){
            playGame();
        }

        System.out.println("Player " + p1.getName() + " has got " + result.get(p1));
        System.out.println("Player " + p2.getName() + " has got " + result.get(p2));
        System.out.println("------------------");

        return result;
    }

    private void playGame(){

        int p1Answer = p1.ask();
        int p2Answer = p2.ask();

        result.put(p1, result.getOrDefault(p1, 0.0) + matrix.get(p1Answer, p2Answer));
        result.put(p2, result.getOrDefault(p2, 0.0) + matrix.get(p2Answer, p1Answer));

        p1.addGameResult(p2, p2Answer);
        p2.addGameResult(p1, p1Answer);

    }


    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public void setGamesCount(int gamesCount) {
        this.gamesCount = gamesCount;
    }
}
