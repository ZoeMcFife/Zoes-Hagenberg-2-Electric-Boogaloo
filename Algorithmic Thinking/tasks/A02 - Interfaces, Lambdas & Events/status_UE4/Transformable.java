package at.fhooe.ald.shapes;

import at.fhooe.ald.math.Matrix3;

public interface Transformable {
    void setTransform(Matrix3 transform);
    double[][] getCoordinates();
}
