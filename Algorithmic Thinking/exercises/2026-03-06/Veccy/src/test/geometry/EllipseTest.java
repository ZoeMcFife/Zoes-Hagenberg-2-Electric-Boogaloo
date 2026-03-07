package test.geometry;

import main.geometry.Ellipse;
import main.geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EllipseTest
{
    @Test
    void testConstructorAndGetters()
    {
        Ellipse e = new Ellipse(5, 3);
        assertEquals(5, e.getSemiMajorAxis(), 1e-9);
        assertEquals(3, e.getSemiMinorAxis(), 1e-9);
        assertEquals(new Vector2(0, 0), e.getPosition());

        Vector2 pos = new Vector2(2, 4);
        Ellipse e2 = new Ellipse(pos, 6, 2);
        assertEquals(6, e2.getSemiMajorAxis(), 1e-9);
        assertEquals(2, e2.getSemiMinorAxis(), 1e-9);
        assertEquals(pos, e2.getPosition());
    }

    @Test
    void testArea()
    {
        Ellipse e = new Ellipse(4, 2);
        assertEquals(Math.PI * 4 * 2, e.area(), 1e-9);
    }

    @Test
    void testPerimeter()
    {
        Ellipse e = new Ellipse(3, 2);
        double expected = Math.PI * (3 * (3 + 2) - Math.sqrt((3 * 3 + 2) * (3 + 3 * 2)));
        assertEquals(expected, e.perimeter(), 1e-9);
    }

    @Test
    void testEquals()
    {
        Ellipse e1 = new Ellipse(5, 3);
        Ellipse e2 = new Ellipse(5, 3);
        Ellipse e3 = new Ellipse(6, 3);

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertNotEquals(null, e1);
        assertNotEquals("Ellipse", e3);
    }

    @Test
    void testToString()
    {
        Ellipse e = new Ellipse(new Vector2(1, 2), 5, 3);
        assertEquals("Ellipse(semiMajorAxis=5.00, semiMinorAxis=3.00, position=Vector2(1.00, 2.00))", e.toString());
    }

    @Test
    void testInvalidAxes()
    {
        assertThrows(IllegalArgumentException.class, () -> new Ellipse(0, 3));
        assertThrows(IllegalArgumentException.class, () -> new Ellipse(3, 0));
        assertThrows(IllegalArgumentException.class, () -> new Ellipse(-5, 3));
        assertThrows(IllegalArgumentException.class, () -> new Ellipse(5, -2));
    }
}