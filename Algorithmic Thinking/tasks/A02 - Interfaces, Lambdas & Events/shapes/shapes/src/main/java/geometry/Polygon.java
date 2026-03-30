package geometry;

import art.Drawable;
import geometry.interfaces.Selectable;
import geometry.interfaces.Shape;
import geometry.interfaces.Transformable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Matrix3;
import math.TransformFactory;
import math.Vector2;
import math.Vector3;

public abstract class Polygon implements Shape, Drawable, Transformable, Selectable
{
    private Vector2 position;
    private Matrix3 transform;
    private boolean selected;

    Polygon()
    {
       setPosition(Vector2.ZERO);
    }

    Polygon(Vector2 position)
    {
        setPosition(position);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }

    private Color fillColor = Color.PURPLE;
    private Color strokeColor = Color.PURPLE;


    @Override
    public String toString()
    {
        return String.format("%s at %s", getClass().getSimpleName(), getPosition());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Polygon other = (Polygon) obj;
        return getPosition().equals(other.getPosition());
    }

    public double getX()
    {
        return position.getX();
    }

    public double getY()
    {
        return position.getY();
    }

    public void setX(double x)
    {
        position.setX(x);
    }

    public void setY(double y)
    {
        position.setY(y);
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void applyTransform(Matrix3 m)
    {
        this.transform = (this.transform == null) ? m :m.mult(this.transform);
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
            position.x += dx;
            position.y += dy;
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

    protected Vector3[] transformed(Vector3[] points)
    {
        if (transform != null)
        {
            for (int i = 0; i < points.length; i++)
            {
                points[i] = transform.mult(points[i]);
            }
        }

        return points;
    }

    public Rectangle getBoundingBox()
    {
        double[][] coords = getCoordinates();
        double minX = coords[0][0], maxX = coords[0][0];
        double minY = coords[1][0], maxY = coords[1][0];

        for (int i = 1; i < coords[0].length; i++)
        {
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
    public void draw(GraphicsContext gc)
    {
        if (isSelected())
        {
            Rectangle bb = getBoundingBox();
            gc.save();
            gc.setStroke(Color.LIGHTGREEN);
            gc.setLineWidth(2);
            gc.strokeRect(bb.getX(), bb.getY(), bb.getWidth(), bb.getHeight());
            bb.draw(gc);
            gc.restore();
        }
        gc.setFill(fillColor);
        gc.setStroke(strokeColor);
    }

    // ========================================
    // HELPER
    // ========================================

    // Converts an array of Vector3 vertices to a double[2][n] coordinate array
    static double[][] toCoordinates(Vector3[] transformed)
    {
        double[][] ret = new double[2][transformed.length];
        for (int i = 0; i < transformed.length; i++)
        {
            ret[0][i] = transformed[i].getX();
            ret[1][i] = transformed[i].getY();
        }
        return ret;
    }
}

