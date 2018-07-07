package gnova.function;

import gnova.annotation.NotNull;

import java.util.function.BiConsumer;

/**
 * 值设置器
 *
 * 值设置器用于向一个对象中设置值
 *
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface Setter
        extends BiConsumer<String, Object> {

    /**
     * 设置一个值
     *
     * 当键存在时，才会设置这个值，若键不存在，该方法不会增加值
     *
     * @param key 键，不允许为null
     * @param value 值
     * @param <T> 值的类型
     */
    <T> void setValue(@NotNull String key, T value);

    /**
     * 函数接口{@link BiConsumer BiConsumer}的{@link BiConsumer#accept(Object, Object) accept}方法
     *
     * @param key 键，不允许为null
     * @param value 值
     * @see BiConsumer#accept(Object, Object)
     */
    @Override
    default void accept(String key, Object value) {
        setValue(key, value);
    }

}
