package gnova.data;

import gnova.annotation.NotNull;

import java.util.Collection;

/**
 * 数据源
 *
 * @param <E> 数据记录的类型
 */
public interface Source<E>
        extends ReadOnlySource<E>, Batchable {

    /**
     * 插入一条记录
     *
     * @param target 数据集合的名字
     * @param record 待插入的数据记录
     * @return 若插入成功，则返回true，否则返回false
     * @throws SourceException
     */
    boolean insert(String target, @NotNull E record) throws SourceException;

    /**
     * 插入多条记录
     *
     * @param target 数据集合的名字
     * @param records 待插入的数据记录的集合
     * @return 插入记录的个数
     * @throws SourceException
     */
    default long insert(String target, @NotNull Collection<? extends E> records)
            throws SourceException {
        try {
            if (supportBatch()) {
                // 开始事务
                start();
            }
            long size = 0L;
            for (E record : records) {
                if (record == null) {
                    continue;
                }
                if (insert(target, record)) {
                    size++;
                }
            }
            if (supportBatch()) {
                // 提交事务
                commit();
            }
            return size;
        } catch (BatchException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } catch (SourceException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw e;
        } catch (Exception e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } finally {
            if (supportBatch()) {
                try {
                    // 停止事务
                    stop();
                } catch (BatchException e) {
                    throw new SourceException(e);
                }
            }
        }

    }

    /**
     * 更新一条记录
     *
     * 更新记录是增量更新方式，即只会更新修改和新增的字段，未修改的字段不会更新，若希望全部使用新的记录替换，
     * 可以使用replace方法。
     * 更新记录时，待更新的记录必须提供获取唯一主键的方法，这样做的目的是为了在数据源中找到合适的记录并更新之。
     * 若记录本身无法提供这个方法，则可以使用updateBy方法或者updateWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待更新的数据记录
     * @return 若更新成功，则返回true，否则返回false
     * @throws SourceException
     */
    boolean update(String target, @NotNull E record) throws SourceException;

    /**
     * 更新多条记录
     *
     * 更新记录是增量更新方式，即只会更新修改和新增的字段，未修改的字段不会更新，若希望全部使用新的记录替换，
     * 可以使用replace方法。
     * 更新记录时，待更新的记录必须提供获取唯一主键的方法，这样做的目的是为了在数据源中找到合适的记录并更新之。
     * 若记录本身无法提供这个方法，则可以使用updateBy方法或者updateWhere方法。
     *
     * @param target 数据集合的名字
     * @param records 待更新的数据记录的集合
     * @return 更新记录的个数
     * @throws SourceException
     */
    default long update(String target, @NotNull Collection<? extends E> records)
            throws SourceException {
        try {
            if (supportBatch()) {
                // 开始事务
                start();
            }
            long size = 0L;
            for (E record : records) {
                if (record == null) {
                    continue;
                }
                if (update(target, record)) {
                    size++;
                }
            }
            if (supportBatch()) {
                // 提交事务
                commit();
            }
            return size;
        } catch (BatchException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } catch (SourceException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw e;
        } catch (Exception e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } finally {
            if (supportBatch()) {
                try {
                    // 停止事务
                    stop();
                } catch (BatchException e) {
                    throw new SourceException(e);
                }
            }
        }
    }

    /**
     * 更新符合条件的记录
     *
     * 更新记录是增量更新方式，即只会更新修改和新增的字段，未修改的字段不会更新，若希望全部使用新的记录替换，
     * 可以使用replaceBy方法。
     *
     * @param target 数据集合的名字
     * @param record 待更新的数据记录
     * @param key 匹配的字段名称
     * @param value 匹配的字段值
     * @param <T> 字段值的类型
     * @return 更新记录的个数
     * @throws SourceException
     */
    <T> long updateBy(String target, @NotNull E record, @NotNull String key, T value)
            throws SourceException;

    /**
     * 更新符合条件的记录
     *
     * 更新记录是增量更新方式，即只会更新修改和新增的字段，未修改的字段不会更新，若希望全部使用新的记录替换，
     * 可以使用replaceWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待更新的数据记录
     * @param selection 匹配过滤条件，为一个表达式字符串，若为null，则表示全部更新
     * @return 获取更新记录的个数
     * @throws SourceException
     */
    long updateWhere(String target, @NotNull E record, String selection) throws SourceException;

    /**
     * 更新符合条件的记录
     *
     * 过滤条件中包含占位符
     * 更新记录是增量更新方式，即只会更新修改和新增的字段，未修改的字段不会更新，若希望全部使用新的记录替换，
     * 可以使用replaceWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待更新的数据记录
     * @param selection 匹配过滤条件，为一个表达式字符串，若为null，则表示全部更新
     * @param params 替换selection中的占位符，必须与selection中的占位符一一对应
     * @return 获取更新记录的个数
     * @throws SourceException
     */
    long updateWhere(String target, @NotNull E record, String selection, Object... params) throws SourceException;

    /**
     * 替换一条记录
     *
     * 替换记录会将匹配的记录完全替换成新的记录，未修改的字段会被删除，若希望增量更新记录，可以使用update方法。
     * 替换记录时，待替换的记录必须提供获取唯一主键的方法，这样做的目的是为了在数据源中找到合适的记录并替换之。
     * 若记录本身无法提供这个方法，则可以使用replaceBy方法或者replaceWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待替换的数据记录
     * @return 若替换成功，则返回true，否则返回false
     * @throws SourceException
     */
    boolean replace(String target, @NotNull E record) throws SourceException;

    /**
     * 替换多条记录
     *
     * 替换记录会将匹配的记录完全替换成新的记录，未修改的字段会被删除，若希望增量更新记录，可以使用update方法。
     * 替换记录时，待替换的记录必须提供获取唯一主键的方法，这样做的目的是为了在数据源中找到合适的记录并替换之。
     * 若记录本身无法提供这个方法，则可以使用replaceBy方法或者replaceWhere方法。
     *
     * @param target 数据集合的名字
     * @param records 待替换的数据记录的集合
     * @return 获取替换记录的个数
     * @throws SourceException
     */
    default long replace(String target, @NotNull Collection<? extends E> records)
            throws SourceException {
        try {
            if (supportBatch()) {
                // 开始事务
                start();
            }
            long size = 0L;
            for (E record : records) {
                if (record == null) {
                    continue;
                }
                if (replace(target, record)) {
                    size++;
                }
            }
            if (supportBatch()) {
                // 提交事务
                commit();
            }
            return size;
        } catch (BatchException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } catch (SourceException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw e;
        } catch (Exception e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } finally {
            if (supportBatch()) {
                try {
                    // 停止事务
                    stop();
                } catch (BatchException e) {
                    throw new SourceException(e);
                }
            }
        }
    }

    /**
     * 替换符合条件的记录
     *
     * 替换记录会将匹配的记录完全替换成新的记录，未修改的字段会被删除，若希望增量更新记录，可以使用updateBy方法。
     *
     * @param target 数据集合的名字
     * @param record 待替换的数据记录
     * @param key 匹配的字段名称
     * @param value 匹配的字段值
     * @param <T> 字段值的类型
     * @return 获取替换记录的个数
     * @throws SourceException
     */
    <T> long replaceBy(String target, @NotNull E record, @NotNull String key, T value)
            throws SourceException;

    /**
     * 替换符合条件的记录
     *
     * 替换记录会将匹配的记录完全替换成新的记录，未修改的字段会被删除，若希望增量更新记录，可以使用updateWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待替换的数据记录
     * @param selection 匹配过滤条件，为一个表达式字符串，若为null，则表示全部替换
     * @return 获取替换记录的个数
     * @throws SourceException
     */
    long replaceWhere(String target, @NotNull E record, String selection) throws SourceException;

    /**
     * 更新符合条件的记录
     *
     * 过滤条件中包含占位符
     * 替换记录会将匹配的记录完全替换成新的记录，未修改的字段会被删除，若希望增量更新记录，可以使用updateWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待替换的数据记录
     * @param selection 匹配过滤条件，为一个表达式字符串，若为null，则表示全部替换
     * @param params 替换selection中的占位符，必须与selection中的占位符一一对应
     * @return 获取替换记录的个数
     * @throws SourceException
     */
    long replaceWhere(String target, @NotNull E record, String selection, Object... params) throws SourceException;

    /**
     * 删除一条记录
     *
     * 替换删除时，待删除的记录必须提供获取唯一主键的方法，这样做的目的是为了在数据源中找到合适的记录并删除之。
     * 若记录本身无法提供这个方法，则可以使用deleteBy方法或者deleteWhere方法。
     *
     * @param target 数据集合的名字
     * @param record 待删除的数据记录
     * @return 若删除成功，则返回true，否则返回false
     * @throws SourceException
     */
    boolean delete(String target, @NotNull E record) throws SourceException;

    /**
     * 删除多条记录
     *
     * 替换删除时，待删除的记录必须提供获取唯一主键的方法，这样做的目的是为了在数据源中找到合适的记录并删除之。
     * 若记录本身无法提供这个方法，则可以使用deleteBy方法或者deleteWhere方法。
     *
     * @param target 数据集合的名字
     * @param records 待删除的数据记录的集合
     * @return 获取替换记录的个数
     * @throws SourceException
     */
    default long delete(String target, @NotNull Collection<? extends E> records)
            throws SourceException {
        try {
            if (supportBatch()) {
                // 开始事务
                start();
            }
            long size = 0L;
            for (E record : records) {
                if (record == null) {
                    continue;
                }
                if (delete(target, record)) {
                    size++;
                }
            }
            if (supportBatch()) {
                // 提交事务
                commit();
            }
            return size;
        } catch (BatchException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } catch (SourceException e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw e;
        } catch (Exception e) {
            if (supportBatch()) {
                // 回滚事务
                try {
                    abort();
                } catch (BatchException e1) {
                    throw new SourceException(e);
                }
            }
            throw new SourceException(e);
        } finally {
            if (supportBatch()) {
                try {
                    // 停止事务
                    stop();
                } catch (BatchException e) {
                    throw new SourceException(e);
                }
            }
        }
    }

    /**
     * 删除符合条件的记录
     *
     * @param target 数据集合的名字
     * @param key 匹配的字段名称
     * @param value 匹配的字段值
     * @param <T> 字段值的类型
     * @return 获取删除记录的个数
     * @throws SourceException
     */
    <T> long deleteBy(String target, @NotNull String key, T value) throws SourceException;

    /**
     * 删除符合条件的记录
     *
     * @param target 数据集合的名字
     * @param selection 匹配过滤条件，为一个表达式字符串，若为null，则表示全部删除
     * @return 获取删除记录的个数
     * @throws SourceException
     */
    long deleteWhere(String target, String selection) throws SourceException;

    /**
     * 删除符合条件的记录
     *
     * 过滤条件中包含占位符
     *
     * @param target 数据集合的名字
     * @param selection 匹配过滤条件，为一个表达式字符串，若为null，则表示全部删除
     * @param params 替换selection中的占位符，必须与selection中的占位符一一对应
     * @return 获取删除记录的个数
     * @throws SourceException
     */
    long deleteWhere(String target, String selection, Object... params) throws SourceException;

}
