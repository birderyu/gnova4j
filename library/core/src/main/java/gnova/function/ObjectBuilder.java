package gnova.function;

import gnova.annotation.NotNull;

import java.util.function.Supplier;

/**
 * 对象构造器
 *
 * 对象构造器用于构造一个空的对象
 *
 * @param <T> 对象的类型
 * @see Supplier
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface ObjectBuilder<T>
        extends Supplier<T> {

    /**
     * 构建一个对象
     *
     * @return 对象，不会返回null
     */
    @NotNull
    T build();

    /**
     * 函数接口{@link Supplier Supplier}的{@link Supplier#get() get}方法
     *
     * @return 对象，不会返回null
     * @see Supplier#get()
     */
    @Override
    @NotNull
    default T get() {
        return build();
    }

}
