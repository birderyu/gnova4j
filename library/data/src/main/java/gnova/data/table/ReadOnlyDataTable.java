package gnova.data.table;

import gnova.annotation.NotNull;
import gnova.data.Order;
import gnova.data.ReadOnlyColumns;
import gnova.data.ReadOnlyRows;

import java.io.Serializable;
import java.util.Iterator;
import java.util.function.Predicate;

/**
 * 只读的数据表
 *
 * 数据表是一种相对严格的二维数据结构，
 * 数据表通过行和列的下标获取数据，通常需要具备常数级别的访问效率，
 * 数据表在使用之前必须明确定义列的组织结构，
 * 当数据以行的形式插入数据表时，需要判断行的值是否合法
 *
 * @see ReadOnlyDataColumn
 * @see ReadOnlyDataRow
 */
public interface ReadOnlyDataTable
        extends ReadOnlyRows<ReadOnlyDataRow>, ReadOnlyColumns<ReadOnlyDataColumn>,
        Iterable<ReadOnlyDataRow>, Serializable, Cloneable {

    /**
     * 获取表的名称
     *
     * @return 表的名称，不会返回null
     */
    @NotNull
    String getName();

    /**
     * 获取表的数据记录的数量
     *
     * 数据记录的数量即行的数量
     *
     * @return 数据记录的数量
     */
    default int size() {
        return getRowSize();
    }

    /**
     * 判断表是否为空
     *
     * 若表的行数为空，则该表为空
     *
     * @return 若表为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return isRowEmpty();
    }

    /**
     * 根据行和列的下标获取值
     *
     * @param rIndex 行的下标
     * @param cIndex 列的下标
     * @param <T> 值的类型
     * @return 值
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    <T> T getValue(int rIndex, int cIndex) throws IndexOutOfBoundsException;

    /**
     * 根据行的下标和列的名称获取值
     *
     * @param rIndex 行的下标
     * @param cName 列的名称，不可以为null
     * @param <T> 值的类型
     * @return 值
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    <T> T getValue(int rIndex, @NotNull String cName) throws IndexOutOfBoundsException;

    /**
     * 获取所有行的迭代器
     *
     * @return 迭代器，不会返回null
     * @see Iterable#iterator()
     */
    @Override
    @NotNull
    default Iterator<ReadOnlyDataRow> iterator() {
        return rowIterator();
    }

    /**
     * 获取部分行的迭代器
     *
     * @param fromIndex 起始行的下标，结果中将包含起始行
     * @param toIndex 终止行的下标，结果中将不包含终止行
     * @return 迭代器，不会返回null
     */
    @NotNull
    default Iterator<ReadOnlyDataRow> iterator(int fromIndex, int toIndex) {
        return rowIterator(fromIndex, toIndex);
    }

    /**
     * 获取部分行的迭代器
     *
     * @param fromIndex 起始行的下标
     * @param fromInclusive 是否包含起始行
     * @param toIndex 终止行的下标
     * @param toInclusive 是否包含终止行
     * @return 迭代器，不会返回null
     */
    @NotNull
    default Iterator<ReadOnlyDataRow> iterator(int fromIndex, boolean fromInclusive,
                                               int toIndex, boolean toInclusive) {
        return rowIterator(fromIndex, fromInclusive, toIndex, toInclusive);
    }

    /**
     * 获取部分行的迭代器
     *
     * @param selection 查询过滤条件，可以为null，表示全查询
     * @param params 查询过滤条件中的占位符参数，其数量必须与查询过滤条件中占位符的个数一致，
     *               若查询过滤条件中不包含占位符，则该值可以为null
     * @return 迭代器，不会返回null
     */
    @NotNull
    default Iterator<ReadOnlyDataRow> iterator(String selection, Object[] params) {
        return rowIterator(selection, params);
    }

    /**
     *
     * @param columns 结果中包含的列的名称，可以为null，表示结果中包含所有列
     * @param inclusion 包含模式，仅当columns不为null时有效，
     *                  若inclusion为true表示结果中仅包含columns中指定的列，
     *                  若inclusion为false表示结果中仅排除columns中指定的列
     * @param selection
     * @param params
     * @param orders
     * @return
     * @throws IllegalArgumentException
     */
    ReadOnlyDataTable select(String[] columns, boolean inclusion,
                             String selection, Object[] params,
                             Order[] orders)
            throws IllegalArgumentException;

    /**
     * 访问所有数据
     *
     * @param visitor 数据访问者，不可以为null
     * @return 若全部的数据访问完毕，则返回true，否则返回false
     */
    default boolean foreach(@NotNull Predicate visitor) {
        return rowForeach(row -> {
            if (!row.foreach(visitor)) {
                return false;
            }
            return true;
        });
    }

    /**
     * 拷贝一个空的数据表
     *
     * 该数据表将包含当前表中所有的列信息和索引信息，但不包含任何行
     *
     * @return 数据表，不会返回null
     */
    @NotNull
    DataTable cloneEmpty();

    /**
     * 将当前表转换为一个可以写入数据的表
     *
     * @return 数据表，不会返回null
     */
    @NotNull
    DataTable toWritable();

    /**
     * 拷贝当前的数据表
     *
     * @return 只读的数据表，不会返回null
     * @see Object#clone()
     */
    @NotNull
    ReadOnlyDataTable clone();

    /**
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object o);

    /**
     * @see Object#toString()
     */
    @Override
    String toString();

}
