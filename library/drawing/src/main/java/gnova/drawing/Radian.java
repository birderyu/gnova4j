package gnova.drawing;

/**
 * 与弧度转换有关的方法
 *
 * @author birderyu
 * @version 1.0.0
 */
public class Radian {

    /**
     * 弧度转角度
     *
     * @param r 弧度值
     * @return 角度值
     */
    public static float radian2Angle(float r) {
        return (float) (r / Math.PI * 180.0);
    }

    /**
     * 弧度转角度
     *
     * @param r 弧度值
     * @return 角度值
     */
    public static double radian2Angle(double r) {
        return r / Math.PI * 180.0;
    }

    /**
     * 角度转弧度
     *
     * @param a 角度值
     * @return 弧度值
     */
    public static float angle2Radian(float a) {
        return (float) (a * Math.PI / 180.0);
    }

    /**
     * 角度转弧度
     *
     * @param a 角度值
     * @return 弧度值
     */
    public static double angle2Radian(double a) {
        return a * Math.PI / 180.0;
    }

}
