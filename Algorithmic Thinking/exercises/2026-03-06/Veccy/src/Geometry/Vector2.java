package Geometry;

public class Vector2
{
    public double x;
    public double y;

    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other)
    {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 subtract(Vector2 other)
    {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 scale(double scalar)
    {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public double dot(Vector2 other)
    {
        return this.x * other.x + this.y * other.y;
    }

    public double magnitude()
    {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 normalize()
    {
        double mag = magnitude();
        if (mag == 0) return new Vector2(0, 0);
        return new Vector2(this.x / mag, this.y / mag);
    }

    public double distanceTo(Vector2 other)
    {
        return this.subtract(other).magnitude();
    }

    @Override
    public String toString()
    {
        return String.format("Vector2(%.2f, %.2f)", x, y);
    }
}
