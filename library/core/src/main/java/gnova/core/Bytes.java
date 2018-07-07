package gnova.core;

/**
 * 与字节数组相关的静态方法
 *
 * @author birderyu
 * @version 1.0.0
 */
public class Bytes {

    /**
     * 大端字节序
     */
    public static final int BIG_ENDIAN = 0;

    /**
     * 小端字节序
     */
    public static final int LITTLE_ENDIAN = 1;

    /**
     * 网络字节序
     *
     * 网络字节序为大端字节序
     */
    public static final int NETWORK_BYTE_ORDER = BIG_ENDIAN;

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(byte val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(byte val, int endian) {
        byte[] b = new byte[1];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(char val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(char val, int endian) {
        byte[] b = new byte[2];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(short val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(short val, int endian) {
        byte[] b = new byte[2];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(int val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(int val, int endian) {
        byte[] b = new byte[4];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(long val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(long val, int endian) {
        byte[] b = new byte[8];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(float val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(float val, int endian) {
        byte[] b = new byte[4];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 转换为大端排序的字节数组
     *
     * @param val 值
     * @return 字节数组
     */
    public static byte[] toBytes(double val) {
        return toBytes(val, BIG_ENDIAN);
    }

    /**
     * 转换为字节数组
     *
     * @param val 值
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 字节数组
     */
    public static byte[] toBytes(double val, int endian) {
        byte[] b = new byte[8];
        attachToBytes(val, b, 0, endian);
        return b;
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(byte val, byte[] b, int from, int endian) {
        b[from++] = val;
        return from;
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(char val, byte[] b, int from, int endian) {
        return attachToBytes((short) val, b, from, endian);
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(short val, byte[] b, int from, int endian) {
        if (endian == LITTLE_ENDIAN) {
            // 小端
            b[from++] = (byte) (val & 0xFF);
            b[from++] = (byte) ((val >> 8) & 0xFF);
        } else {
            // 大端
            b[from++] = (byte) ((val >> 8) & 0xFF);
            b[from++] = (byte) (val & 0xFF);
        }
        return from;
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(int val, byte[] b, int from, int endian) {
        if (endian == LITTLE_ENDIAN) {
            // 小端
            b[from++] = (byte) (val & 0xFF);
            b[from++] = (byte) ((val >> 8) & 0xFF);
            b[from++] = (byte) ((val >> 16) & 0xFF);
            b[from++] = (byte) ((val >> 24) & 0xFF);
        } else {
            // 大端
            b[from++] = (byte) ((val >> 24) & 0xFF);
            b[from++] = (byte) ((val >> 16) & 0xFF);
            b[from++] = (byte) ((val >> 8) & 0xFF);
            b[from++] = (byte) (val & 0xFF);
        }
        return from;
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(long val, byte[] b, int from, int endian) {
        if (endian == LITTLE_ENDIAN) {
            // 小端
            b[from++] = (byte) (val & 0xFF);
            b[from++] = (byte) ((val >> 8) & 0xFF);
            b[from++] = (byte) ((val >> 16) & 0xFF);
            b[from++] = (byte) ((val >> 24) & 0xFF);
            b[from++] = (byte) ((val >> 32) & 0xFF);
            b[from++] = (byte) ((val >> 40) & 0xFF);
            b[from++] = (byte) ((val >> 48) & 0xFF);
            b[from++] = (byte) ((val >> 56) & 0xFF);
        } else {
            // 大端
            b[from++] = (byte) ((val >> 56) & 0xFF);
            b[from++] = (byte) ((val >> 48) & 0xFF);
            b[from++] = (byte) ((val >> 40) & 0xFF);
            b[from++] = (byte) ((val >> 32) & 0xFF);
            b[from++] = (byte) ((val >> 24) & 0xFF);
            b[from++] = (byte) ((val >> 16) & 0xFF);
            b[from++] = (byte) ((val >> 8) & 0xFF);
            b[from++] = (byte) (val & 0xFF);
        }
        return from;
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(float val, byte[] b, int from, int endian) {
        return attachToBytes(Float.floatToIntBits(val), b, from, endian);
    }

    /**
     * 将一个值添加到字节数组的某一个位置，返回添加值之后值位于字节数组的结束位置的下一个下标
     *
     * @param val 值
     * @param b 字节数组
     * @param from 起始元素的下标，将值添加到该位置，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 添加值到字节数组之后，返回值的结束下标的下一个下标
     */
    public static int attachToBytes(double val, byte[] b, int from, int endian) {
        return attachToBytes(Double.doubleToRawLongBits(val), b, from, endian);
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toByte(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static byte toByte(byte[] b, int endian) {
        return detachByteFromBytes(b, 0, endian).get1st();
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toChar(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static char toChar(byte[] b, int endian) {
        return detachCharFromBytes(b, 0, endian).get1st();
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toShort(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static short toShort(byte[] b, int endian) {
        return detachShortFromBytes(b, 0, endian).get1st();
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toInt(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static int toInt(byte[] b, int endian) {
        return detachIntFromBytes(b, 0, endian).get1st();
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toLong(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static long toLong(byte[] b, int endian) {
        return detachLongFromBytes(b, 0, endian).get1st();
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toFloat(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static float toFloat(byte[] b, int endian) {
        return detachFloatFromBytes(b, 0, endian).get1st();
    }

    /**
     * 将大端排序的字节数组转换为值
     *
     * @param b 字节数组
     * @return 值
     */
    public static byte toDouble(byte[] b) {
        return toByte(b, BIG_ENDIAN);
    }

    /**
     * 将字节数组转化为值
     *
     * @param b 字节数组
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 值
     */
    public static double toDouble(byte[] b, int endian) {
        return detachDoubleFromBytes(b, 0, endian).get1st();
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachByteFromBytes(byte[] b, int from, int endian) {
        return Tuple.of2(b[from++], from);
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachCharFromBytes(byte[] b, int from, int endian) {
        Tuple tuple = detachShortFromBytes(b, from, endian);
        return Tuple.of2((char) (short) tuple.get1st(), tuple.get2nd());
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachShortFromBytes(byte[] b, int from, int endian) {
        short val = 0;
        if (endian == LITTLE_ENDIAN) {
            // 小端
            val |= ( (short) b[from++]        & 0x00ff);
            val |= (((short) b[from++] << 8)  & 0xff00);
        } else {
            // 大端
            val |= (((short) b[from++] << 8)  & 0xff00);
            val |= ( (short) b[from++]        & 0x00ff);
        }
        return Tuple.of2(val, from);
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachIntFromBytes(byte[] b, int from, int endian) {
        int val = 0;
        if (endian == LITTLE_ENDIAN) {
            // 小端
            val |= ( (int) b[from++]        & 0x000000ff);
            val |= (((int) b[from++] << 8)  & 0x0000ff00);
            val |= (((int) b[from++] << 16) & 0x00ff0000);
            val |= (((int) b[from++] << 24) & 0xff000000);
        } else {
            // 大端
            val |= (((int) b[from++] << 24) & 0xff000000);
            val |= (((int) b[from++] << 16) & 0x00ff0000);
            val |= (((int) b[from++] << 8)  & 0x0000ff00);
            val |= ( (int) b[from++]        & 0x000000ff);
        }
        return Tuple.of2(val, from);
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachLongFromBytes(byte[] b, int from, int endian) {
        long val = 0;
        if (endian == LITTLE_ENDIAN) {
            // 小端
            val |= ( (long) b[from++]        & 0x00000000000000ffL);
            val |= (((long) b[from++] << 8)  & 0x000000000000ff00L);
            val |= (((long) b[from++] << 16) & 0x0000000000ff0000L);
            val |= (((long) b[from++] << 24) & 0x00000000ff000000L);
            val |= (((long) b[from++] << 32) & 0x000000ff00000000L);
            val |= (((long) b[from++] << 40) & 0x0000ff0000000000L);
            val |= (((long) b[from++] << 48) & 0x00ff000000000000L);
            val |= (((long) b[from++] << 56) & 0xff00000000000000L);
        } else {
            // 大端
            val |= (((long) b[from++] << 56) & 0xff00000000000000L);
            val |= (((long) b[from++] << 48) & 0x00ff000000000000L);
            val |= (((long) b[from++] << 40) & 0x0000ff0000000000L);
            val |= (((long) b[from++] << 32) & 0x000000ff00000000L);
            val |= (((long) b[from++] << 24) & 0x00000000ff000000L);
            val |= (((long) b[from++] << 16) & 0x0000000000ff0000L);
            val |= (((long) b[from++] << 8)  & 0x000000000000ff00L);
            val |= ( (long) b[from++]        & 0x00000000000000ffL);
        }
        return Tuple.of2(val, from);
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachFloatFromBytes(byte[] b, int from, int endian) {
        Tuple tuple = detachIntFromBytes(b, from, endian);
        return Tuple.of2(Float.intBitsToFloat(tuple.get1st()), tuple.get2nd());
    }

    /**
     * 从字节数组的某一个位置开始将其转换成值，返回值所占据字节数组的结束位置的下一个下标
     *
     * @param b 字节数组
     * @param from 起始元素的下标，从该位置开始进行转换，包含该下标
     * @param endian 字节顺序，大端{@link #BIG_ENDIAN}或小端{@link #LITTLE_ENDIAN}
     * @return 返回一个包含两个元素的元组，首位元素为转换的值，
     *          第二位元素为该值所占据字节数组的结束位置的下一个下标
     */
    public static Tuple detachDoubleFromBytes(byte[] b, int from, int endian) {
        Tuple tuple = detachLongFromBytes(b, from, endian);
        return Tuple.of2(Double.longBitsToDouble(tuple.get1st()), tuple.get2nd());
    }

}
