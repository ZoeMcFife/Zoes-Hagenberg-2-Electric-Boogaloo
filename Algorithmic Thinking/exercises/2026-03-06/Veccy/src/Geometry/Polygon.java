package Geometry;

public abstract class Polygon implements Shape
{
    private Vector2 position;

    Polygon()
    {
       setPosition(Vector2.ZERO);
    }

    Polygon(Vector2 position)
    {
        setPosition(position);
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public void setPosition(Vector2 position)
    {
        this.position = position;
    }
}
