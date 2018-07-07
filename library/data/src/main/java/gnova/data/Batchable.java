package gnova.data;

/**
 * 支持批量操作的
 *
 * 一个事务的流程为：
 * 开始事务（start）->
 * 执行事务流程->
 * 若中途执行出错，则取消事务（abort）->
 * 若全部执行成功，则提交事务（commit）->
 * 结束事务（stop）->
 *
 */
public interface Batchable {

    /**
     * 是否支持事务
     *
     * @return 若支持事务，则返回false，否则返回true
     */
    boolean supportBatch();

    /**
     * 开始事务
     *
     * @throws BatchException
     */
    default void start() throws BatchException {
        if (!supportBatch()) {
            throw new UnsupportedOperationException("start");
        }
    }

    /**
     * 结束事务
     *
     * @throws BatchException
     */
    default void stop() throws BatchException {
        if (!supportBatch()) {
            throw new UnsupportedOperationException("stop");
        }
    }

    /**
     * 提交事务
     *
     * @throws BatchException
     */
    default void commit() throws BatchException {
        if (!supportBatch()) {
            throw new UnsupportedOperationException("commit");
        }
    }

    /**
     * 取消事务
     *
     * 取消事务时，有些数据源可以做出回滚操作（rollback），视具体的实现而定
     *
     * @throws BatchException
     */
    default void abort() throws BatchException {
        if (!supportBatch()) {
            throw new UnsupportedOperationException("abort");
        }
    }

}
