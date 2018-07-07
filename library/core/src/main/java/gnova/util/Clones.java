package gnova.util;

import gnova.annotation.Checked;
import gnova.annotation.NotNull;

import java.io.*;
import java.util.Collection;

/**
 * 与克隆对象相关的静态方法
 *
 * @author birderyu
 * @version 1.0.0
 */
public class Clones {

    /**
     * 克隆一个对象
     *
     * @param origin 原始对象，必须实现Serializable，不允许为null
     * @param <T> 克隆对象的类型
     * @return 克隆的对象，不会返回null
     * @throws IOException 若对象无法被序列化，则抛出此异常
     * @throws ClassNotFoundException 若类不存在，则抛出此异常
     */
    @NotNull
    public static <T> T cloneBy(@NotNull Serializable origin)
            throws IOException, ClassNotFoundException {
        return (T) fromBytes(toBytes(origin));
    }

    /**
     * 克隆多个对象并成为一个数组
     *
     * @param origins 原始容器，容器中的每一个元素必须实现Serializable，不允许为null
     * @param a 返回的数组
     * @param <T> 数组元素的类型
     * @return 克隆的数组，不会返回null
     * @throws IOException 若对象无法被序列化，则抛出此异常
     * @throws ClassNotFoundException 若类不存在，则抛出此异常
     */
    @NotNull
    public static <T> T[] cloneBy(@NotNull Collection<? extends Serializable> origins,
                                  @NotNull T[] a)
            throws IOException, ClassNotFoundException {
        int size = origins.size();
        T[] r = a.length >= size ? a :
                (T[])java.lang.reflect.Array
                        .newInstance(a.getClass().getComponentType(), size);
        r = fromBytes(toBytes(origins), r);
        for (int i = size; i < r.length; i++) {
            r[i] = null;
        }
        return r;
    }

    /**
     * 将一个Serializable转化为二进制数组
     *
     * @param origin 原始对象，不允许为null
     * @return 二进制数组，不会返回null
     * @throws IOException 若对象无法被序列化，则抛出此异常
     */
    @NotNull
    private static byte[] toBytes(@NotNull Serializable origin)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(origin);
            return baos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            baos.close();
        }
    }

    /**
     * 将一个Serializable的容器转化为二进制数组
     *
     * @param origins 原始容器，容器中的每一个元素必须实现Serializable，不允许为null
     * @return 二进制数组，不会返回null
     * @throws IOException 若对象无法被序列化，则抛出此异常
     */
    @NotNull
    private static byte[] toBytes(@Checked Collection<? extends Serializable> origins)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            for (Serializable origin : origins) {
                oos.writeObject(origin);
            }
            return baos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            baos.close();
        }
    }

    /**
     * 将一个二进制字节数组转换为对象
     *
     * @param bytes 二进制数组，不允许为null
     * @return 对象
     * @throws IOException 若对象无法被序列化，则抛出此异常
     * @throws ClassNotFoundException 若类不存在，则抛出此异常
     */
    @NotNull
    private static Object fromBytes(@NotNull byte[] bytes)
            throws IOException, ClassNotFoundException {

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } finally {
            if (ois != null) {
                ois.close();
            }
            bais.close();
        }

    }

    /**
     * 将一个二进制字节数组转换为数组
     *
     * @param bytes 二进制数组，不允许为null
     * @param a 数组，数组的元素个数必须与对象的个数完全一致
     * @param <T> 数组中元素的类型
     * @return 数组
     * @throws IOException 若对象无法被序列化，则抛出此异常
     * @throws ClassNotFoundException 若类不存在，则抛出此异常
     */
    private static <T> T[] fromBytes(@NotNull byte[] bytes,
                                     @Checked T[] a)
            throws IOException, ClassNotFoundException {

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            for (int i = 0; i < a.length; i++) {
                a[i] = (T) ois.readObject();
            }
            return a;
        } finally {
            if (ois != null) {
                ois.close();
            }
            bais.close();
        }

    }

    private Clones() {}

}
