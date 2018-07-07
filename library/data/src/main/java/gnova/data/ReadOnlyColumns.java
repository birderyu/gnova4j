package gnova.data;

import gnova.annotation.NotNull;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * 以列作为基础组织的数据结构
 *
 * @param <C> 列的类型
 */
public interface ReadOnlyColumns<C> {

    /**
     * 获取列的数量
     *
     * @return 列的数量
     */
    int getColumnSize();

    /**
     * 判断列的数量是否为空
     *
     * @return 若列的数量为空，则返回true，否则返回false
     */
    default boolean isColumnEmpty() {
        return getColumnSize() == 0;
    }

    /**
     * 根据列的名称获取列
     *
     * @param name 列的名称，不可以为null
     * @return 列，若不存在该列，则返回null
     */
    C getColumn(@NotNull String name);

    /**
     * 根据列的下标获取列
     *
     * @param index 列的下标
     * @return 列，不会返回null
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    @NotNull
    C getColumn(int index) throws IndexOutOfBoundsException;

    /**
     * 根据列的名称判断是否包含该列
     *
     * @param name 列的名称，不可以为null
     * @return 若包含该列，则返回true，否则返回false
     */
    boolean containsColumn(@NotNull String name);

    /**
     * 根据列的名称获取列的下标
     *
     * @param name 列的名称，不可以为null
     * @return 列的下标，如果不存在该列，则返回-1
     */
    int getColumnIndex(@NotNull String name);

    /**
     * 根据列的下标获取列的名称
     *
     * @param index 列的下标
     * @return 列的名称，不会返回null
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    @NotNull
    String getColumnName(int index) throws IndexOutOfBoundsException;

    /**
     * 获取所有列的迭代器
     *
     * @return 迭代器，不会返回null
     */
    @NotNull
    Iterator<C> columnIterator();

    /**
     *
     *
     * @param names 结果中包含的列的名称，可以为null，表示结果中包含所有列
     * @param inclusion 包含模式，仅当columns不为null时有效，
     *                  若inclusion为true表示结果中仅包含columns中指定的列，
     *                  若inclusion为false表示结果中仅排除columns中指定的列
     * @return
     * @throws IllegalArgumentException
     */
    @NotNull
    Iterator<C> columnIterator(String[] names, boolean inclusion)
            throws IllegalArgumentException;

    /**
     * 访问所有列
     *
     * @param visitor 列的访问者
     * @return 若全部的列访问完毕，则返回true，否则返回false
     */
    default boolean columnForeach(@NotNull Predicate<? super C> visitor) {
        Iterator<C> iterator = columnIterator();
        while (iterator.hasNext()) {
            if (!visitor.test(iterator.next())) {
                return false;
            }
        }
        return true;
    }

}
