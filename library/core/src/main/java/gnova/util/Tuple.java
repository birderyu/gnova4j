package gnova.util;

import gnova.annotation.NotNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Collection;

/**
 * 元组类型
 *
 * 元组是一种可以包含多个元素的组合类型，使用该对象，可以构造使用最大包含9个元素的对象
 *
 * @see Collection
 * @see ReadOnlyCollection
 * @author birderyu
 * @version 1.0.0
 */
public abstract class Tuple
        extends AbstractReadOnlyCollection implements ReadOnlyCollection {

    /**
     * 构建一个包含一个元素的元组
     *
     * @param first 第一个元素
     * @param <T1> 第一个元素的类型
     * @return 元组对象
     */
    public static <T1>
    Tuple of1(T1 first) {
        return new _1(first);
    }

    /**
     * 构建一个包含两个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @return 元组对象
     */
    public static <T1, T2>
    Tuple of2(T1 first, T2 second) {
        return new _2(first, second);
    }

    /**
     * 构建一个包含三个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3>
    Tuple of3(T1 first, T2 second, T3 third) {
        return new _3(first, second, third);
    }

    /**
     * 构建一个包含四个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param fourth 第四个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3, T4>
    Tuple of4(T1 first, T2 second, T3 third, T4 fourth) {
        return new _4(first, second, third, fourth);
    }

    /**
     * 构建一个包含五个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param fourth 第四个元素
     * @param fifth 第五个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3, T4, T5>
    Tuple of5(T1 first, T2 second, T3 third, T4 fourth, T5 fifth) {
        return new _5(first, second, third, fourth, fifth);
    }

    /**
     * 构建一个包含六个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param fourth 第四个元素
     * @param fifth 第五个元素
     * @param sixth 第六个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3, T4, T5, T6>
    Tuple of6(T1 first, T2 second, T3 third,
              T4 fourth, T5 fifth, T6 sixth) {
        return new _6(first, second, third, fourth, fifth, sixth);
    }

    /**
     * 构建一个包含七个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param fourth 第四个元素
     * @param fifth 第五个元素
     * @param sixth 第六个元素
     * @param seventh 第七个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @param <T7> 第七个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3, T4, T5, T6, T7>
    Tuple of7(T1 first, T2 second, T3 third,
              T4 fourth, T5 fifth, T6 sixth, T7 seventh) {
        return new _7(first, second, third,
                fourth, fifth, sixth, seventh);
    }

    /**
     * 构建一个包含八个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param fourth 第四个元素
     * @param fifth 第五个元素
     * @param sixth 第六个元素
     * @param seventh 第七个元素
     * @param eighth 第八个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @param <T7> 第七个元素的类型
     * @param <T8> 第八个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8>
    Tuple of8(T1 first, T2 second, T3 third, T4 fourth,
              T5 fifth, T6 sixth, T7 seventh, T8 eighth) {
        return new _8(first, second, third, fourth,
                fifth, sixth, seventh, eighth);
    }

    /**
     * 构建一个包含九个元素的元组
     *
     * @param first 第一个元素
     * @param second 第二个元素
     * @param third 第三个元素
     * @param fourth 第四个元素
     * @param fifth 第五个元素
     * @param sixth 第六个元素
     * @param seventh 第七个元素
     * @param eighth 第八个元素
     * @param ninth 第九个元素
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @param <T7> 第七个元素的类型
     * @param <T8> 第八个元素的类型
     * @param <T9> 第九个元素的类型
     * @return 元组对象
     */
    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9>
    Tuple of9(T1 first, T2 second, T3 third,
              T4 fourth, T5 fifth, T6 sixth,
              T7 seventh, T8 eighth, T9 ninth) {
        return new _9(first, second, third,
                fourth, fifth, sixth,
                seventh, eighth, ninth);
    }

    /**
     * 不允许使用构造方法构建一个元组对象，请使用静态方法of1~of9构造对应的元组对象
     */
    private Tuple() {

    }

    /**
     * 获取第一个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get1st()
            throws NoSuchElementException {
        throw new NoSuchElementException("first");
    }

    /**
     * 设置第一个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set1st(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("first");
    }

    /**
     * 获取第二个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get2nd()
            throws NoSuchElementException {
        throw new NoSuchElementException("second");
    }

    /**
     * 设置第二个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set2nd(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("second");
    }

    /**
     * 获取第三个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get3rd()
            throws NoSuchElementException {
        throw new NoSuchElementException("third");
    }

    /**
     * 设置第三个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set3rd(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("third");
    }

    /**
     * 获取第四个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get4th()
            throws NoSuchElementException {
        throw new NoSuchElementException("fourth");
    }

    /**
     * 设置第四个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set4th(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("fourth");
    }

    /**
     * 获取第五个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get5th()
            throws NoSuchElementException {
        throw new NoSuchElementException("fifth");
    }

    /**
     * 设置第五个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set5th(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("fifth");
    }

    /**
     * 获取第六个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get6th()
            throws NoSuchElementException {
        throw new NoSuchElementException("sixth");
    }

    /**
     * 设置第六个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set6th(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("sixth");
    }

    /**
     * 获取第七个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get7th()
            throws NoSuchElementException {
        throw new NoSuchElementException("seventh");
    }

    /**
     * 设置第七个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set7th(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("seventh");
    }

    /**
     * 获取第八个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get8th()
            throws NoSuchElementException {
        throw new NoSuchElementException("eighth");
    }

    /**
     * 设置第八个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set8th(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("eighth");
    }

    /**
     * 获取第九个元素
     *
     * @param <T> 元素的类型
     * @return 元素的值
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> T get9th()
            throws NoSuchElementException {
        throw new NoSuchElementException("ninth");
    }

    /**
     * 设置第九个元素
     *
     * @param t 元素的值
     * @param <T> 元素的类型
     * @throws NoSuchElementException 若不存在该元素，则抛出此异常
     */
    public <T> void set9th(T t)
            throws NoSuchElementException {
        throw new NoSuchElementException("ninth");
    }

    /**
     * 包含一个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @see Tuple
     */
    private static class _1<T1>
            extends Tuple {

        /**
         * 第一个元素
         */
        private T1 _1st;

        /**
         * 构建一个包含一个元素的元组
         *
         * @param _1st 第一个元素
         */
        public _1(T1 _1st) {
            this._1st = _1st;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 1;
        }

        /**
         * 获取第一个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get1st()
         */
        @Override
        public <T> T get1st() {
            return (T) _1st;
        }

        /**
         * 设置第一个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set1st(T)
         */
        @Override
        public <T> void set1st(T t) {
            _1st = (T1) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new SingleIterator(_1st);
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            _1<?> tuple = (_1<?>) o;
            return Objects.equals(_1st, tuple._1st);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(_1st);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return "first: " + _1st;
        }
    }

    /**
     * 包含两个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @see Tuple
     * @see _1
     */
    private static class _2<T1, T2>
            extends _1<T1> {

        /**
         * 第二个元素
         */
        private T2 _2nd;

        /**
         * 构建一个包含两个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         */
        public _2(T1 _1st, T2 _2nd) {
            super(_1st);
            this._2nd = _2nd;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 2;
        }

        /**
         * 获取第二个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get2nd()
         */
        @Override
        public <T> T get2nd() {
            return (T) _2nd;
        }

        /**
         * 设置第二个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set2nd(T)
         */
        @Override
        public <T> void set2nd(T t) {
            this._2nd = (T2) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), _2nd));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _2<?, ?> tuple = (_2<?, ?>) o;
            return Objects.equals(_2nd, tuple._2nd);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _2nd);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", second: " + _2nd;
        }
    }

    /**
     * 包含三个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     */
    private static class _3<T1, T2, T3>
            extends _2<T1, T2> {

        /**
         * 第三个元素
         */
        private T3 _3rd;

        /**
         * 构建一个包含三个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         */
        public _3(T1 _1st, T2 _2nd, T3 _3rd) {
            super(_1st, _2nd);
            this._3rd = _3rd;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 3;
        }

        /**
         * 获取第三个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get3rd()
         */
        @Override
        public <T> T get3rd() {
            return (T) _3rd;
        }

        /**
         * 设置第三个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set3rd(T)
         */
        @Override
        public <T> void set3rd(T t) {
            this._3rd = (T3) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(), _3rd));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _3<?, ?, ?> tuple = (_3<?, ?, ?>) o;
            return Objects.equals(_3rd, tuple._3rd);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _3rd);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", third: " + _3rd;
        }
    }

    /**
     * 包含四个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     * @see _3
     */
    private static class _4<T1, T2, T3, T4>
            extends _3<T1, T2, T3> {

        /**
         * 第四个元素
         */
        private T4 _4th;

        /**
         * 构建一个包含四个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         * @param _4th 第四个元素
         */
        public _4(T1 _1st, T2 _2nd, T3 _3rd, T4 _4th) {
            super(_1st, _2nd, _3rd);
            this._4th = _4th;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 4;
        }

        /**
         * 获取第四个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get4th()
         */
        @Override
        public <T> T get4th() {
            return (T) _4th;
        }

        /**
         * 设置第四个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set4th(T)
         */
        @Override
        public <T> void set4th(T t) {
            this._4th = (T4) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(), get3rd(), _4th));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _4<?, ?, ?, ?> tuple = (_4<?, ?, ?, ?>) o;
            return Objects.equals(_4th, tuple._4th);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _4th);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", fourth: " + _4th;
        }
    }

    /**
     * 包含五个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     * @see _3
     * @see _4
     */
    private static class _5<T1, T2, T3, T4, T5>
            extends _4<T1, T2, T3, T4> {

        /**
         * 第五个元素
         */
        private T5 _5th;

        /**
         * 构建一个包含五个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         * @param _4th 第四个元素
         * @param _5th 第五个元素
         */
        public _5(T1 _1st, T2 _2nd, T3 _3rd, T4 _4th, T5 _5th) {
            super(_1st, _2nd, _3rd, _4th);
            this._5th = _5th;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 5;
        }

        /**
         * 获取第五个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get5th()
         */
        @Override
        public <T> T get5th() {
            return (T) _5th;
        }

        /**
         * 设置第五个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set5th(T)
         */
        @Override
        public <T> void set5th(T t) {
            this._5th = (T5) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(),
                    get3rd(), get4th(), _5th));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _5<?, ?, ?, ?, ?> tuple = (_5<?, ?, ?, ?, ?>) o;
            return Objects.equals(_5th, tuple._5th);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _5th);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", fifth: " + _5th;
        }
    }

    /**
     * 包含六个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     * @see _3
     * @see _4
     * @see _5
     */
    private static class _6<T1, T2, T3, T4, T5, T6>
            extends _5<T1, T2, T3, T4, T5> {

        /**
         * 第六个元素
         */
        private T6 _6th;

        /**
         * 构建一个包含六个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         * @param _4th 第四个元素
         * @param _5th 第五个元素
         * @param _6th 第六个元素
         */
        public _6(T1 _1st, T2 _2nd, T3 _3rd,
                  T4 _4th, T5 _5th, T6 _6th) {
            super(_1st, _2nd, _3rd, _4th, _5th);
            this._6th = _6th;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 6;
        }

        /**
         * 获取第六个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get6th()
         */
        @Override
        public <T> T get6th() {
            return (T) _6th;
        }

        /**
         * 设置第六个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set6th(T)
         */
        @Override
        public <T> void set6th(T t) {
            this._6th = (T6) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(),
                    get3rd(), get4th(), get5th(), _6th));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _6<?, ?, ?, ?, ?, ?> tuple = (_6<?, ?, ?, ?, ?, ?>) o;
            return Objects.equals(_6th, tuple._6th);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _6th);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", sixth: " + _6th;
        }
    }

    /**
     * 包含七个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @param <T7> 第七个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     * @see _3
     * @see _4
     * @see _5
     * @see _6
     */
    private static class _7<T1, T2, T3, T4, T5, T6, T7>
            extends _6<T1, T2, T3, T4, T5, T6> {

        /**
         * 第七个元素
         */
        private T7 _7th;

        /**
         * 构建一个包含七个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         * @param _4th 第四个元素
         * @param _5th 第五个元素
         * @param _6th 第六个元素
         * @param _7th 第七个元素
         */
        public _7(T1 _1st, T2 _2nd,
                  T3 _3rd, T4 _4th,
                  T5 _5th, T6 _6th, T7 _7th) {
            super(_1st, _2nd, _3rd, _4th, _5th, _6th);
            this._7th = _7th;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 7;
        }

        /**
         * 获取第七个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get7th()
         */
        @Override
        public <T> T get7th() {
            return (T) _7th;
        }

        /**
         * 设置第七个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set7th(T)
         */
        @Override
        public <T> void set7th(T t) {
            this._7th = (T7) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(),
                    get3rd(), get4th(), get5th(), get6th(), _7th));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _7<?, ?, ?, ?, ?, ?, ?> tuple = (_7<?, ?, ?, ?, ?, ?, ?>) o;
            return Objects.equals(_7th, tuple._7th);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _7th);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", seventh: " + _7th;
        }
    }

    /**
     * 包含八个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @param <T7> 第七个元素的类型
     * @param <T8> 第八个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     * @see _3
     * @see _4
     * @see _5
     * @see _6
     * @see _7
     */
    private static class _8<T1, T2, T3, T4, T5, T6, T7, T8>
            extends _7<T1, T2, T3, T4, T5, T6, T7> {

        /**
         * 第八个元素
         */
        private T8 _8th;

        /**
         * 构建一个包含八个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         * @param _4th 第四个元素
         * @param _5th 第五个元素
         * @param _6th 第六个元素
         * @param _7th 第七个元素
         * @param _8th 第八个元素
         */
        public _8(T1 _1st, T2 _2nd, T3 _3rd, T4 _4th,
                  T5 _5th, T6 _6th, T7 _7th, T8 _8th) {
            super(_1st, _2nd, _3rd, _4th, _5th, _6th, _7th);
            this._8th = _8th;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 8;
        }

        /**
         * 获取第八个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get8th()
         */
        @Override
        public <T> T get8th() {
            return (T) _8th;
        }

        /**
         * 设置第八个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set8th(T)
         */
        @Override
        public <T> void set8th(T t) {
            this._8th = (T8) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(),
                    get3rd(), get4th(), get5th(), get6th(), get7th(), _8th));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _8<?, ?, ?, ?, ?, ?, ?, ?> tuple = (_8<?, ?, ?, ?, ?, ?, ?, ?>) o;
            return Objects.equals(_8th, tuple._8th);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _8th);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", eighth: " + _8th;
        }
    }

    /**
     * 包含九个元素的元组
     *
     * @param <T1> 第一个元素的类型
     * @param <T2> 第二个元素的类型
     * @param <T3> 第三个元素的类型
     * @param <T4> 第四个元素的类型
     * @param <T5> 第五个元素的类型
     * @param <T6> 第六个元素的类型
     * @param <T7> 第七个元素的类型
     * @param <T8> 第八个元素的类型
     * @param <T9> 第九个元素的类型
     * @see Tuple
     * @see _1
     * @see _2
     * @see _3
     * @see _4
     * @see _5
     * @see _6
     * @see _7
     * @see _8
     */
    private static class _9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
            extends _8<T1, T2, T3, T4, T5, T6, T7, T8> {

        /**
         * 第九个元素
         */
        private T9 _9th;

        /**
         * 构建一个包含九个元素的元组
         *
         * @param _1st 第一个元素
         * @param _2nd 第二个元素
         * @param _3rd 第三个元素
         * @param _4th 第四个元素
         * @param _5th 第五个元素
         * @param _6th 第六个元素
         * @param _7th 第七个元素
         * @param _8th 第八个元素
         * @param _9th 第九个元素
         */
        public _9(T1 _1st, T2 _2nd, T3 _3rd,
                  T4 _4th, T5 _5th, T6 _6th,
                  T7 _7th, T8 _8th, T9 _9th) {
            super(_1st, _2nd, _3rd, _4th,
                    _5th, _6th, _7th, _8th);
            this._9th = _9th;
        }

        /**
         * 获取元素的个数
         *
         * @return 元素的个数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return 9;
        }

        /**
         * 获取第九个元素
         *
         * @param <T> 元素的类型
         * @return 元素的值
         * @see Tuple#get9th()
         */
        @Override
        public <T> T get9th() {
            return (T) _9th;
        }

        /**
         * 设置第九个元素
         *
         * @param t 元素的值
         * @param <T> 元素的类型
         * @see Tuple#set9th(T)
         */
        @Override
        public <T> void set9th(T t) {
            this._9th = (T9) t;
        }

        /**
         * 获取只读的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public ReadOnlyIterator iterator() {
            return new ReadOnlyIteratorProxy(new ArrayIterator(get1st(), get2nd(),
                    get3rd(), get4th(), get5th(), get6th(), get7th(), get8th(), _9th));
        }

        /**
         * 判断两个对象是否相等
         *
         * @param o 对象
         * @return 若对象相等，则返回true，否则返回false
         * @see Object#equals(Object)
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            _9<?, ?, ?, ?, ?, ?, ?, ?, ?> tuple = (_9<?, ?, ?, ?, ?, ?, ?, ?, ?>) o;
            return Objects.equals(_9th, tuple._9th);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), _9th);
        }

        /**
         * 转换为字符串
         *
         * @return 字符串
         * @see Object#toString()
         */
        @Override
        @NotNull
        public String toString() {
            return super.toString() + ", ninth: " + _9th;
        }
    }

}
