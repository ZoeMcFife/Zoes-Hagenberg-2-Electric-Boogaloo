package geometry;

public class Vector2
{
    public double x;
    public double y;

    public static final Vector2 ZERO = new Vector2(0, 0);

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
    public boolean equals(Object other)
    {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector2 vector2 = (Vector2) other;

        return (Math.abs(this.x - vector2.x) < 1e-9) && (Math.abs(this.y - vector2.y) < 1e-9);
    }

    @Override
    public String toString()
    {
        return String.format("Vector2(%.2f, %.2f)", x, y);
    }
}
