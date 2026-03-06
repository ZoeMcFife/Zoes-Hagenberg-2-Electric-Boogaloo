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

    @Override
    public String toString()
    {
        return String.format("%s at %s", getClass().getSimpleName(), getPosition());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Polygon other = (Polygon) obj;
        return getPosition().equals(other.getPosition());
    }
}
