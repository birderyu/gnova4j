package gnova.core.function;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 执行一个没有任何参数，没有任何返回值，也不抛出异常的方法
 *
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface Action
        extends ActionThrowException,
        Function<Void, Void>, Supplier<Void>, Consumer<Void> {

    /**
     * 执行方法
     */
    @Override
    void does();

    /**
     * 函数接口{@link Function Function}的{@link Function#apply(Object) apply}方法
     *
     * @param t 传入null即可，方法内部不会接受
     * @return 返回null
     * @see Function#apply(Object)
     */
    default Void apply(Void t) {
        does();
        return null;
    }

    /**
     * 函数接口{@link Supplier Supplier}的{@link Supplier#get() get}方法
     *
     * @return 返回null
     * @see Supplier#get()
     */
    default Void get() {
        does();
        return null;
    }

    /**
     * 函数接口{@link Consumer Consumer}的{@link Consumer#accept(Object) accept}方法
     *
     * @param t 传入null即可，方法内部不会接受
     * @see Consumer#accept(Object)
     */
    default void accept(Void t) {
        does();
    }

}
