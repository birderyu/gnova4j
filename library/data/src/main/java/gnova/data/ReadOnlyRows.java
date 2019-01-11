package gnova.data;

import gnova.core.annotation.NotNull;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 只读的行的集合
 *
 * @param <R> 行的类型
 */
public interface ReadOnlyRows<R> {

    /**
     * 获取行的数量
     *
     * @return 行的数量
     */
    int getRowSize();

    /**
     * 判断行的数量是否为空
     *
     * @return 若行的数量为空，则返回true，否则返回false
     */
    default boolean isRowEmpty() {
        return getRowSize() == 0;
    }

    /**
     * 根据行的下标获取行
     *
     * @param index 行的下标
     * @return 行，不会返回null
     * @throws IndexOutOfBoundsException 若下标超过范围，则抛出此异常
     */
    @NotNull
    R getRow(int index) throws IndexOutOfBoundsException;

    /**
     * 获取所有行的迭代器
     *
     * @return 迭代器，不会返回null
     */
    @NotNull
    Iterator<R> rowIterator();

    /**
     * 获取部分行的迭代器
     *
     * @param fromIndex 起始行的下标，结果中将包含起始行
     * @param toIndex 终止行的下标，结果中将不包含终止行
     * @return 迭代器，不会返回null
     */
    @NotNull
    default Iterator<R> rowIterator(int fromIndex, int toIndex) {
        return rowIterator(fromIndex, true, toIndex, false);
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
    Iterator<R> rowIterator(int fromIndex, boolean fromInclusive,
                            int toIndex, boolean toInclusive);

    /**
     * 获取部分行的迭代器
     *
     * @param selection 查询过滤条件，可以为null，表示全查询
     * @param params 查询过滤条件中的占位符参数，其数量必须与查询过滤条件中占位符的个数一致，
     *               若查询过滤条件中不包含占位符，则该值可以为null
     * @return 迭代器，不会返回null
     * @throws IllegalArgumentException 若过滤条件非法，则抛出此异常
     */
    @NotNull
    Iterator<R> rowIterator(String selection, Object[] params)
            throws IllegalArgumentException;

    /**
     * 访问所有行
     *
     * @param visitor 行的访问者
     */
    default void rowForeach(@NotNull Consumer<? super R> visitor) {
        Iterator<R> iterator = rowIterator();
        while (iterator.hasNext()) {
            visitor.accept(iterator.next());
        }
    }

    /**
     * 访问所有行
     *
     * @param visitor 行的访问者
     * @return 若全部的行访问完毕，则返回true，否则返回false
     */
    default boolean rowForeach(@NotNull Predicate<? super R> visitor) {
        Iterator<R> iterator = rowIterator();
        while (iterator.hasNext()) {
            if (!visitor.test(iterator.next())) {
                return false;
            }
        }
        return true;
    }

}
