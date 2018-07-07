package gnova.core.function;

import gnova.core.annotation.NotNull;

/**
 * 使用反射（reflection）实现的对象构造器
 *
 * @param <T> 对象的类型
 * @see ObjectBuilder
 * @author birderyu
 * @version 1.0.0
 */
public class ReflectObjectBuilder<T>
        implements ObjectBuilder<T> {

    /**
     * 对象的Class，不会为null
     */
    @NotNull
    private final Class<? extends T> clazz;

    /**
     * 构建一个使用反射实现的对象构造器
     *
     * @param clazz 对象的Class，不允许为null
     */
    public ReflectObjectBuilder(@NotNull Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 构建一个对象
     *
     * @return 对象，不会返回null
     * @see ObjectBuilder#build()
     */
    @Override
    public T build() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
