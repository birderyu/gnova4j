package gnova.function;

import gnova.annotation.NotNull;

/**
 * 带有参数的对象构造器
 *
 * 对象构造器用于构造一个空的对象，在构造这个对象时，需要传入相应的参数
 *
 * @param <T> 对象的类型
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface ObjectBuilderWithParams<T> {

    /**
     * 构建一个对象
     *
     * @param params 构建该对象需要使用的参数
     * @return 对象，不允许返回null
     */
    @NotNull
    T build(Object ...params);

}
