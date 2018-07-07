package gnova.data.table;

import gnova.annotation.NotNull;
import gnova.function.Getter;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * 数据行
 */
public interface ReadOnlyDataRow
        extends Getter, Iterable, Cloneable {

    @NotNull
    ReadOnlyDataTable getTable();

    /**
     *
     * @param index 列的下标
     * @param <T>
     * @return
     * @throws IndexOutOfBoundsException
     */
    <T> T getValue(int index) throws IndexOutOfBoundsException;

    /**
     *
     * @param name 列的名称
     * @param <T>
     * @return
     */
    <T> T getValue(@NotNull String name);

    /**
     * 获取列的数量
     *
     * @return
     */
    default int size() {
        return getTable().getColumnSize();
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 根据列的名称获取列
     *
     * @param name
     * @return
     */
    default ReadOnlyDataColumn getColumn(String name) {
        return getTable().getColumn(name);
    }

    /**
     * 根据列的下标获取列
     *
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    default ReadOnlyDataColumn getColumn(int index) throws IndexOutOfBoundsException {
        return getTable().getColumn(index);
    }

    /**
     * 是否包含列
     *
     * @param name
     * @return
     */
    default boolean containsColumn(String name) {
        return getTable().containsColumn(name);
    }

    /**
     * 根据列的名称获取列的下标
     *
     * @param name
     * @return 如果不存在该列，则返回-1
     */
    default int getColumnIndex(String name) {
        return getTable().getColumnIndex(name);
    }

    /**
     * 根据列的下标获取列的名称
     *
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    @NotNull
    default String getColumnName(int index) throws IndexOutOfBoundsException {
        return getTable().getColumnName(index);
    }

    default boolean foreach(@NotNull Predicate visitor) {
        for (Object value : this) {
            if (!visitor.test(value)) {
                return false;
            }
        }
        return true;
    }

    ReadOnlyDataRow clone();

    DataRow toWriteable();

    /**
     * 获取该行所有数据的迭代器
     *
     * @return
     */
    @Override
    Iterator iterator();

}
