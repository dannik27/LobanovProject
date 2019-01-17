package com.dannik.prisoner_bot.contributors;

public abstract class Contributor {

  private double contribution;
  private double[] income;
  private double termination_probability;
  private int hand;

  private final String name;

  public Contributor(String name) {
    this.name = name;
  }


  public void gameStarted(double contribution, double[] income, double termination_probability, int hand){
    this.contribution = contribution;
    this.income = income;
    this.termination_probability = termination_probability;
    this.hand = hand;
  }

  public abstract String getStrategyName();
  abstract public int ask();

  abstract public void roundEnd(int[] result);


  public String getName() {
    return name;
  }
}
