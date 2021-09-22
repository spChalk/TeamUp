package com.example.socialnetworkingapp.util;

import org.springframework.beans.factory.annotation.Autowired;

/* Matrix factorization */
public class MF {

    private final MatrixUtil util;

    @Autowired
    public MF(MatrixUtil util) {
        this.util = util;
    }

    public float[][] matrix_factorization(float[][] R, float[][] P, float[][] Q, int K) {

        int steps = 5000;
        float alpha = (float)0.0002;
        float beta = (float)0.02;

        Q = util.transpose(Q);

        for(int i = 0; i < steps; i++) {
            for(int j = 0; j < R.length; j++) {
                for(int k = 0; k < R[j].length; k++) {
                    if(R[j][k] > 0) {
                        float eij = R[j][k] - util.dot(P[j], util.transpose(Q)[k]);
                        for(int z = 0; z < K; z++) {
                            P[j][z] = P[j][z] + alpha * (2 * eij * Q[z][k] - beta * P[j][z]);
                            Q[z][k] = Q[z][k] + alpha * (2 * eij * P[j][z] - beta * Q[z][k]);
                        }
                    }
                }
            }
            float e = 0;
            for(int j = 0; j < R.length; j++) {
                for(int k = 0; k < R[j].length; k++) {
                    if(R[j][k] > 0) {
                        e = e + (float)Math.pow(R[j][k] - util.dot(P[j], util.transpose(Q)[k]), 2);
                        for(int z = 0; z < K; z++) {
                            e = e + (float)((beta/2) * (Math.pow(P[j][z], 2) + Math.pow(Q[z][k], 2)));
                        }
                    }
                }
            }
            if (e < 0.001) { break; }
        }
        float[][] finalArr = util.dot2D(P, Q);
        for (int a = 0; a < finalArr.length; a++) {
            for (int b = 0; b < finalArr[a].length; b++) {
                finalArr[a][b] = Math.round(finalArr[a][b]);
            }
        }
        return finalArr;
    }
}