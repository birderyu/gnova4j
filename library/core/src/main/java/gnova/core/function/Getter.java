package gnova.core.function;

import gnova.core.annotation.NotNull;
import java.util.function.Function;

/**
 * 值获取器
 *
 * <p>值获取器用于从一个对象中获取值
 *
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface Getter
        extends Function<String, Object> {

    /**
     * 获取一个值
     *
     * <p>该方法不会抛出异常
     *
     * @param key 键，不允许为null
     * @param <T> 值的类型
     * @return 字段值，若不存在该字段，则返回null
     */
    <T> T getValue(@NotNull String key);

    /**
     * 函数接口{@link Function Function}的{@link Function#apply(Object) apply}方法
     *
     * @param key 键，不允许为null
     * @return 字段值，若不存在该字段，则返回null
     * @see Function#apply(Object)
     */
    @Override
    default Object apply(@NotNull String key) {
        return getValue(key);
    }

}
