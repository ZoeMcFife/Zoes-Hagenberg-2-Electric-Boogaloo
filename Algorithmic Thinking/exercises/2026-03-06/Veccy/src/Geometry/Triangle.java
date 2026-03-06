package Geometry;

public class Triangle extends Polygon
{
    public double a;
    public double b;
    public double c;

    Triangle(double a, double b, double c)
    {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    Triangle(Vector2 position, double a, double b, double c)
    {
        super(position);
        this.a = a;
        this.b = b;
        this.c = c;
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
}
