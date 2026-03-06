package Geometry;

public class Ellipse extends Polygon
{
    public double semiMajorAxis;
    public double semiMinorAxis;

    Ellipse(double semiMajorAxis, double semiMinorAxis)
    {
        super();
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
    }

    Ellipse(Vector2 position, double semiMajorAxis, double semiMinorAxis)
    {
        super(position);
        this.semiMajorAxis = semiMajorAxis;
        this.semiMinorAxis = semiMinorAxis;
    }

    @Override
    public double area()
    {
        return Math.PI * semiMajorAxis * semiMinorAxis;
    }

    @Override
    public double perimeter()
    {
        return Math.PI * (3 * (semiMajorAxis + semiMinorAxis) - Math.sqrt((3 * semiMajorAxis + semiMinorAxis) * (semiMajorAxis + 3 * semiMinorAxis)));
    }
}
