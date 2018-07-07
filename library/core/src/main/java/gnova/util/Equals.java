package gnova.util;

/**
 * 判断两个值是否相等的静态方法
 *
 * @author birderyu
 * @version 1.0.0
 */
public class Equals {

    /**
     * 静态方法类，不允许构造
     */
    private Equals() {}

    /**
     * 判断两个浮点数字是否相等
     *
     * @param f1 浮点数字1
     * @param f2 浮点数字2
     * @param tolerance 容差
     * @return 若两个值之差小于容差，则认为二者相等
     */
    public static boolean floatEquals(float f1, float f2, float tolerance) {
        return Math.abs(f1 - f2) <= Math.abs(tolerance);
    }

    /**
     * 判断两个双精度浮点数字是否相等
     *
     * @param d1 双精度浮点数字1
     * @param d2 双精度浮点数字2
     * @param tolerance 容差
     * @return 若两个值之差小于容差，则认为二者相等
     */
    public static boolean doubleEquals(double d1, double d2, double tolerance) {
        return Math.abs(d1 - d2) <= Math.abs(tolerance);
    }

}
