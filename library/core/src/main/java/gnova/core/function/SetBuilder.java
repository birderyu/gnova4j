package gnova.core.function;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * 集合构造器
 *
 * 列表构造器封装了构造一个{@link Set Set}对象的方法
 *
 * @param <E> 集合中元素的类型
 * @see CollectionBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface SetBuilder<E>
        extends CollectionBuilder<E, Set<E>> {

    /**
     * 获取一个构造{@link HashSet HashSet}的SetBuilder
     *
     * @param <T> 集合中元素的类型
     * @return SetBuilder对象
     */
    static <T> SetBuilder<T> hashSetBuilder() {
        return HashSet::new;
    }

    /**
     * 获取一个构造{@link TreeSet TreeSet}的SetBuilder
     *
     * @param <T> 集合中元素的类型
     * @return SetBuilder对象
     */
    static <T> SetBuilder<T> treeSetBuilder() {
        return TreeSet::new;
    }

}
