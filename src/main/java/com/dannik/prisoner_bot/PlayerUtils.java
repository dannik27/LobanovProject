package com.dannik.prisoner_bot;

public class PlayerUtils {

    public static int bestScore(Matrix matrix){
        double bestScore = Double.MIN_VALUE;
        int bestVariant = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                if(matrix.get(i, j) > bestScore){
                    bestScore = matrix.get(i, j);
                    bestVariant = i;
                }
            }
        }
        return bestVariant;
    }

    public static int bestScoreForBoth(Matrix matrix){
        double bestScore = Double.MIN_VALUE;
        int bestVariant = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            for (int j = 0; j < matrix.getSize(); j++) {
                double scoreSum = matrix.get(i, j) + matrix.get(j, i);
                if(scoreSum > bestScore){
                    bestScore = scoreSum;
                    bestVariant = i;
                }
            }
        }
        return bestVariant;
    }

    public static int bestScoreSumInLine(Matrix matrix){
        int bestSum = 0;
        int bestVariant = 0;
        for (int i = 0; i < matrix.getSize(); i++) {
            int sum = 0;

            for (int j = 0; j < matrix.getSize(); j++) {
                sum += matrix.get(i, j);
            }

            if(sum > bestSum){
                bestSum = sum;
                bestVariant = i;
            }
        }
        return bestVariant;
    }

    public static int simpleNesh(Matrix matrix){

        for (int i = 0; i < matrix.getSize(); i++){
            next:for(int j = 0; j < matrix.getSize(); j++){

                for(int k = 0; k < matrix.getSize(); k++){
                    if(matrix.get(k, j) > matrix.get(i, j)){
                        continue next;
                    }
                }

                for(int l = 0; l < matrix.getSize(); l++){
                    if(matrix.get(l, i) > matrix.get(j, i)){
                        continue next;
                    }
                }
                return i;
            }
        }
        return -1;
    }

}
