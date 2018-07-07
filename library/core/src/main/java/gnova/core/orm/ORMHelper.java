package gnova.core.orm;

import gnova.core.annotation.NotNull;
import gnova.core.ArrayUtil;
import gnova.core.ArrayIterator;
import gnova.core.SingleIterator;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * ORM的帮助类
 *
 * @author birderyu
 * @version 1.0.0
 */
public class ORMHelper {

    /**
     * 将一个对象转换为布尔值
     *
     * @param obj 对象
     * @return 布尔值
     */
    public static Boolean toBoolean(@NotNull Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        } else {
            return Boolean.valueOf(obj.toString());
        }
    }

    /**
     * 将一个对象转换为字节值
     *
     * @param obj 对象
     * @return 字节值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Byte toByte(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Number) {
            return ((Number) obj).byteValue();
        } else {
            try {
                return Byte.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }

        }
    }

    /**
     * 将一个对象转换为字符值
     *
     * @param obj 对象
     * @return 字符值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Character toCharacter(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Character) {
            return (Character) obj;
        } else {
            String s = obj.toString();
            if (s == null || s.isEmpty()) {
                throw new ORMException("不支持的转换：从对象" + obj + "转换为字符。");
            }
            return Character.valueOf(s.charAt(0));
        }

    }

    /**
     * 将一个对象转换为短整型值
     *
     * @param obj 对象
     * @return 短整型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Short toShort(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        } else {
            try {
                return Short.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为整型值
     *
     * @param obj 对象
     * @return 整型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Integer toInteger(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else {
            try {
                return Integer.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为短整型值
     *
     * @param obj 对象
     * @return 短整型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Long toLong(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        } else {
            try {
                return Long.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为浮点型值
     *
     * @param obj 对象
     * @return 浮点型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Float toFloat(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        } else {
            try {
                return Float.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为双精度浮点型值
     *
     * @param obj 对象
     * @return 双精度浮点型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static Double toDouble(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else {
            try {
                return Double.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为大整型值
     *
     * @param obj 对象
     * @return 大整型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static BigInteger toBigInteger(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof BigInteger) {
            return (BigInteger) obj;
        } else {
            try {
                return new BigInteger(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为高精度浮点型值
     *
     * @param obj 对象
     * @return 高精度浮点型值
     * @throws ORMException 若转换失败，则抛出此异常
     */
    public static BigDecimal toBigDecimal(@NotNull Object obj)
            throws ORMException {
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else {
            try {
                return new BigDecimal(obj.toString());
            } catch (NumberFormatException e) {
                throw new ORMException(e);
            }
        }
    }

    /**
     * 将一个对象转换为字符串值
     *
     * @param obj 对象
     * @return 字符串值
     */
    public static String toString(@NotNull Object obj) {
        if (obj instanceof String) {
            return obj.toString();
        } else if (obj.getClass().isArray()) {
            Class<?> cc = obj.getClass().getComponentType();
            if (cc == char.class) {
                return new String((char[]) obj).intern();
            } else if (cc == Character.class) {
                Character[] oc = (Character[]) obj;
                char[] cs = new char[oc.length];
                for (int i = 0; i < oc.length; i++) {
                    cs[i] = oc[i];
                }
                return new String((char[]) obj).intern();
            } else if (cc == boolean.class) {
                return java.util.Arrays.toString((boolean[]) obj);
            } else if (cc == byte.class) {
                return java.util.Arrays.toString((byte[]) obj);
            } else if (cc == short.class) {
                return java.util.Arrays.toString((short[]) obj);
            } else if (cc == int.class) {
                return java.util.Arrays.toString((int[]) obj);
            } else if (cc == long.class) {
                return java.util.Arrays.toString((long[]) obj);
            } else if (cc == float.class) {
                return java.util.Arrays.toString((float[]) obj);
            } else if (cc == double.class) {
                return java.util.Arrays.toString((double[]) obj);
            } else {
                Object[] a = (Object[]) obj;
                int iMax = a.length - 1;
                if (iMax == -1)
                    return "[]";

                StringBuilder b = new StringBuilder();
                b.append('[');
                for (int i = 0; ; i++) {
                    b.append(toString(a[i]));
                    if (i == iMax)
                        return b.append(']').toString();
                    b.append(", ");
                }
            }
        } else {
            return obj.toString();
        }
    }

    /**
     * 将一个对象转换为列表
     *
     * @param obj 对象
     * @return 列表
     */
    public static List toList(@NotNull Object obj) {
        if (obj instanceof List) {
            return (List) obj;
        }
        Iterator i = toIterator(obj);
        List l = new ArrayList();
        while (i.hasNext()) {
            l.add(i.next());
        }
        return l;
    }

    /**
     * 将一个对象转换为集合
     *
     * @param obj 对象
     * @return 集合
     */
    public static Set toSet(@NotNull Object obj) {
        if (obj instanceof Set) {
            return (Set) obj;
        }
        Iterator i = toIterator(obj);
        Set l = new HashSet();
        while (i.hasNext()) {
            l.add(i.next());
        }
        return l;
    }

    /**
     * 将一个对象转换为迭代器
     *
     * @param obj 对象
     * @return 迭代器
     */
    @NotNull
    public static Iterator toIterator(@NotNull Object obj) {
        if (obj.getClass().isArray()) {
            // 如果对象是数组，需要将数组装箱
            return new ArrayIterator(ArrayUtil.boxing(obj));
        } else if (obj instanceof Iterable) {
            // 可迭代的
            return ((Iterable) obj).iterator();
        } else if (obj instanceof Iterator) {
            // 迭代器
            return (Iterator) obj;
        } else {
            // 将对象封装到迭代器中
            return new SingleIterator(obj);
        }
    }

    /**
     * 将一个值写入对象的相应字段中
     *
     * 写入时会调用该字段的setter方法，若该字段无对应的Setter方法，则不会写入
     *
     * @param instance 对象的实例
     * @param clazz 对象的类型
     * @param field 需要写入的字段
     * @param value 需要写入的字段的值
     * @param <T> 对象的类型
     * @throws ORMException 若写入失败，则抛出此异常
     */
    public static <T> void writeField(T instance, Class<? extends T> clazz,
                                      Field field, Object value)
            throws ORMException {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method method = pd.getWriteMethod();
            if (method == null) {
                return;
            }
            method.invoke(instance, value);
        } catch (IntrospectionException e) {
            throw new ORMException(e);
        } catch (IllegalAccessException e) {
            throw new ORMException(e);
        } catch (InvocationTargetException e) {
            throw new ORMException(e);
        }
    }
}
