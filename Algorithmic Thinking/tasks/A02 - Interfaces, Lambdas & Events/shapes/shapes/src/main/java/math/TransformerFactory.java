package math;

import javax.xml.transform.Transformer;

public class TransformerFactory
{
    private static Matrix3 createTranslation(double dx, double dy)
    {
        return new Matrix3(new double[][]
                {
                        {1, 0, dx},
                        {0, 1, dy},
                        {0, 0, 1}
                });
    }

    public static Matrix3 createRotation(double radians)
    {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Matrix3(new double[][]
                {
                        {cos, -sin, 0},
                        {sin, cos, 0},
                        {0, 0, 1}
                });
    }
}
