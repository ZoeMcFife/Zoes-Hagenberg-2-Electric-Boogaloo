package Geometry;

public abstract class Polygon implements Shape
{
    public Vector2 position;

    Polygon()
    {
        this.position = new Vector2(0, 0);
    }

    Polygon(Vector2 position)
    {
        this.position = position;
    }
}
