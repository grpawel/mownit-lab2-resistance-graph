package pl.edu.agh.mownit.lab2;

import Jama.Matrix;

/**
 * Created by Paweł Grochola on 03.11.2017.
 */
public class LinearEquationSystem {
    private final double[][] coefficients;
    private final double[] freeTerms;
    private double[] result;
    private final int size;

    public LinearEquationSystem(final int size) {
        this.coefficients = new double[size][size];
        this.freeTerms = new double[size];
        this.result = new double[size];
        this.size = size;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                coefficients[i][j] = 0;
            }
            freeTerms[i] = 0;
            result[i] = 0;
        }
    }

    public void solve() {
        Matrix lhs = new Matrix(coefficients);
        Matrix rhs = new Matrix(freeTerms, size);
        double[][] ans = lhs.solve(rhs).getArray();
        for (int i = 0; i < size; i++) {
            result[i] = ans[i][0];
        }
    }

    public double[][] getCoefficients() {
        return coefficients;
    }

    public double[] getFreeTerms() {
        return freeTerms;
    }

    public double[] getResult() {
        return result;
    }

    public int getSize() {
        return size;
    }
}
