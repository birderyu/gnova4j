package gnova.query.linq;

/**
 *
 * @param <T> 分组的依据
 * @param <E> 元素的类型
 * @param <R> 结果的类型
 */
public interface Grouping<T, E, R>
        extends Linq<E, R> {

    //Linq<E, R> get(T t);

}
