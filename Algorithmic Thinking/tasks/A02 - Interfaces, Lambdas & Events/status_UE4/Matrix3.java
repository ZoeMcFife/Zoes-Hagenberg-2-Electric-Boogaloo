package at.fhooe.ald.math;

public class Matrix3 {
    private double[][] values;

    // Default constructor: identity matrix
    // 1.0 0.0 0.0
    // 0.0 1.0 0.0
    // 0.0 0.0 1.0
    public Matrix3() {
        this(new double[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
    }

    // Initializes this.values with the given 2D array
    public Matrix3(double[][] values) {
        this.values = values;
    }

    // Copy constructor
    public Matrix3(Matrix3 matrix) {
        this(matrix.values);
    }

    // Matrix-matrix multiplication: returns a new Matrix3 with the result
    public Matrix3 mult(Matrix3 other) {
        double[][] result = matrixMult(this.values, other.values);
        return new Matrix3(result);
    }

    // Matrix-vector multiplication: returns a new Vector3 with the result
    public Vector3 mult(Vector3 vector) {
        double[][] col = {
            {vector.getValues()[0]},
            {vector.getValues()[1]},
            {vector.getValues()[2]}
        };
        double[][] result = matrixMult(this.values, col);
        return new Vector3(new double[]{result[0][0], result[1][0], result[2][0]});
    }

    public double[][] getValues() {
        return this.values;
    }

    private double[][] matrixMult(double[][] m1, double[][] m2) {
        int rows = m1.length;
        int cols = m2[0].length;
        int inner = m1[0].length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < inner; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return result;
    }
}
