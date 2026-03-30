package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Line extends Polygon
{
    private Vector2 to;
    private Vector2 from;

    public Line(Vector2 from, Vector2 to)
    {
        super();
        this.from = from;
        this.to = to;
        setPosition(getMidPoint());
    }


    public Line(Vector2 from, Vector2 to, Color strokeColor)
    {
        super();
        this.from = from;
        this.to = to;
        setPosition(getMidPoint());
        setStrokeColor(strokeColor);
    }


    public Vector2 getMidPoint()
    {
        return new Vector2((from.x + to.x) / 2, (from.y + to.y) / 2);
    }

    public double getLength()
    {
        return Math.sqrt(Math.pow(to.x - from.x, 2) + Math.pow(to.y - from.y, 2));
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
        vertices[0] = new Vector3(from.x, from.y);
        vertices[1] = new Vector3(to.x, to.y);

        return toCoordinates(transformed(vertices));
    }

    public Vector2 getTo()
    {
        return to;
    }

    private void setTo(Vector2 to)
    {
        this.to = to;
        this.setPosition(getMidPoint());
    }

    public Vector2 getFrom()
    {
        return from;
    }

    private void setFrom(Vector2 from)
    {
        this.from = from;
        this.setPosition(getMidPoint());
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
        return this.from.equals(other.from) && this.to.equals(other.to);
    }

    @Override
    public String toString()
    {
        return "Line: From" + from + " to " + to;
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
