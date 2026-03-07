package main.geometry;

public class Triangle extends Polygon
{
    private double a;
    private double b;
    private double c;
    private double angleC;

    Triangle(double a, double b, double angleC)
    {
        super();
        setA(a);
        setB(b);

        setAngleC(angleC);

        setC(Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(getAngleC()))));
    }

    Triangle(Vector2 position, double a, double b, double angleC)
    {
        super(position);

        setA(a);
        setB(b);

        setAngleC(angleC);

        setC(Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(getAngleC()))));
    }

    @Override
    public double area()
    {
        double s = perimeter() / 2;

        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double perimeter()
    {
        return a + b + c;
    }

    public double getA()
    {
        return a;
    }

    private void setA(double a)
    {
        if (a <= 0)
        {
            throw new IllegalArgumentException("Side a must be greater than 0.");
        }

        this.a = a;
    }

    public double getB()
    {
        return b;
    }

    private void setB(double b)
    {
        if (b <= 0)
        {
            throw new IllegalArgumentException("Side b must be greater than 0.");
        }

        this.b = b;
    }

    public double getC()
    {
        return c;
    }

    private void setC(double c)
    {
        if (c <= 0)
        {
            throw new IllegalArgumentException("Side c must be greater than 0.");
        }

        this.c = c;
    }

    private void setAngleC(double angleC)
    {
        if (angleC <= 0 || angleC >= 180)
        {
            throw new IllegalArgumentException("Angle C must be between 0 and 180 degrees.");
        }

        this.angleC = angleC;
    }

    private double getAngleC()
    {
        return angleC;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Triangle triangle = (Triangle) obj;

        return Double.compare(triangle.a, a) == 0 &&
               Double.compare(triangle.b, b) == 0 &&
               Double.compare(triangle.c, c) == 0;
    }

    @Override
    public String toString()
    {
        return String.format("Triangle [position=%s, a=%.2f, b=%.2f, c=%.2f]", getPosition(), getA(), getB(), getC());
    }
}
