package com.example.socialnetworkingapp.util;

public class MatrixUtil {

    public float[][] transpose(float[][] A) {

        int M = A.length, N = A[0].length;
        float[][] T = new float[N][M];

        for(int i = 0 ; i < N ; i++){
            for(int j = 0 ; j < M ; j++){
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

        /* For two matrices to be multiplied, the number of columns in first matrix must be equal to number of rows in second matrix */
        if(col1 != row2){
            System.out.println("Matrices cannot be multiplied");
        } else {

            float[][] prod = new float[row1][col2];

            /* Performs product of matrices A and B.
                Stores the result in matrix prod */
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
