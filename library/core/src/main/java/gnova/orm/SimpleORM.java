package gnova.orm;

import gnova.annotation.NotNull;
import gnova.util.ArrayUtil;
import gnova.util.KeyValue;
import gnova.util.Dictionary;
import gnova.util.MapDictionary;
import gnova.function.Getter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 一个简单的ORM实现
 *
 * @see ORM
 * @author birderyu
 * @version 1.0.0
 */
public class SimpleORM
        implements ORM {

    /**
     * 将一个文档转换成为对象
     *
     * @param doc 文档，支持所有的基本类型及其封装类、{@link BigInteger 大整型}对象、{@link BigDecimal 高精度浮点型}对象、
     *              {@link String 字符串}对象、数组对象、
     *              {@link List 列表}对象、{@link Set 集合}对象、{@link Collection 容器}对象、
     *              {@link Map Map}对象、{@link Dictionary 字典}对象，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param params 转换过程中需要使用的参数，根据实际情况，可以为null
     * @param <T> 对象的类型
     * @return 对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     * @see ORM#read(Object, Class, Getter)
     */
    @Override
    public <T> T read(@NotNull Object doc, @NotNull Class<? extends T> clazz, Getter params)
            throws ORMException {
        return toObject(doc, clazz);
    }

    /**
     * 将一个文档转换成为对象
     *
     * 如果对象上带有标注，会优先使用标注
     *
     * @param doc 文档，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param <T> 对象的类型
     * @return 对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    public static <T> T toObject(@NotNull Object doc,
                                 @NotNull Class<? extends T> clazz)
            throws ORMException {

        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) ORMHelper.toBoolean(doc);
        } else if (clazz == byte.class || clazz == Byte.class) {
            return (T) ORMHelper.toByte(doc);
        } else if (clazz == char.class || clazz == Character.class) {
            return (T) ORMHelper.toCharacter(doc);
        } else if (clazz == short.class || clazz == Short.class) {
            return (T) ORMHelper.toShort(doc);
        } else if (clazz == int.class || clazz == Integer.class) {
            return (T) ORMHelper.toInteger(doc);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) ORMHelper.toLong(doc);
        } else if (clazz == float.class || clazz == Float.class) {
            return (T) ORMHelper.toFloat(doc);
        } else if (clazz == double.class || clazz == Double.class) {
            return (T) ORMHelper.toDouble(doc);
        } else if (clazz == BigInteger.class) {
            return (T) ORMHelper.toBigInteger(doc);
        } else if (clazz == BigDecimal.class) {
            return (T) ORMHelper.toBigDecimal(doc);
        } else if (clazz == String.class) {
            return (T) ORMHelper.toString(doc);
        } else if (clazz.isArray()) {
            // 数组
            return (T) toArray(doc, clazz.getComponentType());
        } else if (clazz == List.class) {
            // 列表
            return (T) ORMHelper.toList(doc);
        } else if (clazz == Set.class) {
            // 集合
            return (T) ORMHelper.toSet(doc);
        } else if (clazz == Collection.class) {
            // 容器
            return (T) ORMHelper.toList(doc);
        } else {
            // Java Bean
            if (doc instanceof Map) {
                return toBean((Map<String, Object>) doc, clazz);
            } else if (doc instanceof Dictionary) {
                return toBean((Dictionary) doc, clazz);
            }
        }
        throw new ORMException("不支持的对象：" + doc);
    }

    /**
     * 将一个Map文档转换为对象
     *
     * @param map Map对象，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param <T> 对象的类型
     * @return 对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    public static <T> T toBean(@NotNull Map<String, Object> map,
                               @NotNull Class<? extends T> clazz)
            throws ORMException {
        return toBean(new MapDictionary(map), clazz);

    }

    /**
     * 将一个字典文档转换为对象
     *
     * @param kvs 字典对象，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param <T> 对象的类型
     * @return 对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    public static <T> T toBean(@NotNull Dictionary kvs,
                               @NotNull Class<? extends T> clazz)
            throws ORMException {

        T instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new ORMException(e);
        } catch (IllegalAccessException e) {
            throw new ORMException(e);
        }

        if (!toBeanWithAnnotation(instance, kvs, clazz)) {
            toBeanWithoutAnnotation(instance, kvs, clazz);
        }
        return instance;
    }

    /**
     * 将一个对象转换成为数组
     *
     * @param obj 对象，不允许为null
     * @param component 数组元素的类型，不允许为null
     * @param <T> 数组元素的类型
     * @return 数组对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    public static <T> Object toArray(@NotNull Object obj,
                                     @NotNull Class<? extends T> component)
            throws ORMException {

        if (obj.getClass().isArray() &&
                obj.getClass().getComponentType() == component) {
            return obj;
        } else if (obj instanceof String) {
            if (component == char.class) {
                return ((String) obj).toCharArray();
            } else if (component == Character.class) {
                return ArrayUtil.boxing(((String) obj).toCharArray());
            }
        }

        Iterator i = ORMHelper.toIterator(obj);
        Collection c = new ArrayList();
        while (i.hasNext()) {
            c.add(i.next());
        }

        if (component == boolean.class) {
            boolean[] a = new boolean[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toBoolean(t);
            }
            return a;
        } else if (component == byte.class) {
            byte[] a = new byte[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toByte(t);
            }
            return a;
        } else if (component == char.class) {
            char[] a = new char[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toCharacter(t);
            }
            return a;
        } else if (component == short.class) {
            short[] a = new short[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toShort(t);
            }
            return a;
        } else if (component == int.class) {
            int[] a = new int[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toInteger(t);
            }
            return a;
        } else if (component == long.class) {
            long[] a = new long[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toLong(t);
            }
            return a;
        } else if (component == float.class) {
            float[] a = new float[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toFloat(t);
            }
            return a;
        } else if (component == double.class) {
            double[] a = new double[c.size()];
            int index = 0;
            for (Object t : c) {
                a[index++] = ORMHelper.toDouble(t);
            }
            return a;
        } else {
            T[] a = (T[]) Array.newInstance(component, c.size());
            int index = 0;
            for (Object t : c) {
                a[index++] = toObject(t, component);
            }
            return a;
        }
    }

    /**
     * 将一个对象转换成为列表
     *
     * @param obj 对象，不允许为null
     * @param component 列表元素的类型，不允许为null
     * @param <T> 列表元素的类型
     * @return 列表对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    public static <T> List<T> toList(@NotNull Object obj,
                                     @NotNull Class<? extends T> component)
            throws ORMException {
        if (obj instanceof List) {
            return (List<T>) obj;
        }
        Iterator i = ORMHelper.toIterator(obj);
        List<T> l = new ArrayList<>();
        while (i.hasNext()) {
            l.add(toObject(i.next(), component));
        }
        return l;
    }

    /**
     * 将一个对象转换成为集合
     *
     * @param obj 对象，不允许为null
     * @param component 集合元素的类型，不允许为null
     * @param <T> 集合元素的类型
     * @return 集合对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    public static <T> Set<T> toSet(@NotNull Object obj,
                                   @NotNull Class<? extends T> component)
            throws ORMException {
        if (obj instanceof Set) {
            return (Set<T>) obj;
        }
        Iterator i = ORMHelper.toIterator(obj);
        Set<T> l = new HashSet<>();
        while (i.hasNext()) {
            l.add(toObject(i.next(), component));
        }
        return l;
    }

    /**
     * 将一个字典文档转换为对象（包含注解）
     *
     * @param instance 对象的实例，不允许为null
     * @param kvs 字典对象，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param <T> 对象的类型
     * @return 若该对象不包含注解，则返回false，否则返回true
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    private static <T> boolean toBeanWithAnnotation(@NotNull T instance,
                                                    @NotNull Dictionary kvs,
                                                    @NotNull Class<? extends T> clazz)
            throws ORMException {

        boolean r = false;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ORF orf = field.getAnnotation(ORF.class);
            if (orf == null) {
                continue;
            } else if (!r) {
                r = true;
            }

            KeyValue kv = kvs.get(orf.name());
            if (kv == null) {
                continue;
            }
            Object value = null;
            if (kv.getValue() != null) {
                switch (orf.type()) {
                    case Normal:
                        value = toObject(kv.getValue(), field.getType());
                        break;
                    case Array:
                        value = toArray(kv.getValue(), orf.component());
                        break;
                    case List:
                    case Collection:
                        value = toList(kv.getValue(), orf.component());
                        break;
                    case Set:
                        value = toSet(kv.getValue(), orf.component());
                        break;
                }
            }
            ORMHelper.writeField(instance, clazz, field, value);
        }
        return r;
    }

    /**
     * 将一个字典文档转换为对象（不包含注解）
     *
     * @param instance 对象的实例，不允许为null
     * @param kvs 字典对象，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param <T> 对象的类型
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    private static <T> void toBeanWithoutAnnotation(@NotNull T instance,
                                                    @NotNull Dictionary kvs,
                                                    @NotNull Class<? extends T> clazz)
            throws ORMException {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            KeyValue kv = kvs.get(field.getName());
            if (kv == null) {
                continue;
            }
            Object value = null;
            if (kv.getValue() != null) {
                value = toObject(kv.getValue(), field.getType());
            }
            ORMHelper.writeField(instance, clazz, field, value);
        }

    }

}
