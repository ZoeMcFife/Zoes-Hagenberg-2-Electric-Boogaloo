package geometry;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Vector2Test
{
    @Test
    void testAdd()
    {
        Vector2 v1 = new Vector2(2, 3);
        Vector2 v2 = new Vector2(4, -1);
        Vector2 result = v1.add(v2);
        assertEquals(new Vector2(6, 2), result);
    }

    @Test
    void testSubtract()
    {
        Vector2 v1 = new Vector2(5, 7);
        Vector2 v2 = new Vector2(2, 3);
        Vector2 result = v1.subtract(v2);
        assertEquals(new Vector2(3, 4), result);
    }

    @Test
    void testScale()
    {
        Vector2 v = new Vector2(3, -4);
        Vector2 result = v.scale(2);
        assertEquals(new Vector2(6, -8), result);

        result = v.scale(0);
        assertEquals(Vector2.ZERO, result);
    }

    @Test
    void testDot()
    {
        Vector2 v1 = new Vector2(1, 2);
        Vector2 v2 = new Vector2(3, 4);
        assertEquals(11, v1.dot(v2), 1e-9);
    }

    @Test
    void testMagnitude()
    {
        Vector2 v = new Vector2(3, 4);
        assertEquals(5, v.magnitude(), 1e-9);
    }

    @Test
    void testNormalize()
    {
        Vector2 v = new Vector2(3, 4);
        Vector2 normalized = v.normalize();
        assertEquals(1.0, normalized.magnitude(), 1e-9);
        assertEquals(new Vector2(0, 0), Vector2.ZERO.normalize());
    }

    @Test
    void testDistanceTo()
    {
        Vector2 v1 = new Vector2(0, 0);
        Vector2 v2 = new Vector2(3, 4);
        assertEquals(5, v1.distanceTo(v2), 1e-9);
        assertEquals(0, v1.distanceTo(v1), 1e-9);
    }

    @Test
    void testEquals()
    {
        Vector2 v1 = new Vector2(1, 1);
        Vector2 v2 = new Vector2(1, 1);
        Vector2 v3 = new Vector2(1, 2);

        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(null, v1);
        assertNotEquals("Not a Vector2", v1);
    }

    @Test
    void testToString()
    {
        Vector2 v = new Vector2(1.2345, 6.789);
        assertEquals("Vector2(1.23, 6.79)", v.toString());
    }
}