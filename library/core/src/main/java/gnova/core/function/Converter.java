package gnova.core.function;

import java.util.function.Function;

/**
 * 转换方法封装
 *
 * 转换方法是一个函数式接口，它封装了从一个对象到另一个对象的转换方法
 *
 * @param <F> from，转换前的对象的类型
 * @param <T> to，转换后的对象的类型
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface Converter<F, T> extends Function<F, T> {

    /**
     * 转换方法
     *
     * @param f 转换前的对象，不允许为null
     * @return 转换后的对象
     * @throws ClassCastException 若转换失败，则抛出此异常
     */
    T convert(F f) throws ClassCastException;

    /**
     * 尝试转换的方法
     *
     * 该方法不会抛出异常，若转换失败，则返回一个null
     *
     * @param f 转换前的对象
     * @return 转换后的对象，若转换失败，则返回null
     */
    default T tryConvert(F f) {
        try {
            return convert(f);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 函数接口{@link Function Function}的{@link Function#apply(Object) apply}方法
     *
     * @param f 转换前的对象
     * @return 转换后的对象，若转换失败，则返回null
     * @see Function#apply(Object)
     */
    default T apply(F f) {
        return tryConvert(f);
    }

    /**
     * 一个强制转换的函数对象
     *
     * 该对象封装了强制转换的方法，即将一个_F类型的对象强制转换为_T类型的对象
     *
     * @param <_F> from，转换前的对象的类型
     * @param <_T> to，转换后的对象的类型
     * @return 函数对象
     */
    static <_F, _T> Converter<_F, _T> caser() {
        return f -> (_T) f;
    }

}
