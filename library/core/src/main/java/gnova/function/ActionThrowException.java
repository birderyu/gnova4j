package gnova.function;

/**
 * 执行一个没有任何参数，没有任何返回值，但抛出异常的方法
 *
 * @param <T> 异常的类型
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface ActionThrowException<T extends Throwable> {

    /**
     * 执行方法
     *
     * @throws T 若方法执行失败，则抛出此异常
     */
    void does() throws T;

}