package gnova.query.linq;

import gnova.annotation.NotNull;
import gnova.query.cursor.Cursor;
import gnova.query.cursor.Cursorable;

import java.util.Comparator;
import java.util.function.*;

/**
 * 综合查询语言（Language Integrated Query）
 *
 * <p>仿照C#中LINQ的语法，使用Java 1.8实现类似的概念
 *
 * @param <E> 数据的类型
 * @param <R> 结果的类型
 */
public interface Linq<E, R> extends Cursorable<R> {

    /**
     * 从数据中选择出结果
     *
     * @param selector 筛选器
     * @return
     */
    @NotNull
    Linq<E, R> select(Function<? super E, ? extends R> selector);

    /**
     * 获取数据来源
     *
     * @param source 数据来源
     * @return
     */
    @NotNull
    Linq<E, R> from(@NotNull Cursorable<? extends E> source);

    @NotNull
    Linq<E, R> from(@NotNull Iterable<? extends E> source);

    /**
     *
     * @param predicate 数据谓词
     * @return
     */
    @NotNull
    Linq<E, R> where(Predicate<? super E> predicate);

    /**
     * 分组
     *
     * @param selector
     * @param <T> 分组的依据
     * @return
     */
    <T> Grouping<T, E, Linq<E, R>> groupBy(Function<E, T> selector);

    //<R> Linq<T> orderBy(Function<T, R> selector);

    default long count() {
        long r = 0;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                ++r;
                cursor.next();
            }
        }
        return r;
    }

    default long count(@NotNull Predicate<? super R> predicate) {
        long r = 0;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                if (predicate.test(cursor.next())) {
                    r++;
                }
            }
        }
        return r;
    }

    default boolean any() {
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                return true;
            }
        }
        return false;
    }

    default int sum(@NotNull ToIntFunction<? super R> selector) {
        int s = 0;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                s += selector.applyAsInt(cursor.next());
            }
        }
        return s;
    }

    default long sum(@NotNull ToLongFunction<? super R> selector) {
        long s = 0;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                s += selector.applyAsLong(cursor.next());
            }
        }
        return s;
    }

    default double sum(@NotNull ToDoubleFunction<? super R> selector) {
        double s = 0;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                s += selector.applyAsDouble(cursor.next());
            }
        }
        return s;
    }

    default <T> T sum(Function<R, T> selector, BiFunction<? super T, ? super T, ? extends T> summator) {
        T r = null;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                r = summator.apply(r, selector.apply(cursor.next()));
            }
        }
        return r;
    }

    default int min(@NotNull ToIntFunction<? super R> selector) {
        int r = 0;
        try(Cursor<R> cursor = cursor()) {
            int index = 0;
            while (cursor.hasNext()) {
                int c = selector.applyAsInt(cursor.next());
                if (index++ == 0) {
                    r = c;
                } else {
                    r = r < c ? r : c;
                }
            }
        }
        return r;
    }

    default long min(@NotNull ToLongFunction<? super R> selector) {
        long r = 0;
        try(Cursor<R> cursor = cursor()) {
            int index = 0;
            while (cursor.hasNext()) {
                long c = selector.applyAsLong(cursor.next());
                if (index++ == 0) {
                    r = c;
                } else {
                    r = r < c ? r : c;
                }
            }
        }
        return r;
    }

    default double min(@NotNull ToDoubleFunction<? super R> selector) {
        double r = 0;
        try(Cursor<R> cursor = cursor()) {
            int index = 0;
            while (cursor.hasNext()) {
                double c = selector.applyAsDouble(cursor.next());
                if (index++ == 0) {
                    r = c;
                } else {
                    r = r < c ? r : c;
                }
            }
        }
        return r;
    }

    default <T extends Comparable<T>> T min(Function<? super R, ? extends T> selector) {
        T r = null;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                T _r = selector.apply(cursor.next());
                if (_r == null) {
                    continue;
                } else if (r == null) {
                    r = _r;
                } else {
                    r = r.compareTo(_r) < 0 ? r : _r;
                }
            }
        }
        return r;
    }

    default <T> T min(Function<? super R, ? extends T> selector, Comparator<T> comparator) {
        T r = null;
        try(Cursor<R> cursor = cursor()) {
            while (cursor.hasNext()) {
                T _r = selector.apply(cursor.next());
                if (_r == null) {
                    continue;
                } else if (r == null) {
                    r = _r;
                } else {
                    r = comparator.compare(r, _r) < 0 ? r : _r;
                }
            }
        }
        return r;
    }

}
