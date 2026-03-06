package Geometry;

public class Star extends Polygon
{
    public double innerRadius;
    public double outerRadius;
    public int numPoints;

    Star(double innerRadius, double outerRadius, int numPoints)
    {
        super();

        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.numPoints = numPoints;
    }

    Star(Vector2 position, double innerRadius, double outerRadius, int numPoints)
    {
        super(position);

        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.numPoints = numPoints;
    }

    @Override
    public double area()
    {
        return numPoints * innerRadius * outerRadius * Math.sin(Math.PI / numPoints);
    }

    @Override
    public double perimeter()
    {
        return 2 * numPoints * getEdgeLength();
    }

    private double getEdgeLength()
    {
        double angle = 360 / numPoints;
        double innerEdge = Math.sqrt(2 * innerRadius * innerRadius - 2 * innerRadius * innerRadius * Math.cos(angle));
        double innerTriangleHeight = Math.sqrt(innerRadius * innerRadius - (innerEdge / 2) * (innerEdge / 2));
        double outerTriangleHeight = outerRadius - innerTriangleHeight;
        double outerEdge = Math.sqrt(2 * outerTriangleHeight * outerTriangleHeight + (innerEdge / 2) * (innerEdge / 2));

        return outerEdge;
    }
}
