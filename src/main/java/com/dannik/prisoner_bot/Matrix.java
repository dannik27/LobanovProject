package com.dannik.prisoner_bot;

public class Matrix {

    private double[][] array;
    private int size;

    public Matrix(int size){
        this.size = size;
        array = new double[size][size];
    }

    public double get(int p1, int p2){
        return array[p1][p2];
    }

    public void set(int p1, int p2, double value){
        array[p1][p2] = value;
    }

    public int getSize(){
        return size;
    }


}
