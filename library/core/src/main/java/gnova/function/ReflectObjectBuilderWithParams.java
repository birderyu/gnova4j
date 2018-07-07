package gnova.function;

import gnova.annotation.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 使用反射（reflection）实现的带有参数的对象构造器
 *
 * @param <T> 对象的类型
 * @see ObjectBuilderWithParams
 * @author birderyu
 * @version 1.0.0
 */
public class ReflectObjectBuilderWithParams<T>
        implements ObjectBuilderWithParams<T> {

    /**
     * 带有参数的构造方法，不会为null
     */
    @NotNull
    private final Constructor<? extends T> constructor;

    /**
     * 构建一个使用反射实现的带有参数的对象构造器
     *
     * @param clazz 对象的Class，不允许为null
     * @param parameterTypes 构造方法中参数的Class列表
     * @throws IllegalArgumentException 若该类不包含该构造方法，则抛出此异常
     */
    public ReflectObjectBuilderWithParams(@NotNull Class<? extends T> clazz,
                                          Class<?>... parameterTypes)
            throws IllegalArgumentException {
        try {
            constructor = clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 构建一个对象
     *
     * @param params 构建该对象需要使用的参数
     * @return 对象，不允许返回null
     * @throws IllegalArgumentException 若构建失败，则抛出此异常
     * @see ObjectBuilderWithParams#build(Object...)
     */
    @Override
    public T build(Object... params) throws IllegalArgumentException {
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
