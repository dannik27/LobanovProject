package com.dannik.prisoner_bot;

import java.util.Random;

public class MatrixUtils {


    public static Matrix matrixFromMessage(int[] size, double[][][] values, int hand) {
        Matrix matrix = new Matrix(size[0]);
        for (int i = 0; i < size[0]; i++){
            for (int j = 0; j < size[0]; j++){
                matrix.set(i, j, values[i][j][hand]);
            }
        }
        return matrix;
    }

    public static Matrix createPrisonersMatrix() {
        Random random = new Random();

        int a = random.nextInt(10) - 5;
        int b = a + 1 + random.nextInt(5);
        int c = b + 1 + random.nextInt(5);
        int d = c + 1 + random.nextInt(5);

        Matrix matrix = new Matrix(2);
        matrix.set(0, 0, c); // не сдал/не сдал
        matrix.set(0, 1, a); // не сдал/сдал
        matrix.set(1, 0, d); // сдал/не сдал
        matrix.set(1, 1, b); // сдал/сдал

        return matrix;
    }

    public static Matrix createChickenMatrix() {
        Random random = new Random();

        int a = 0;
        int b = 1 + random.nextInt(10);
        int c = b * (-1);
        int d = (b + 10 + random.nextInt(100)) * (-1);

        Matrix matrix = new Matrix(2);
        matrix.set(0, 0, a); // не сдал/не сдал
        matrix.set(0, 1, c); // не сдал/сдал
        matrix.set(1, 0, b); // сдал/не сдал
        matrix.set(1, 1, d); // сдал/сдал

        return matrix;
    }

    public static Matrix createRandomMatrix(){
        Random random = new Random();

        int size = 2 + random.nextInt(2);

        Matrix matrix = new Matrix(size);

        for (int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
//                if(j >= i){
                matrix.set(i, j,(-4) + random.nextInt(9));
//                } else{
//                    matrix.set(i, j, (-1) * matrix.get(j, i));
//                }
            }
        }
        return matrix;
    }

    public static void printMatrix(Matrix matrix){

        for(int i = 0; i < matrix.getSize() * 2 + 1; i++){
            System.out.print("|");
            if(i % 2 == 1){
                for(int j = 0; j < matrix.getSize(); j++){
                    System.out.print(" ");
                    if(matrix.get((i-1)/2, j) >= 0){
                        System.out.print(" ");
                    }
                    System.out.print(matrix.get((i-1)/2, j));
                    System.out.print(" |");

                }
            } else{
                for(int j = 0; j < matrix.getSize(); j++){
                    System.out.print("----|");

                }
            }

            System.out.print("\n");
        }

    }

}
