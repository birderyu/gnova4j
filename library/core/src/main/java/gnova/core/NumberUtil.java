package gnova.core;

import gnova.core.annotation.NotNull;

import java.util.regex.Pattern;

public class NumberUtil {

    private static Pattern pattern = null;

    /**
     * 判断
     * @param string
     * @return
     */
    public static boolean isNumber(@NotNull CharSequence string) {
        Pattern p = pattern;
        if (p == null) {
            p = pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        }
        return p.matcher(string).matches();
    }

    /**
     * 尝试将一个对象转换为32位整型数字
     *
     * <p>若转换失败，该方法不会抛出异常，而是返回传入的默认值
     *
     * @param obj 对象
     * @param defaultValue 默认数字值
     * @return 数字值
     */
    public static int tryToInt32(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        try {
            return toInt32(obj);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 将一个对象转换为16位整型数字
     *
     * @param obj 对象，不可以为null
     * @return 数字值
     * @throws NumberFormatException 若转化失败，则抛出此异常
     */
    public static short toInt16(@NotNull Object obj) throws NumberFormatException {
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        } else {
            return Short.valueOf(obj.toString());
        }
    }

    /**
     * 将一个对象转换为32位整型数字
     *
     * @param obj 对象，不可以为null
     * @return 数字值
     * @throws NumberFormatException 若转化失败，则抛出此异常
     */
    public static int toInt32(@NotNull Object obj) throws NumberFormatException {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else {
            return Integer.valueOf(obj.toString());
        }
    }

    /**
     * 将一个对象转换为64位整型数字
     *
     * @param obj 对象，不可以为null
     * @return 数字值
     * @throws NumberFormatException 若转化失败，则抛出此异常
     */
    public static long toInt64(@NotNull Object obj) throws NumberFormatException {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else {
            return Long.valueOf(obj.toString());
        }
    }

    /**
     * 将一个对象转换为单精度浮点型数字
     *
     * @param obj 对象，不可以为null
     * @return 数字值
     * @throws NumberFormatException 若转化失败，则抛出此异常
     */
    public static float toFloat(@NotNull Object obj) throws NumberFormatException {
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        } else {
            return Float.valueOf(obj.toString());
        }
    }

    /**
     * 将一个对象转换为双精度浮点型数字
     *
     * @param obj 对象，不可以为null
     * @return 数字值
     * @throws NumberFormatException 若转化失败，则抛出此异常
     */
    public static double toDouble(@NotNull Object obj) throws NumberFormatException {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else {
            return Double.valueOf(obj.toString());
        }
    }

}
