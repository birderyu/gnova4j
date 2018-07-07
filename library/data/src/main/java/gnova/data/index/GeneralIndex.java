package gnova.data.index;

import gnova.data.Index;

/**
 * 普通字段索引
 *
 * 普通字段索引与空间字段索引相对应
 *
 * @param <E>
 */
public interface GeneralIndex<E>
        extends Index<E> {

    /**
     * 是否是有序的
     *
     * 一个普通字段索引要么是有序的，要么是无序的
     *
     * @return
     */
    boolean isOrdered();

}
