package at.fhooe.ald.shapes;

import at.fhooe.ald.math.Matrix3;
import at.fhooe.ald.math.TransformFactory;
import at.fhooe.ald.math.Vector3;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape implements Selectable, Transformable {

    // ========================================
    // FIELDS
    // ========================================

    protected Vector3 position;
    protected Matrix3 transform;
    protected Color fillColor;
    protected Color strokeColor;

    private boolean selected = false;

    // ========================================
    // CONSTRUCTOR
    // ========================================

    public Shape(double x, double y, Color fillColor) {
        this.position = new Vector3(x, y);
        this.fillColor = fillColor;
        this.strokeColor = fillColor;
    }

    // ========================================
    // POSITION GETTERS / SETTERS
    // ========================================

    public double getX() {
        return this.position.getX();
    }

    public double getY() {
        return this.position.getY();
    }

    // Note: directly modifies the underlying array (position is mutable)
    public void setX(double x) {
        this.position.getValues()[0] = x;
    }

    public void setY(double y) {
        this.position.getValues()[1] = y;
    }

    // Moves the shape by (dx, dy) in world (display) space.
    // If a transform matrix is active, we prepend a translation matrix so the
    // drag direction matches what the user sees regardless of rotation/scale.
    // If no transform exists, we update the raw position directly.
    // Subclasses with extra raw points (Triangle, Line) override this, but call
    // super first — they only need to move their extra points when transform == null.
    public void translate(double dx, double dy) {
        if (transform != null) {
            transform = TransformFactory.createTranslation(dx, dy).mult(transform);
        } else {
            position.getValues()[0] += dx;
            position.getValues()[1] += dy;
        }
    }

    // ========================================
    // SELECTABLE INTERFACE
    // ========================================

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // ========================================
    // TRANSFORMABLE INTERFACE
    // ========================================

    @Override
    public void setTransform(Matrix3 transform) {
        this.transform = transform;
    }

    public Matrix3 getTransform() {
        return this.transform;
    }

    // Composes m with the current transform (or sets it if none exists yet).
    public void applyTransform(Matrix3 m) {
        this.transform = (this.transform == null) ? m : m.mult(this.transform);
    }

    // getCoordinates() is abstract -- each shape implements its own geometry
    @Override
    public abstract double[][] getCoordinates();

    // ========================================
    // COLOR SETTERS
    // ========================================

    public void setFillColor(Color c) {
        this.fillColor = c;
    }

    public void setStrokeColor(Color c) {
        this.strokeColor = c;
    }

    // ========================================
    // ABSTRACT METHODS (geometry)
    // ========================================

    public abstract double getArea();

    public abstract double getPerimeter();

    // ========================================
    // BOUNDING BOX
    // ========================================

    // Calculates the axis-aligned bounding box from getCoordinates()
    public Rectangle getBoundingBox() {
        double[][] coords = getCoordinates();
        double minX = coords[0][0], maxX = coords[0][0];
        double minY = coords[1][0], maxY = coords[1][0];
        for (int i = 1; i < coords[0].length; i++) {
            if (coords[0][i] < minX) minX = coords[0][i];
            if (coords[0][i] > maxX) maxX = coords[0][i];
            if (coords[1][i] < minY) minY = coords[1][i];
            if (coords[1][i] > maxY) maxY = coords[1][i];
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    // ========================================
    // DRAW
    // ========================================

    // Draws a light-green bounding box if selected, then sets fill/stroke color.
    // Subclasses call super.draw(gc) first, then draw their own geometry.
    public void draw(GraphicsContext gc) {
        if (isSelected()) {
            Rectangle bb = getBoundingBox();
            gc.save();
            gc.setStroke(Color.LIGHTGREEN);
            gc.setLineWidth(2);
            gc.strokeRect(bb.getX(), bb.getY(), bb.getWidth(), bb.getHeight());
            gc.restore();
        }
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
    }

    // ========================================
    // HELPER
    // ========================================

    // Converts an array of Vector3 vertices to a double[2][n] coordinate array
    static double[][] toCoordinates(Vector3[] transformed) {
        double[][] ret = new double[2][transformed.length];
        for (int i = 0; i < transformed.length; i++) {
            ret[0][i] = transformed[i].getX();
            ret[1][i] = transformed[i].getY();
        }
        return ret;
    }
}
