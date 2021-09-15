package com.example.socialnetworkingapp.util;

public class MatrixUtil {

    public void print1DArray(float[] A) {

        for (float v : A) {
            System.out.print(v);
            System.out.print(" ");
        }
        System.out.print("\n\n");
    }

    public void print2DArray(float[][] A) {

        for (float[] floats : A) {
            for (float aFloat : floats) {
                System.out.print(aFloat);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    public float[][] transpose(float[][] A) {

        int M = A.length, N = A[0].length;
        float[][] T = new float[N][M];

        for(int i=0 ; i<N ; i++){
            for(int j=0 ; j<M ; j++){
                T[i][j] = A[j][i];
            }
        }
        return T;
    }

    public float dot(float[] A, float[] B) {

        float product = 0;
        for (int i = 0; i < A.length; i++)
            product += A[i] * B[i];
        return product;
    }


    public float[][] dot2D(float[][] A, float[][] B) {

        int row1 = A.length;
        int col1 = A[0].length;
        int row2 = B.length;
        int col2 = B[0].length;

        //For two matrices to be multiplied,
        //number of columns in first matrix must be equal to number of rows in second matrix
        if(col1 != row2){
            System.out.println("Matrices cannot be multiplied");
        } else {
            //Array prod will hold the result
            float[][] prod = new float[row1][col2];

            //Performs product of matrices a and b. Store the result in matrix prod
            for(int i = 0; i < row1; i++){
                for(int j = 0; j < col2; j++){
                    for(int k = 0; k < row2; k++){
                        prod[i][j] = prod[i][j] + A[i][k] * B[k][j];
                    }
                }
            }
            return prod;
        }
        return null;
    }
}