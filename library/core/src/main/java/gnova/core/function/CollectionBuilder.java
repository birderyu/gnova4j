package gnova.core.function;

import gnova.core.annotation.NotNull;

import java.util.Collection;
import java.util.Iterator;

/**
 * 顺序容器构造器
 *
 * 顺序容器构造器封装了构造一个{@link Collection Collection}对象的方法
 *
 * @param <E> 容器中元素的类型
 * @param <C> 容器的类型
 * @see ObjectBuilder
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface CollectionBuilder<E, C extends Collection<E>>
        extends ObjectBuilder<C> {

    /**
     * 构建一个空的容器
     *
     * @return 容器对象，不允许返回null
     * @see ObjectBuilder#build()
     */
    @Override
    @NotNull
    C build();

    /**
     * 构建一个带有元素的容器
     *
     * @param e 容器中的元素
     * @return 容器对象，不会返回null
     */
    @NotNull
    default C build(E e) {
        C nc = build();
        nc.add(e);
        return nc;
    }

    /**
     * 构建一个带有元素的容器
     *
     * @param a 容器中的元素，不允许为null
     * @return 容器对象，不会返回null
     */
    @NotNull
    default C build(@NotNull E... a) {
        C nc = build();
        for (E e : a) {
            nc.add(e);
        }
        return nc;
    }

    /**
     * 构建一个带有元素的容器
     *
     * @param i 容器中的元素，不允许为null
     * @return 容器对象，不会返回null
     */
    @NotNull
    default C build(@NotNull Iterator<? extends E> i) {
        C nc = build();
        while (i.hasNext()) {
            nc.add(i.next());
        }
        return nc;
    }

    /**
     * 构建一个带有元素的容器
     *
     * @param i 容器中的元素，不允许为null
     * @return 容器对象，不会返回null
     */
    @NotNull
    default C build(@NotNull Iterable<? extends E> i) {
        C nc = build();
        for (E e : i) {
            nc.add(e);
        }
        return nc;
    }

    /**
     * 构建一个带有元素的容器
     *
     * @param c 容器中的元素，不允许为null
     * @return 容器对象，不会返回null
     */
    @NotNull
    default C build(@NotNull Collection<? extends E> c) {
        C nc = build();
        nc.addAll(c);
        return nc;
    }

}
