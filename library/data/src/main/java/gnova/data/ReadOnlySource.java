package gnova.data;

import gnova.annotation.NotNull;
import gnova.query.cursor.Cursor;

import java.io.Closeable;

/**
 * 只读的数据源
 *
 * 数据来源是数据的源头，可以进行增删改查操作，
 * 它可能是远程的（Remote），也可能是本地的（Local），
 * 它可能是一个数据库实例的连接，也可能是一个或多个文件，
 * 总之，取决于具体的实现。
 *
 * @param <E> 数据记录的类型
 */
public interface ReadOnlySource<E>
        extends Closeable {

    /**
     * 数据源的配置
     */
    class Options {

        private volatile int batchSize;
        private volatile boolean batchSizeChanged = false;

        /**
         * 批量查询一次获取的数据记录数量
         *
         * @param batchSize
         * @return
         */
        public Options batchSize(int batchSize) {
            if (batchSize <= 0) {
                throw new IllegalArgumentException("batchSize必须大于0。");
            }
            this.batchSizeChanged = true;
            this.batchSize = batchSize;
            return this;
        }

        /**
         * 将查询一次获取的数据记录数量设置为默认值
         *
         * 这个默认值取决于每个数据源具体的实现
         *
         * @return
         */
        public Options defaultBatchSize() {
            batchSizeChanged = false;
            return this;
        }

        /**
         * 获取查询一次获取的数据记录数量
         *
         * @return
         */
        public int getBatchSize() {
            return batchSize;
        }

        /**
         * 查看查询一次获取的数据记录数量是否为默认值
         *
         * @return
         */
        public boolean isBatchSizeChanged() {
            return batchSizeChanged;
        }
    }

    /**
     * 获取数据源的配置
     *
     * @return
     */
    @NotNull
    Options options();

    /**
     * 关闭数据源
     */
    @Override
    void close();

    /**
     * 获取目标数据的数量
     *
     * @param targets
     * @param selection
     * @param params
     * @return
     */
    long count(String[] targets, String selection, Object[] params);

    /**
     * 查询数据
     *
     * 过滤条件中包含占位符
     *
     * @param targets 数据集合的名字，若为null或者为空，则表明该查询是一个全文查询（前提是数据源需要支持全文搜索），
     *                若为多个，则会将结果合并在一起
     *                若targets为null或者为空，则selection不可以为null
     * @param fields 字段的集合，可以为null，表示所有字段
     * @param inclusion 返回字段的包含模式，若为true，则表示仅返回fields字段，若为false，则表示返回除fields字段以外的所有字段，
     *                  当且仅当fields不为null时有效
     * @param selection 查询过滤条件，为一个表达式字符串，可以为null，表示全查询
     *                  若selection为null，则targets为不可以null或者为空
     * @param params 替换selection中的占位符，必须与selection中的占位符一一对应
     * @param orders 排序字段的顺序，可以为null，表示不排序，若为多个字段，则按照顺序依次排序
     * @return 查询结果光标
     * @throws SourceException
     *
     * 两个例子：
     * 1）最简单的，调用query({"School"}, null, true, null, null, null)，表示查询集合名称为School的所有数据，如果
     * School是关系型数据库中的一张表，它相当于如下的查询语句：
     * select * from School;
     *
     * 2）一个比较全面的例子，调用
     * forEach({"Person"}, {"target", "age"}, true, "sex = ?", {"male"} , {{"target", Asc}, {"age", Desc}})
     * 表示查询集合名称为Person的数据，返回字段target和age，仅返回满足sex = ?（即性别为一个占位符）的数据，
     * 并将这个占位符设置为字符串male，返回的结果依次按照target和age分别升序和降序排列。
     * 如果Person是关系型数据库中的一张表，它相当于如下的查询语句：
     * select target, age from Person where sex = 'male' order by target asc, age desc;
     */
    Cursor<E> query(String[] targets,
                    String[] fields, boolean inclusion,
                    String selection, Object[] params,
                    Order[] orders)
            throws SourceException;

}
