package math;

import java.util.Arrays;


/**
 * From class, too lazy to write my own implementation agaiiin
 * */
public class Vector3 {
    private double[] values;

    // Default constructor: initializes to [0.0, 0.0, 1.0]
    public Vector3() {
        this.values = new double[]{0.0, 0.0, 1.0};
    }

    // Initializes this.values with the given array
    public Vector3(double[] values) {
        this.values = Arrays.copyOf(values, values.length);
    }

    // Copy constructor
    public Vector3(Vector3 vector) {
        this(vector.values);
    }

    // Convenience constructor with x and y coordinates (z = 1 for homogeneous coordinates)
    public Vector3(double x, double y) {
        this.values = new double[]{x, y, 1.0};
    }

    public double[] getValues() {
        return this.values;
    }

    public double getX() {
        return this.values[0];
    }

    public double getY() {
        return this.values[1];
    }

    @Override
    public String toString() {
        return "Vector3{values=" + Arrays.toString(values) + "}";
    }

    // Calculates the centroid of the first 'length' vertices
    public static Vector3 getCenter(Vector3[] vertices, int length) {
        double sumX = 0;
        double sumY = 0;
        for (int i = 0; i < length; i++) {
            sumX += vertices[i].getX();
            sumY += vertices[i].getY();
        }
        return new Vector3(sumX / length, sumY / length);
    }
}
