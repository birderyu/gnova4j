package gnova.core;

import gnova.core.annotation.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数组的帮助类
 *
 * @author birderyu
 * @version 1.0.0
 */
public class ArrayUtil {

    /**
     * 返回一个移除数组指定下标的元素生成的新数组
     *
     * @param a 数组
     * @param pos 数组的下标，从0开始计数，同时必须小于数组的长度
     * @param <A> 数组的类型
     * @return 移除指定下标之后生成的新数组
     * @throws IllegalArgumentException 若参数并非一个数组，则抛出此异常
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于等于数组的长度，则抛出此异常
     */
    public static <A> A removeAt(@NotNull A a, int pos)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        int l = Array.getLength(a);
        if (pos < 0 || pos >= l) {
            throw new IndexOutOfBoundsException();
        }
        A na = (A) Array.newInstance(a.getClass().getComponentType(), l - 1);
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos + 1, na, pos, l - pos - 1);
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * 如数组['A', 'B', 'C', 'D']，现将一个元素'E'添加到下标为2的位置上，那么添加完成后的数组为；
     * ['A', 'B', 'E', 'C', 'D']
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @param <A> 数组的类型
     * @param <E> 元素的类型
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IllegalArgumentException 若参数并非一个数组，则抛出此异常
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static <A, E> A addAt(@NotNull A a, E e, int pos)
            throws IllegalArgumentException, IndexOutOfBoundsException {
        int l = Array.getLength(a);
        if (pos < 0 || pos > l) {
            throw new IndexOutOfBoundsException();
        }
        A na = (A) Array.newInstance(a.getClass().getComponentType(), l + 1);
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, l - pos);
        Array.set(na, pos, e);
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static boolean[] addAt(@NotNull boolean[] a, boolean e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        boolean[] na = new boolean[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static byte[] addAt(@NotNull byte[] a, byte e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        byte[] na = new byte[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static char[] addAt(@NotNull char[] a, char e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        char[] na = new char[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static short[] addAt(@NotNull short[] a, short e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        short[] na = new short[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static int[] addAt(@NotNull int[] a, int e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        int[] na = new int[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static long[] addAt(@NotNull long[] a, long e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        long[] na = new long[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static float[] addAt(@NotNull float[] a, float e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        float[] na = new float[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个元素添加到数组指定的下标位置，添加成功后这个元素的下标将会是指定的下标
     *
     * @param a 数组，不允许为null
     * @param e 元素
     * @param pos 数组的下标，从0开始计数，最大不能超过数组的长度
     * @return 添加元素到数组指定的下标之后生成的新数组
     * @throws IndexOutOfBoundsException 若移除元素的下标小于0或者大于数组的长度，则抛出此异常
     */
    public static double[] addAt(@NotNull double[] a, double e, int pos)
            throws IndexOutOfBoundsException {
        if (pos < 0 || pos > a.length) {
            throw new IndexOutOfBoundsException();
        }
        double[] na = new double[a.length + 1];
        System.arraycopy(a, 0, na, 0, pos);
        System.arraycopy(a, pos, na, pos + 1, a.length - pos);
        na[pos] = e;
        return na;
    }

    /**
     * 将一个基本类型的数组封装成为包装类的数组
     * 如int[]将被转换成Integer[]
     *
     * @param a 基本类型的数组
     * @param <T> 需要转换成的包装类
     * @return 包装类的数组对象
     * @throws IllegalArgumentException 若参数并非一个数组，则抛出此异常
     * @throws ClassCastException 若转换失败，则抛出此异常
     */
    public static <T> T[] boxing(@NotNull Object a)
            throws IllegalArgumentException, ClassCastException {
        Class<?> c = a.getClass();
        if (!c.isArray()) {
            throw new IllegalArgumentException("参数必须是数组");
        }
        Class<?> cc = c.getComponentType();
        if (cc == boolean.class) {
            return (T[]) boxing((boolean[]) a);
        } else if (cc == byte.class) {
            return (T[]) boxing((byte[]) a);
        } else if (cc == char.class) {
            return (T[]) boxing((char[]) a);
        } else if (cc == short.class) {
            return (T[]) boxing((short[]) a);
        } else if (cc == int.class) {
            return (T[]) boxing((int[]) a);
        } else if (cc == long.class) {
            return (T[]) boxing((long[]) a);
        } else if (cc == float.class) {
            return (T[]) boxing((float[]) a);
        } else if (cc == double.class) {
            return (T[]) boxing((double[]) a);
        } else {
            return (T[]) a;
        }
    }

    /**
     * 将一个布尔类型的数组封装成布尔类型包装类的数组
     *
     * @param ba 数组
     * @return 数组
     */
    public static Boolean[] boxing(@NotNull boolean[] ba) {
        Boolean[] a = new Boolean[ba.length];
        for (int i = 0; i < ba.length; i++) {
            a[i] = ba[i];
        }
        return a;
    }

    /**
     * 将一个字节类型的数组封装成字节类型包装类的数组
     *
     * @param ba 数组
     * @return 数组
     */
    public static Byte[] boxing(@NotNull byte[] ba) {
        Byte[] a = new Byte[ba.length];
        for (int i = 0; i < ba.length; i++) {
            a[i] = ba[i];
        }
        return a;
    }

    /**
     * 将一个字符类型的数组封装成字符类型包装类的数组
     *
     * @param ca 数组
     * @return 数组
     */
    public static Character[] boxing(@NotNull char[] ca) {
        Character[] a = new Character[ca.length];
        for (int i = 0; i < ca.length; i++) {
            a[i] = ca[i];
        }
        return a;
    }

    /**
     * 将一个短整型的数组封装成短整型包装类的数组
     *
     * @param sa 数组
     * @return 数组
     */
    public static Short[] boxing(@NotNull short[] sa) {
        Short[] a = new Short[sa.length];
        for (int i = 0; i < sa.length; i++) {
            a[i] = sa[i];
        }
        return a;
    }

    /**
     * 将一个整型的数组封装成整型包装类的数组
     *
     * @param ia 数组
     * @return 数组
     */
    public static Integer[] boxing(@NotNull int[] ia) {
        Integer[] a = new Integer[ia.length];
        for (int i = 0; i < ia.length; i++) {
            a[i] = ia[i];
        }
        return a;
    }

    /**
     * 将一个长整型的数组封装成长整型包装类的数组
     *
     * @param la 数组
     * @return 数组
     */
    public static Long[] boxing(@NotNull long[] la) {
        Long[] a = new Long[la.length];
        for (int i = 0; i < la.length; i++) {
            a[i] = la[i];
        }
        return a;
    }

    /**
     * 将一个浮点型的数组封装成浮点型包装类的数组
     *
     * @param fa 数组
     * @return 数组
     */
    public static Float[] boxing(@NotNull float[] fa) {
        Float[] a = new Float[fa.length];
        for (int i = 0; i < fa.length; i++) {
            a[i] = fa[i];
        }
        return a;
    }

    /**
     * 将一个双精度浮点型的数组封装成双精度浮点型包装类的数组
     *
     * @param da 数组
     * @return 数组
     */
    public static Double[] boxing(@NotNull double[] da) {
        Double[] a = new Double[da.length];
        for (int i = 0; i < da.length; i++) {
            a[i] = da[i];
        }
        return a;
    }

    /**
     * 将一个基本类型包装类的数组解封成为基本类型的数组
     * 如Integer[]将被转换成int[]
     *
     * @param a 基本类型包装类的数组
     * @param <T> 基本类型包装类
     * @return 基本类型的数组对象
     * @throws IllegalArgumentException 若参数并非一个数组，则抛出此异常
     */
    public static <T> Object unboxing(@NotNull T[] a) throws IllegalArgumentException {
        Class<?> c = a.getClass();
        if (!c.isArray()) {
            throw new IllegalArgumentException("参数必须是数组");
        }
        Class<?> cc = c.getComponentType();
        if (cc == Boolean.class) {
            return unboxing((Boolean[]) a);
        } else if (cc == Byte.class) {
            return unboxing((Byte[]) a);
        } else if (cc == Character.class) {
            return unboxing((Character[]) a);
        } else if (cc == Short.class) {
            return unboxing((Short[]) a);
        } else if (cc == Integer.class) {
            return unboxing((Integer[]) a);
        } else if (cc == Long.class) {
            return unboxing((Long[]) a);
        } else if (cc == Float.class) {
            return unboxing((Float[]) a);
        } else if (cc == Double.class) {
            return unboxing((Double[]) a);
        } else {
            return a;
        }
    }

    /**
     * 将一个布尔类型包装类的数组解封成布尔类型的数组
     *
     * @param ba 数组
     * @return 数组
     */
    public static boolean[] unboxing(@NotNull Boolean[] ba) {
        boolean[] a = new boolean[ba.length];
        for (int i = 0; i < ba.length; i++) {
            a[i] = ba[i];
        }
        return a;
    }

    /**
     * 将一个字节类型包装类的数组解封成字节类型的数组
     *
     * @param ba 数组
     * @return 数组
     */
    public static byte[] unboxing(@NotNull Byte[] ba) {
        byte[] a = new byte[ba.length];
        for (int i = 0; i < ba.length; i++) {
            a[i] = ba[i];
        }
        return a;
    }

    /**
     * 将一个字符类型包装类的数组解封成字符类型的数组
     *
     * @param ca 数组
     * @return 数组
     */
    public static char[] unboxing(@NotNull Character[] ca) {
        char[] a = new char[ca.length];
        for (int i = 0; i < ca.length; i++) {
            a[i] = ca[i];
        }
        return a;
    }

    /**
     * 将一个短整型包装类的数组解封成短整型的数组
     *
     * @param sa 数组
     * @return 数组
     */
    public static short[] unboxing(@NotNull Short[] sa) {
        short[] a = new short[sa.length];
        for (int i = 0; i < sa.length; i++) {
            a[i] = sa[i];
        }
        return a;
    }

    /**
     * 将一个整型包装类的数组解封成整型的数组
     *
     * @param ia 数组
     * @return 数组
     */
    public static int[] unboxing(@NotNull Integer[] ia) {
        int[] a = new int[ia.length];
        for (int i = 0; i < ia.length; i++) {
            a[i] = ia[i];
        }
        return a;
    }

    /**
     * 将一个长整型包装类的数组解封成长整型的数组
     *
     * @param la 数组
     * @return 数组
     */
    public static long[] unboxing(@NotNull Long[] la) {
        long[] a = new long[la.length];
        for (int i = 0; i < la.length; i++) {
            a[i] = la[i];
        }
        return a;
    }

    /**
     * 将一个浮点型包装类的数组解封成浮点型的数组
     *
     * @param fa 数组
     * @return 数组
     */
    public static float[] unboxing(@NotNull Float[] fa) {
        float[] a = new float[fa.length];
        for (int i = 0; i < fa.length; i++) {
            a[i] = fa[i];
        }
        return a;
    }

    /**
     * 将一个双精度浮点型包装类的数组解封成双精度浮点型的数组
     *
     * @param da 数组
     * @return 数组
     */
    public static double[] unboxing(@NotNull Double[] da) {
        double[] a = new double[da.length];
        for (int i = 0; i < da.length; i++) {
            a[i] = da[i];
        }
        return a;
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull boolean[] a1, @NotNull Boolean[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Boolean[] a1, @NotNull boolean[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull byte[] a1, @NotNull Byte[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Byte[] a1, @NotNull byte[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull char[] a1, @NotNull Character[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Character[] a1, @NotNull char[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull short[] a1, @NotNull Short[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Short[] a1, @NotNull short[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull int[] a1, @NotNull Integer[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Integer[] a1, @NotNull int[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull long[] a1, @NotNull Long[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Long[] a1, @NotNull long[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull float[] a1, @NotNull Float[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Float[] a1, @NotNull float[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull double[] a1, @NotNull Double[] a2) {
        return Arrays.equals(a1, unboxing(a2));
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a1 第一个数组
     * @param a2 第二个数组
     * @return 若两个数组相等，则返回true，否则返回false
     */
    public static boolean equals(@NotNull Double[] a1, @NotNull double[] a2) {
        return Arrays.equals(unboxing(a1), a2);
    }

}
