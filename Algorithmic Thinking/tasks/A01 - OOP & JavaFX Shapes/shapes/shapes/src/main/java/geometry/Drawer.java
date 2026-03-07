package geometry;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Drawer
{
    private final List<Polygon> polygons;
    private final GraphicsContext gc;

    public Drawer(GraphicsContext gc)
    {
        polygons = new ArrayList<>();
        this.gc = gc;
    }

    public Drawer(GraphicsContext gc, List<Polygon> polygons)
    {
        this.polygons = polygons;
        this.gc = gc;
    }

    public void drawAll()
    {
        polygons.forEach(polygon -> polygon.draw(gc, getRandomColor()));
    }

    public void draw(int index)
    {
        draw(index, getRandomColor());
    }

    public void addPolygon(Polygon polygon)
    {
        polygons.add(polygon);
    }

    public void draw(int index, Color color)
    {
        if (index < 0 || index >= polygons.size())
        {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (polygons.size() - 1));
        }

        polygons.get(index).draw(gc, color);
    }

    private Color getRandomColor()
    {
        return Color.color(Math.random(), Math.random(), Math.random());
    }

    public List<Polygon> getPolygons()
    {
        return polygons;
    }
}
