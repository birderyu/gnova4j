package gnova.data.table;

import gnova.core.annotation.NotNull;

import java.util.Iterator;

/**
 * 只读的数据列
 */
public interface ReadOnlyDataColumn
        extends Iterable {

    /**
     * 获取表
     *
     * @return 只读的表，不会返回null
     */
    @NotNull
    ReadOnlyDataTable getTable();

    /**
     * 获取列的名称
     *
     * @return 表的名称，不会返回null
     */
    @NotNull
    String getName();

    /**
     * 获取列的类型
     *
     * @return 列的类型，不会返回null
     */
    @NotNull
    ColumnType getType();

    /**
     * 是否为主键
     *
     * @return 若列为主键，则返回true，否则返回false
     */
    boolean isPrimaryKey();

    /**
     * 获取列的默认值
     *
     * @return 列的默认值
     */
    Object getDefaultValue();

    /**
     * 列是否可以为空值
     *
     * @return 若列可以为空值，则返回true，否则返回false
     */
    boolean isNullable();

    /**
     * 将当前列转换为一个可以修改的列
     *
     * @return 列，不会返回null
     */
    @NotNull
    DataColumn toWritable();

    /**
     * 获取行的数量
     *
     * @return 行的数量
     */
    default int size() {
        return getTable().getRowSize();
    }

    /**
     * 判断行是否为空
     *
     * @return 若行为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     *
     * @param index 行的下标
     * @return
     * @throws IndexOutOfBoundsException
     */
    ReadOnlyDataRow getRow(int index) throws IndexOutOfBoundsException;

    /**
     *
     * @param index 行的下标
     * @param <T>
     * @return
     * @throws IndexOutOfBoundsException
     */
    <T> T getValue(int index) throws IndexOutOfBoundsException;

    /**
     * 获取该列所有数据的迭代器
     *
     * @return
     */
    @Override
    Iterator iterator();

}
