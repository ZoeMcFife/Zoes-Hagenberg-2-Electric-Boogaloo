package Geometry;

public class Ellipse extends Polygon
{
    private double semiMajorAxis;
    private double semiMinorAxis;

    Ellipse(double semiMajorAxis, double semiMinorAxis)
    {
        super();
        setSemiMajorAxis(semiMajorAxis);
        setSemiMinorAxis(semiMinorAxis);
    }

    Ellipse(Vector2 position, double semiMajorAxis, double semiMinorAxis)
    {
        super(position);
        setSemiMajorAxis(semiMajorAxis);
        setSemiMinorAxis(semiMinorAxis);
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

    public double getSemiMajorAxis()
    {
        return semiMajorAxis;
    }

    public double getSemiMinorAxis()
    {
        return semiMinorAxis;
    }

    private void setSemiMajorAxis(double semiMajorAxis)
    {
        if (semiMajorAxis <= 0)
        {
            throw new IllegalArgumentException("Semi-major axis must be positive.");
        }

        this.semiMajorAxis = semiMajorAxis;
    }

    private void setSemiMinorAxis(double semiMinorAxis)
    {
        if (semiMinorAxis <= 0)
        {
            throw new IllegalArgumentException("Semi-minor axis must be positive.");
        }

        this.semiMinorAxis = semiMinorAxis;
    }
}
