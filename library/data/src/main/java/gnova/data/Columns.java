package gnova.data;

import gnova.annotation.NotNull;

public interface Columns<C>
        extends ReadOnlyColumns<C> {

    /**
     * 添加一列
     *
     * @param cName 列的名称，不可以为null
     * @param column 列，不可以为null
     * @return 添加成功的列，不会返回null
     * @throws IllegalArgumentException 若表中已经包含了同名的列，则抛出此异常
     */
    @NotNull
    C appendColumn(@NotNull String cName, @NotNull C column) throws IllegalArgumentException;

    /**
     * 根据列的下标移除一列
     *
     * @param index 列的下标
     * @return 移除成功的列，不会返回null
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    @NotNull
    C removeColumn(int index) throws IndexOutOfBoundsException;

    /**
     * 根据列的名称移除一列
     *
     * @param cName 列的下标
     * @return 移除成功的列，若不存在该列，则返回null
     */
    C removeColumn(@NotNull String cName);

    /**
     * 清空所有列
     */
    void clearColumn();

}
