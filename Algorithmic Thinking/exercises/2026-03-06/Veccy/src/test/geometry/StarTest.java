package test.geometry;

import main.geometry.Star;
import main.geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StarTest
{
    @Test
    void testConstructorAndGetters()
    {
        Star s = new Star(2, 5, 5);
        assertEquals(2, s.getInnerRadius(), 1e-9);
        assertEquals(5, s.getOuterRadius(), 1e-9);
        assertEquals(5, s.getNumPoints());
        assertEquals(new Vector2(0, 0), s.getPosition());

        Vector2 pos = new Vector2(1, 3);
        Star s2 = new Star(pos, 1, 4, 6);
        assertEquals(1, s2.getInnerRadius(), 1e-9);
        assertEquals(4, s2.getOuterRadius(), 1e-9);
        assertEquals(6, s2.getNumPoints());
        assertEquals(pos, s2.getPosition());
    }

    @Test
    void testArea()
    {
        Star s = new Star(2, 5, 5);
        double expected = 5 * 2 * 5 * Math.sin(Math.PI / 5);
        assertEquals(expected, s.area(), 1e-9);
    }

    @Test
    void testPerimeter()
    {
        Star s = new Star(2, 5, 5);
        double edgeLength = Math.sqrt(5*5 + 2*2 - 2*5*2*Math.cos(Math.PI/5));
        double expected = 2 * 5 * edgeLength;
        assertEquals(expected, s.perimeter(), 1e-9);
    }

    @Test
    void testEquals()
    {
        Star s1 = new Star(2, 5, 5);
        Star s2 = new Star(2, 5, 5);
        Star s3 = new Star(2, 4, 5);

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(null, s1);
        assertNotEquals("not a star", s1);
    }

    @Test
    void testToString()
    {
        Star s = new Star(new Vector2(1, 2), 2, 5, 5);
        assertEquals("Star [position=Vector2(1.00, 2.00), innerRadius=2.00, outerRadius=5.00, numPoints=5]", s.toString());
    }

    @Test
    void testInvalidParameters()
    {
        assertThrows(IllegalArgumentException.class, () -> new Star(0, 5, 5));
        assertThrows(IllegalArgumentException.class, () -> new Star(2, 1, 5));
        assertThrows(IllegalArgumentException.class, () -> new Star(2, 5, 2));
        assertThrows(IllegalArgumentException.class, () -> new Star(-1, 5, 5));
        assertThrows(IllegalArgumentException.class, () -> new Star(2, -5, 5));
        assertThrows(IllegalArgumentException.class, () -> new Star(2, 5, -3));
    }
}