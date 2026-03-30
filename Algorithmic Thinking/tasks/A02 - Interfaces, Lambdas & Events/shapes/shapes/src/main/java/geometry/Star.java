package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Star extends Polygon
{
    private double innerRadius;
    private double outerRadius;
    private int numPoints;

    public Star(double innerRadius, double outerRadius, int numPoints)
    {
        super();

        setInnerRadius(innerRadius);
        setOuterRadius(outerRadius);
        setNumPoints(numPoints);
    }

    public Star(Vector2 position, double innerRadius, double outerRadius, int numPoints)
    {
        super(position);

        setInnerRadius(innerRadius);
        setOuterRadius(outerRadius);
        setNumPoints(numPoints);
    }

    public Star(Vector2 position, double innerRadius, double outerRadius, int numPoints, Color fillColor)
    {
        super(position);

        setInnerRadius(innerRadius);
        setOuterRadius(outerRadius);
        setNumPoints(numPoints);
        setFillColor(fillColor);
    }


    @Override
    public double getArea()
    {
        return getNumPoints() * getInnerRadius() * getOuterRadius() * Math.sin(Math.PI / getNumPoints());
    }

    @Override
    public double getPerimeter()
    {
        return 2 * getNumPoints() * getEdgeLength();
    }

    @Override
    public double[][] getCoordinates()
    {
        Vector3[] vertices = new Vector3[getNumPoints() * 2];

        double angleStep = 2 * Math.PI / getNumPoints();
        double[] xPoints = new double[getNumPoints() * 2];
        double[] yPoints = new double[getNumPoints() * 2];

        for (int i = 0; i < getNumPoints(); i++)
        {
            double angle = i * angleStep;

            xPoints[i * 2] = getPosition().x + getOuterRadius() * Math.cos(angle);
            yPoints[i * 2] = getPosition().y + getOuterRadius() * Math.sin(angle);
            xPoints[i * 2 + 1] = getPosition().x + getInnerRadius() * Math.cos(angle + angleStep / 2);
            yPoints[i * 2 + 1] = getPosition().y + getInnerRadius() * Math.sin(angle + angleStep / 2);
        }

        // convert to vertices array

        for (int i = 0; i < getNumPoints() * 2; i++)
        {
            vertices[i] = new Vector3(xPoints[i], yPoints[i]);
        }

        return toCoordinates(transformed(vertices));
    }



    private double getEdgeLength()
    {
        return Math.sqrt(getOuterRadius() * getOuterRadius() + getInnerRadius() * getInnerRadius() - 2 * getOuterRadius() * getInnerRadius() * Math.cos(Math.PI / getNumPoints()));
    }

    private void setInnerRadius(double innerRadius)
    {
        if (innerRadius <= 0)
        {
            throw new IllegalArgumentException("Inner radius must be greater than zero.");
        }

        this.innerRadius = innerRadius;
    }

    public double getInnerRadius()
    {
        return innerRadius;
    }

    private void setOuterRadius(double outerRadius)
    {
        if (outerRadius <= 0)
        {
            throw new IllegalArgumentException("Outer radius must be greater than zero.");
        }

        if (outerRadius <= getInnerRadius())
        {
            throw new IllegalArgumentException("Outer radius must be greater than inner radius.");
        }

        this.outerRadius = outerRadius;
    }

    public double getOuterRadius()
    {
        return outerRadius;
    }

    private void setNumPoints(int numPoints)
    {
        if (numPoints < 3)
        {
            throw new IllegalArgumentException("Number of points must be at least 3.");
        }

        this.numPoints = numPoints;
    }

    public int getNumPoints()
    {
        return numPoints;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Star other = (Star) obj;

        return Double.compare(getInnerRadius(), other.getInnerRadius()) == 0 &&
               Double.compare(getOuterRadius(), other.getOuterRadius()) == 0 &&
               getNumPoints() == other.getNumPoints();
    }

    @Override
    public String toString()
    {
        return String.format("Star [position=%s, innerRadius=%.2f, outerRadius=%.2f, numPoints=%d]", getPosition(), getInnerRadius(), getOuterRadius(), getNumPoints());
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
