package test.geometry;

import main.geometry.Circle;
import main.geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CircleTest
{
    @Test
    void testConstructorAndGetters()
    {
        Circle c = new Circle(5);
        assertEquals(5, c.getRadius(), 1e-9);
        assertEquals(new Vector2(0, 0), c.getPosition());

        Vector2 pos = new Vector2(2, 3);
        Circle c2 = new Circle(pos, 7);
        assertEquals(7, c2.getRadius(), 1e-9);
        assertEquals(pos, c2.getPosition());
    }

    @Test
    void testScale()
    {
        Circle c = new Circle(4);
        Circle scaled = c.scale(2);
        assertEquals(8, scaled.getRadius(), 1e-9);
        assertEquals(new Vector2(0, 0), scaled.getPosition());
    }

    @Test
    void testIsBiggerThan()
    {
        Circle c1 = new Circle(3);
        Circle c2 = new Circle(5);
        assertTrue(c2.isBiggerThan(c1));
        assertFalse(c1.isBiggerThan(c2));
    }

    @Test
    void testAreaAndPerimeter()
    {
        Circle c = new Circle(3);
        assertEquals(Math.PI * 3 * 3, c.area(), 1e-9);
        assertEquals(2 * Math.PI * 3, c.perimeter(), 1e-9);
    }

    @Test
    void testEquals()
    {
        Circle c1 = new Circle(4);
        Circle c2 = new Circle(4);
        Circle c3 = new Circle(5);

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertNotEquals(null, c1);
        assertNotEquals("Not a Circle", c1);
    }

    @Test
    void testToString()
    {
        Circle c = new Circle(new Vector2(1, 2), 3);
        assertEquals("Circle(Position: Vector2(1.00, 2.00), Radius: 3.00)", c.toString());
    }

    @Test
    void testInvalidRadius()
    {
        assertThrows(IllegalArgumentException.class, () -> new Circle(0));
        assertThrows(IllegalArgumentException.class, () -> new Circle(-5));
    }
}