package gnova.data.table;

import gnova.annotation.NotNull;
import gnova.data.Columns;
import gnova.data.Rows;

/**
 * 数据表
 *
 * @see ReadOnlyDataTable
 */
public interface DataTable
        extends ReadOnlyDataTable,
        Rows<ReadOnlyDataRow>, Columns<ReadOnlyDataColumn> {

    /**
     * 创建一个空行，并将其添加进表
     *
     * @return 空行，不会返回null
     */
    @Override
    @NotNull
    DataRow appendRow();

    /**
     * 根据行和列的下标设置值
     *
     * @param rIndex 行的下标
     * @param cIndex 列的下标
     * @param value 值
     * @param <T> 值的类型
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     * @throws IllegalArgumentException 若值不合法，则抛出此异常
     */
    default <T> void setValue(int rIndex, int cIndex, T value)
            throws IndexOutOfBoundsException, IllegalArgumentException {
        ((DataRow) getRow(rIndex)).setValue(cIndex, value);
    }

    /**
     * 根据行的下标和列的名称设置值
     *
     * @param rIndex 行的下标
     * @param cName 列的名称
     * @param value 值
     * @param <T> 值的类型
     * @throws IllegalArgumentException 若值不合法，则抛出此异常
     */
    default <T> void setValue(int rIndex, @NotNull String cName, T value)
            throws IllegalArgumentException {
        ((DataRow) getRow(rIndex)).setValue(cName, value);
    }

    /**
     * 清空表
     */
    default void clear() {
        clearColumn();
    }

    /**
     * 将当前表转换为一个可以写入数据的表
     *
     * @return 数据表
     * @see ReadOnlyDataTable#toWritable()
     */
    @Override
    default DataTable toWritable() {
        return this;
    }

    /**
     * 拷贝当前的数据表
     *
     * @return 数据表，不会返回null
     * @see ReadOnlyDataTable#clone()
     */
    @Override
    @NotNull
    DataTable clone();

}
