package gnova.data.table;

import gnova.core.annotation.NotNull;

/**
 * 数据列
 */
public interface DataColumn
        extends ReadOnlyDataColumn {

    /**
     * 获取表
     *
     * @return 表，不会返回null
     */
    @Override
    @NotNull
    DataTable getTable();

    /**
     * 设置列的名称
     *
     * @param name 列的名称，不可以为null
     */
    void setName(@NotNull String name);

    /**
     * 设置列的类型
     *
     * @param type 列的类型，不可以为null
     */
    void setType(@NotNull ColumnType type);

    /**
     * 设置当前列是否为主键
     *
     * @param primaryKey 是否为主键
     */
    void setPrimaryKey(boolean primaryKey);

    /**
     * 设置列的默认值
     *
     * @param defaultValue 列的默认值，可以为null
     */
    void setDefaultValue(Object defaultValue);

    /**
     * 设置列是否可以为空值
     *
     * @param nullable 是否可以为空值
     */
    void setNullable(boolean nullable);

    /**
     *
     * @param index 行的下标
     * @param value
     * @param <T>
     * @throws IndexOutOfBoundsException
     * @throws IllegalArgumentException
     */
    <T> void setValue(int index, T value)
            throws IndexOutOfBoundsException, IllegalArgumentException;

    /**
     * 将当前列转换为一个可以修改的列
     *
     * @return 列，不会返回null
     */
    @Override
    @NotNull
    default DataColumn toWritable() {
        return this;
    }

}
