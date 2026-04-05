package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Line extends Polygon
{
    private Vector2 toDelta;
    private Vector2 fromDelta;

    public Line(Vector2 from, Vector2 to)
    {
        super();

        setPosition(getMidPoint(from, to));

        this.fromDelta = getPosition().subtract(from);
        this.toDelta = getPosition().subtract(to);
    }


    public Line(Vector2 from, Vector2 to, Color strokeColor)
    {
        super();
        setPosition(getMidPoint(from, to));
        this.fromDelta = getPosition().subtract(from);
        this.toDelta = getPosition().subtract(to);
        setStrokeColor(strokeColor);
    }


    private Vector2 getMidPoint(Vector2 from, Vector2 to)
    {
        return new Vector2((from.x + to.x) / 2, (from.y + to.y) / 2);
    }

    public double getLength()
    {
        return Math.sqrt(Math.pow(toDelta.x - fromDelta.x, 2) + Math.pow(toDelta.y - fromDelta.y, 2));
    }

    @Override
    public double getArea()
    {
        return 0;
    }

    @Override
    public double getPerimeter()
    {
        return getLength();
    }

    @Override
    public double[][] getCoordinates()
    {
        Vector3[] vertices = new Vector3[2];
        vertices[0] = (getPosition().add(fromDelta)).toVector3();
        vertices[1] = (getPosition().add(toDelta)).toVector3();

        return toCoordinates(transformed(vertices));
    }

    public Vector2 getToDelta()
    {
        return toDelta;
    }


    public Vector2 getFromDelta()
    {
        return fromDelta;
    }


    @Override
    public void setFillColor(Color fillColor)
    {
        setStrokeColor(fillColor);
    }

    @Override
    public Color getFillColor()
    {
        return getStrokeColor();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Line other = (Line) obj;
        return this.fromDelta.equals(other.fromDelta) && this.toDelta.equals(other.toDelta);
    }

    @Override
    public String toString()
    {
        return "Line: From" + fromDelta + " to " + toDelta;
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);

        double[][] coords = getCoordinates();

        gc.fillPolygon(coords[0], coords[1], coords[0].length);
        gc.strokePolygon(coords[0], coords[1], coords[0].length);
    }
}
