package com.dannik.prisoner_bot.contributors;

public class GreedyContributor extends Contributor {


  public GreedyContributor(String name) {
    super(name);
  }

  @Override
  public String getStrategyName() {
    return "Greedy contributor";
  }

  @Override
  public int ask() {
    return 0;
  }

  @Override
  public void roundEnd(int result) {

  }
}
