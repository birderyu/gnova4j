package gnova.function;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 列表构造器
 *
 * 列表构造器封装了构造一个{@link List List}对象的方法
 *
 * @param <E> 列表中元素的类型
 * @see CollectionBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface ListBuilder<E>
        extends CollectionBuilder<E, List<E>> {

    /**
     * 获取一个构造{@link ArrayList ArrayList}的ListBuilder
     *
     * @param <T> 列表中元素的类型
     * @return ListBuilder对象
     */
    static <T> ListBuilder<T> arrayListBuilder() {
        return ArrayList::new;
    }

    /**
     * 获取一个构造{@link LinkedList LinkedList}的ListBuilder
     *
     * @param <T> 列表中元素的类型
     * @return ListBuilder对象
     */
    static <T> ListBuilder<T> linkedListBuilder() {
        return LinkedList::new;
    }

}
