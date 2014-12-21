package Utils;

/**
 *
 * @author Алексей Евсеев
 */
public class Utils {

    private static long a1 = 1;
    private static long a2 = 0;
    private static long a3 = 0;
    private static final long b1 = 11973;
    private static final long b2 = 2800;
    private static final long b3 = 2842;
    private static final double x1 = 1.0/4096.0;
    private static final double x2 = x1/16384.0;
    private static final double x3 = x2/16384.0;

    public static double getRandom()
    {
        long c11, c12, c13, c21, c22, c31, d1, d2, d3;

        c11 = b1*a1;  c12 = b1*a2;  c13 = b1*a3;
        c21 = b2*a1;  c22 = b2*a2;
        c31 = b3*a1;
        d1 = c11;
        d2 = c21 + c12 + (d1 >> 14);
        d3 = c31 + c22 + c13 + (d2 >> 14);
        a1=d1 & 16383;  a2=d2 & 16383;  a3=d3 & 4095;
    /*    a1=d1 % 16384;  a2=d2 % 16384;  a3=d3 % 4096;*/
        return (a3*x1 + a2*x2 + a1*x3);
    }
}
