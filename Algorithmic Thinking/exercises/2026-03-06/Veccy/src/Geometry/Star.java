package Geometry;

public class Star extends Polygon
{
    private double innerRadius;
    private double outerRadius;
    private int numPoints;

    Star(double innerRadius, double outerRadius, int numPoints)
    {
        super();

        setInnerRadius(innerRadius);
        setOuterRadius(outerRadius);
        setNumPoints(numPoints);
    }

    Star(Vector2 position, double innerRadius, double outerRadius, int numPoints)
    {
        super(position);

        setInnerRadius(innerRadius);
        setOuterRadius(outerRadius);
        setNumPoints(numPoints);
    }

    @Override
    public double area()
    {
        return getNumPoints() * getInnerRadius() * getOuterRadius() * Math.sin(Math.PI / getNumPoints());
    }

    @Override
    public double perimeter()
    {
        return 2 * getNumPoints() * getEdgeLength();
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
}
