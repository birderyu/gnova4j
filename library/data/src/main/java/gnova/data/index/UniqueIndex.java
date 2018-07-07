package gnova.data.index;

/**
 * 唯一字段索引
 *
 * 唯一字段索引必须是一个普通字段索引（非空间索引）
 *
 * @param <E>
 */
public interface UniqueIndex<E>
        extends GeneralIndex<E> {
}
