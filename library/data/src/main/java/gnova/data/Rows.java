package gnova.data;

import gnova.core.annotation.NotNull;

/**
 * 行的集合
 *
 * @param <R> 行的类型
 */
public interface Rows<R>
        extends ReadOnlyRows<R> {

    /**
     * 创建一个空行，并将其添加进行的集合
     *
     * @return 空行，不会返回null
     */
    @NotNull
    R appendRow();

    /**
     * 添加一行
     *
     * @param row 只读的行
     * @return 添加成功的行，不会返回null
     * @throws IllegalArgumentException 若值不合法，则抛出此异常
     */
    @NotNull
    R appendRow(R row) throws IllegalArgumentException;

    /**
     * 根据行的下标移除一行
     *
     * @param index 行的下标
     * @return 移除成功的行，不会返回null
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    R removeRow(int index) throws IndexOutOfBoundsException;

    /**
     * 清空所有行
     */
    void clearRow();
}
