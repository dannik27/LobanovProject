package com.dannik.prisoner_bot;

public abstract class Player {

    final private String name;

    protected Matrix currentMatrix;
    protected int hand;
    protected Player currentEnemy;

    public abstract int ask();
    public abstract String getStrategyName();
    public abstract void addGameResult(Player enemy, int answer);
    public abstract void setEnemy(Player enemy);

    public Player(String name){
        this.name = name;
    }

    String getName() {
        return name;
    }

    public void setMatrix(Matrix matrix) {
        this.currentMatrix = matrix;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public int getHand() {
        return hand;
    }

    public Matrix getMatrix() {
        return currentMatrix;
    }
}
