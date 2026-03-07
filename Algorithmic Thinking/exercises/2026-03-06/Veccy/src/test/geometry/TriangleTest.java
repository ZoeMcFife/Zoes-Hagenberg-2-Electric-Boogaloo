package test.geometry;

import main.geometry.Triangle;
import main.geometry.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriangleTest
{
    @Test
    void testRightTriangle()
    {
        Triangle t = new Triangle(3, 4, 90);
        assertEquals(3, t.getA(), 1e-9);
        assertEquals(4, t.getB(), 1e-9);
        assertEquals(5, t.getC(), 1e-9); // 3-4-5 right triangle
        assertEquals(6, t.area(), 1e-9);
        assertEquals(12, t.perimeter(), 1e-9);
    }

    @Test
    void testIsoscelesTriangle()
    {
        double side = 5;
        double angleC = 60;
        Triangle t = new Triangle(side, side, angleC);

        // calculate expected side c
        double expectedC = Math.sqrt(side*side + side*side - 2*side*side*Math.cos(Math.toRadians(angleC)));
        assertEquals(side, t.getA(), 1e-9);
        assertEquals(side, t.getB(), 1e-9);
        assertEquals(expectedC, t.getC(), 1e-9);

        double s = t.perimeter() / 2;
        double expectedArea = Math.sqrt(s * (s - t.getA()) * (s - t.getB()) * (s - t.getC()));
        assertEquals(expectedArea, t.area(), 1e-9);
    }

    @Test
    void testScaleneTriangle()
    {
        double a = 4;
        double b = 6;
        double angleC = 50;
        Triangle t = new Triangle(a, b, angleC);

        double expectedC = Math.sqrt(a*a + b*b - 2*a*b*Math.cos(Math.toRadians(angleC)));
        assertEquals(expectedC, t.getC(), 1e-9);

        double s = t.perimeter() / 2;
        double expectedArea = Math.sqrt(s * (s - a) * (s - b) * (s - t.getC()));
        assertEquals(expectedArea, t.area(), 1e-9);
    }

    @Test
    void testEqualsMultipleTriangles()
    {
        Triangle t1 = new Triangle(3, 4, 90);
        Triangle t2 = new Triangle(3, 4, 90);
        Triangle t3 = new Triangle(5, 5, 60);
        Triangle t4 = new Triangle(4, 6, 50);

        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t1, t4);
    }

    @Test
    void testToStringMultipleTriangles()
    {
        Triangle t1 = new Triangle(3, 4, 90);
        Triangle t2 = new Triangle(new Vector2(1, 2), 5, 5, 60);

        assertEquals("Triangle [position=Vector2(0.00, 0.00), a=3.00, b=4.00, c=5.00]", t1.toString());
        assertEquals("Triangle [position=Vector2(1.00, 2.00), a=5.00, b=5.00, c=5.00]", t2.toString()); // approximate check
    }

    @Test
    void testInvalidTriangles()
    {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(0, 4, 90));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(3, 0, 90));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(3, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(3, 4, 180));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(-3, 4, 60));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(3, -4, 60));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(3, 4, -10));
    }
}