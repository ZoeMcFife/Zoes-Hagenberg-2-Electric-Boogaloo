package test.geometry;

import main.geometry.Point;
import main.geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest
{
    @Test
    void testDefaultConstructor()
    {
        Point p = new Point();
        assertEquals(new Vector2(0, 0), p.getPosition());
        assertEquals(0, p.area(), 1e-9);
        assertEquals(0, p.perimeter(), 1e-9);
    }

    @Test
    void testConstructorWithPosition()
    {
        Vector2 pos = new Vector2(3, 4);
        Point p = new Point(pos);
        assertEquals(pos, p.getPosition());
        assertEquals(0, p.area(), 1e-9);
        assertEquals(0, p.perimeter(), 1e-9);
    }

    @Test
    void testToString()
    {
        Vector2 pos = new Vector2(1.234, 5.678);
        Point p = new Point(pos);
        assertEquals("Point at (1.23, 5.68)", p.toString());
    }

    @Test
    void testEquals()
    {
        Vector2 pos1 = new Vector2(2, 3);
        Vector2 pos2 = new Vector2(2, 3);
        Vector2 pos3 = new Vector2(4, 5);

        Point p1 = new Point(pos1);
        Point p2 = new Point(pos2);
        Point p3 = new Point(pos3);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(null, p1);
    }

}
