package gnova.data;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.data.index.MultiIndex;
import gnova.data.index.SingleIndex;
import gnova.query.expression.PredicateExpression;

import java.util.Iterator;

/**
 * 索引
 *
 * @param <E> 索引中元素的类型
 */
public interface Index<E> {

    /**
     * 是否是单字段索引
     *
     * <p>一个索引要么是单字段索引，要么是多字段索引
     *
     * @return 若是单字段索引，则返回true，否则返回false
     */
    boolean isSingle();

    /**
     * 是否是一个唯一索引
     *
     * <p>一个索引要么是唯一索引，要么不是唯一索引
     *
     * @return
     */
    boolean isUnique();

    /**
     * 是否是空间索引
     *
     * 一个索引要么是空间索引，要么是普通字段索引
     *
     * @return
     */
    boolean isSpatial();

    /**
     * 转化为单字段索引
     *
     * 只有当当前索引为单字段索引时，才能够转化成功，否则将返回null
     *
     * @return
     */
    SingleIndex<E> asSingle();

    /**
     * 转化为多字段索引
     *
     * 只有当当前索引为多字段索引时，才能够转化成功，否则将返回null
     *
     * @return
     */
    MultiIndex<E> asMulti();

    /**
     * 清空索引中的所有数据
     */
    void clear();

    /**
     * 添加一条数据到索引
     *
     * @param e
     * @throws IllegalArgumentException 若向唯一索引中插入一条已经存在的数据，则抛出此异常
     */
    void insert(@NotNull E e);

    /**
     * 替换一条数据到索引
     *
     * @param before
     * @param after
     * @throws IllegalArgumentException 若向唯一索引中更新一条已经存在的数据，则抛出此异常
     */
    default void replace(@NotNull E before, @NotNull E after) {
        delete(before);
        insert(after);
    }

    /**
     * 从索引中删除一条数据
     *
     * @param e
     */
    void delete(@NotNull E e);

    /**
     * 简单索引查询
     *
     * 查询索引中字段值等于给定值的所有数据，返回数据的迭代器
     *
     * @param values 数据，不允许为null或为空，并且需要和索引字段一一对应
     * @return 查询结果，该方法不会返回null
     */
    @NotNull
    Iterator<E> search(@Checked Object... values);

    /**
     * 索引查询
     *
     * 这个查询的方法不会做合法性检查，需要调用方实现
     * 这个表达式必须与索引完全对应
     *
     * @param expression 谓词表达式，这个表达式必须与索引完全对应
     * @return 查询结果，该方法不会返回null
     */
    @NotNull
    Iterator<E> query(@Checked PredicateExpression expression);

}
