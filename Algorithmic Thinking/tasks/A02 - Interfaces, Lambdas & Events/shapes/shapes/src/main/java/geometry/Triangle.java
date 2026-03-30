package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import math.Vector2;
import math.Vector3;

public class Triangle extends Polygon
{
    private double a;
    private double b;
    private double c;
    private double angleC;

    private Vector3[] originalVertices;

    public Triangle(double a, double b, double angleC)
    {
        super();
        setA(a);
        setB(b);

        setAngleC(angleC);

        setC(Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(getAngleC()))));
    }

    public Triangle(Vector2 position, double a, double b, double angleC)
    {
        super(position);

        setA(a);
        setB(b);

        setAngleC(angleC);

        setC(Math.sqrt(a * a + b * b - 2 * a * b * Math.cos(Math.toRadians(getAngleC()))));
    }

    public Triangle(Vector2 vertexA, Vector2 vertexB, Vector2 vertexC, Color fillColor)
    {
        originalVertices = new Vector3[]
                {
            vertexA.toVector3(), vertexB.toVector3(), vertexC.toVector3()
        };

        super(new Vector2((vertexA.x + vertexB.x + vertexC.x) / 3, (vertexA.y + vertexB.y + vertexC.y) / 3));

        setA(Math.abs(Math.sqrt(Math.pow(vertexB.x - vertexA.x, 2) + Math.pow(vertexB.y - vertexA.y, 2))));
        setB(Math.abs(Math.sqrt(Math.pow(vertexC.x - vertexB.x, 2) + Math.pow(vertexC.y - vertexB.y, 2))));
        setC(Math.abs(Math.sqrt(Math.pow(vertexA.x - vertexC.x, 2) + Math.pow(vertexA.y - vertexC.y, 2))));

        double angleCAB = Math.toDegrees(Math.acos((getA() * getA() + getC() * getC() - getB() * getB()) / (2 * getA() * getC())));
        setAngleC(angleCAB);

        setFillColor(fillColor);
    }

    @Override
    public double getArea()
    {
        double s = getPerimeter() / 2;

        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double getPerimeter()
    {
        return a + b + c;
    }

    @Override
    public double[][] getCoordinates()
    {
        if (originalVertices == null)
        {
            originalVertices = new Vector3[] {
                new Vector3(getPosition().x, getPosition().y),
                new Vector3(getPosition().x + a, getPosition().y),
                new Vector3(getPosition().x + b * Math.cos(Math.toRadians(getAngleC())), getPosition().y + b * Math.sin(Math.toRadians(getAngleC())))
            };
        }

        return toCoordinates(transformed(originalVertices));
    }

    public double getA()
    {
        return a;
    }

    private void setA(double a)
    {
        if (a <= 0)
        {
            //throw new IllegalArgumentException("Side a must be greater than 0.");
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
            //throw new IllegalArgumentException("Side b must be greater than 0.");
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
            //throw new IllegalArgumentException("Side c must be greater than 0.");
        }

        this.c = c;
    }

    private void setAngleC(double angleC)
    {
        if (angleC <= 0 || angleC >= 180)
        {
            //throw new IllegalArgumentException("Angle C must be between 0 and 180 degrees.");
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

    @Override
    public void draw(GraphicsContext gc)
    {
        super.draw(gc);
        double[][] coords = getCoordinates();

        gc.fillPolygon(coords[0], coords[1], coords[0].length);
        gc.strokePolygon(coords[0], coords[1], coords[0].length);

    }
}
