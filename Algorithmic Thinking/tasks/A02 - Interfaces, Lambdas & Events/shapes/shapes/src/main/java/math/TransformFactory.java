package math;

public class TransformFactory
{
    public static Matrix3 createTranslation(double dx, double dy)
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

    public static Matrix3 createScaling(double fx, double fy)
    {
        return new Matrix3(new double[][]
                {
                        {fx, 0, 0},
                        {0, fy, 0},
                        {0, 0, 1}
                });
    }

    public static Matrix3 createHorizontalMirroring()
    {
        return new Matrix3(new double[][]
                {
                        {1, 0, 0},
                        {0, -1, 0},
                        {0, 0, 1}
                });
    }

    public static Matrix3 createVerticalMirroring()
    {
        return new Matrix3(new double[][]
                {
                        {-1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                });
    }
}
