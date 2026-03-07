package test.geometry;

import main.geometry.Rectangle;
import main.geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RectangleTest
{
    @Test
    void testConstructorAndGetters()
    {
        Rectangle r = new Rectangle(4, 5);
        assertEquals(4, r.getWidth(), 1e-9);
        assertEquals(5, r.getHeight(), 1e-9);
        assertEquals(new Vector2(0, 0), r.getPosition());

        Vector2 pos = new Vector2(2, 3);
        Rectangle r2 = new Rectangle(pos, 6, 7);
        assertEquals(6, r2.getWidth(), 1e-9);
        assertEquals(7, r2.getHeight(), 1e-9);
        assertEquals(pos, r2.getPosition());
    }

    @Test
    void testAreaAndPerimeter()
    {
        Rectangle r = new Rectangle(3, 4);
        assertEquals(12, r.area(), 1e-9);
        assertEquals(14, r.perimeter(), 1e-9);
    }

    @Test
    void testDiagonalLength()
    {
        Rectangle r = new Rectangle(3, 4);
        assertEquals(5, r.getDiagonalLength(), 1e-9);
    }

    @Test
    void testEquals()
    {
        Rectangle r1 = new Rectangle(4, 5);
        Rectangle r2 = new Rectangle(4, 5);
        Rectangle r3 = new Rectangle(5, 5);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertNotEquals(null, r1);
        assertNotEquals("not a rectangle", r1);
    }

    @Test
    void testToString()
    {
        Rectangle r = new Rectangle(new Vector2(1, 2), 3, 4);
        assertEquals("Rectangle [position=Vector2(1.00, 2.00), width=3.00, height=4.00]", r.toString());
    }

    @Test
    void testInvalidDimensions()
    {
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(0, 2));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(2, 0));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(-3, 5));
        assertThrows(IllegalArgumentException.class, () -> new Rectangle(3, -5));
    }
}