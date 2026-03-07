package geometry;

import geometry.Line;
import geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LineTest
{
    @Test
    void testConstructorAndMidPoint()
    {
        Vector2 from = new Vector2(0, 0);
        Vector2 to = new Vector2(4, 6);
        Line line = new Line(from, to);

        Vector2 expectedMid = new Vector2(2, 3);
        assertEquals(expectedMid, line.getPosition());
        assertEquals(from, line.getFrom());
        assertEquals(to, line.getTo());
    }

    @Test
    void testGetLength()
    {
        Vector2 from = new Vector2(0, 0);
        Vector2 to = new Vector2(3, 4);
        Line line = new Line(from, to);

        assertEquals(5, line.getLength(), 1e-9);
        assertEquals(line.getLength(), line.perimeter(), 1e-9);
    }

    @Test
    void testArea()
    {
        Line line = new Line(new Vector2(0, 0), new Vector2(1, 1));
        assertEquals(0, line.area(), 1e-9);
    }

    @Test
    void testEquals()
    {
        Line l1 = new Line(new Vector2(1, 2), new Vector2(3, 4));
        Line l2 = new Line(new Vector2(1, 2), new Vector2(3, 4));
        Line l3 = new Line(new Vector2(0, 0), new Vector2(3, 4));

        assertEquals(l1, l2);
        assertNotEquals(l1, l3);
        assertNotEquals(null, l1);
        assertNotEquals("Not a Line", l1);
    }

    @Test
    void testToString()
    {
        Line line = new Line(new Vector2(1, 2), new Vector2(3, 4));
        assertEquals("Line: FromVector2(1.00, 2.00) to Vector2(3.00, 4.00)", line.toString());
    }
}
