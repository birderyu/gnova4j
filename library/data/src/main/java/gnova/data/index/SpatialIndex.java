package gnova.data.index;

/**
 * 空间索引
 *
 * 由于空间数据的特殊性，空间索引索引必须是一个单字段索引，不允许多个字段建立联合索引
 * 空间索引不存在有序和无序的区别，换言之，可以认为空间索引永远是有序的
 *
 * @param <E>
 */
public interface SpatialIndex<E>
        extends SingleIndex<E> {

}
