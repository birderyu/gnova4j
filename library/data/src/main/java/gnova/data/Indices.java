package gnova.data;

import gnova.annotation.Checked;
import gnova.annotation.NotNull;
import gnova.data.index.SpatialIndex;
import gnova.data.index.UniqueIndex;
import gnova.query.LogicalExpression;
import gnova.query.LogicalOperator;

import java.util.Iterator;

/**
 * 索引的集合
 *
 * @param <E> 索引中数据元素的类型
 */
public interface Indices<E>
        extends Iterable<Index<E>> {

    /**
     * 索引查询的结果
     *
     * @param <E> 数据元素的类型
     */
    class QueryResult<E> {

        /**
         * 已经完成的匹配
         */
        private final Iterator<E> left;

        /**
         * 连接操作符
         */
        private final LogicalOperator link;

        /**
         * 未经完成的匹配
         */
        private final LogicalExpression right;

        /**
         * 构造一个索引的集合
         * @param left 已经完成的匹配
         * @param link 连接操作符
         * @param right 未经完成的匹配
         */
        public QueryResult(Iterator<E> left,
                           LogicalOperator link,
                           LogicalExpression right) {
            this.left = left;
            this.link = link;
            this.right = right;
        }

        /**
         * 获取已经完成匹配的数据，若为空，表示表达式无法匹配任何索引
         *
         * @return
         */
        public Iterator<E> getLeft() {
            return left;
        }

        /**
         * 获取连接操作符
         *
         * @return
         */
        public LogicalOperator getLink() {
            return link;
        }

        /**
         * 获取剩余的表达式
         *
         * @return
         */
        public LogicalExpression getRight() {
            return right;
        }

    }

    /**
     * 获取索引的数量
     *
     * @return 索引的数量
     */
    int size();

    /**
     * 索引是否为空
     *
     * @return 若无索引，则返回true，否则返回false
     */
    default boolean isEmpty()  {
        return size() == 0;
    }

    /**
     * 获取一个索引
     *
     * @param name 索引的名字
     * @return 索引，若无则返回null
     */
    Index<E> get(@NotNull String name);

    /**
     * 创建一个普通字段索引
     *
     * @param name 索引的名字，不允许为null
     * @param fieldNames 建立索引的字段名，不允许为null
     * @param ordered 是否是有序索引
     * @return 索引，该索引不包含任何数据，需要调用insert方法向其中插入数据，
     *      若索引创建失败，则抛出异常，该方法不会返回null
     * @throws IllegalArgumentException 若同名索引已经存在，或字段名称为空，则抛出此异常
     */
    @NotNull
    Index<E> build(@NotNull String name, @NotNull String[] fieldNames, boolean ordered);

    /**
     * 创建一个唯一字段索引
     *
     * @param name 索引的名字，不允许为null
     * @param fieldNames 建立索引的字段名，不允许为null
     * @param ordered 是否是有序索引
     * @return 唯一索引，该索引不包含任何数据，需要调用insert方法向其中插入数据，
     *      若索引创建失败，则抛出异常，该方法不会返回null
     * @throws IllegalArgumentException 若同名索引已经存在，或字段名称为空，则抛出此异常
     */
    @NotNull
    UniqueIndex<E> buildUnique(@NotNull String name, @NotNull String[] fieldNames, boolean ordered);

    /**
     * 创建一个空间索引
     *
     * @param name 索引的名字，不允许为null
     * @param fieldName 建立空间索引的字段名
     * @return 空间索引，该索引不包含任何数据，需要调用insert方法向其中插入数据，
     *      若索引创建失败，则抛出异常，该方法不会返回null
     * @throws IllegalArgumentException 若同名索引已经存在，或字段名称为空，则抛出此异常
     */
    @NotNull
    SpatialIndex<E> buildSpatial(@NotNull String name, @NotNull String fieldName);

    /**
     * 移除一个索引
     *
     * @param name 索引的名字，不允许为null
     */
    void drop(@NotNull String name);

    /**
     * 移除所有索引
     */
    void dropAll();

    /**
     * 清空所有索引中的数据，但不删除索引本身
     *
     * 调用clear方法之后，索引依旧保留，但其中的数据已经清空
     */
    void clear();

    /**
     * 向索引中添加一条数据
     *
     * @param e 数据，不允许为null
     */
    void insert(@NotNull E e);

    /**
     * 向索引中更新一条数据
     *
     * @param before 更新之前的数据，不允许为null
     * @param after 更新之后的数据，不允许为null
     */
    void update(@NotNull E before, @NotNull E after);

    /**
     * 删除一条数据
     *
     * @param e 数据，不允许为null
     */
    void delete(@NotNull E e);

    /**
     * 索引查询
     *
     * @param expression 表达式，表达式对象应该在调用该方法前完成了合法性的校验，包括非空判断、是否包含占位符等，
     *                   方法内部不会对此进行校验，若用户没有完成这一过程，那么该方法可能抛出异常。     *
     * @return 索引查询的结果，不会返回null，
     *      结果包含如下三个字段(left、link、right)，分别表示(已经完成匹配的数据、连接操作符、剩余的表达式)，
     *      一般返回值会有以下几种情况：
     *      1）(left, null, null)：即连接操作符和剩余的表达式都为null，说明在索引中完成了全部表达式的匹配查询；
     *      2）(null, null, right)：即已经完成匹配的数据和连接操作符都为null，说明在索引中没有完成任何匹配查询，
     *          此时right和输入的参数expression应该是一样的；
     *      3）(left、link、right)，即三者都不为null，说明在索引中完成了部分表达式的匹配查询，但还剩余了一部分
     *          表达式没有匹配上，此时会将未能完成的匹配以表达式的方式返回。
     */
    @NotNull
    QueryResult<E> query(@Checked LogicalExpression expression);

    @NotNull
    Indices<E> cloneEmpty();

}
