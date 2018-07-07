package gnova.core.multimap.proxy;

import gnova.core.annotation.NotNull;
import gnova.core.AbstractReadOnlyCollection;
import gnova.core.ReadOnlyCollection;
import gnova.core.ReadOnlyIterable;
import gnova.core.ReadOnlyIterator;
import gnova.core.multimap.MultiMap;

import java.util.*;

/**
 * 使用Map作为代理去实现一个MultiMap
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @see MultiMap
 * @author birderyu
 * @version 1.0.0
 */
public abstract class MultiMapProxy<K, V>
        implements MultiMap<K, V> {

    /**
     * Map对象
     */
    volatile private Map<K, Collection<V>> map;

    /**
     * 当前容器中元素的个数
     */
    volatile private int size = 0;

    /**
     * 键值对的集合
     */
    transient Set<Entry<K,V>> entrySet;

    /**
     * 创建一个空的Map，交由子类实现
     *
     * @return Map对象，不会返回null
     */
    @NotNull
    protected abstract Map<K, Collection<V>> buildEmptyMap();

    /**
     * 创建一个空的容器，交由子类实现
     *
     * 这个集合必须是一个{@link CollectionProxy CollectionProxy}的子类，它是一个容器的代理实现
     *
     * @return 容器对象，不会返回null
     */
    @NotNull
    protected abstract CollectionProxy<V> buildEmptyCollection();

    /**
     * 获取Map对象
     *
     * @return Map对象
     */
    @NotNull
    protected Map<K, Collection<V>> getMap() {
        return map;
    }

    /**
     * 设置Map对象
     *
     * @param map Map对象，不允许为null
     */
    protected void setMap(@NotNull Map<K, Collection<V>> map) {
        this.map = map;
        count();
    }

    /**
     * 获取值的总数
     *
     * @return 值的总数
     * @see MultiMap#size()
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 判断容器是否为空
     *
     * @return 若容器为空，则返回true，否则为false
     * @see MultiMap#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 判断是否包含键以及关联该键的值
     *
     * @param key 键
     * @param value 值
     * @return 若包含该键，并且包含关联该键的值，则返回true，否则返回false
     * @see MultiMap#contains(Object, Object)
     */
    @Override
    public boolean contains(K key, V value) {
        Collection<V> c = map.get(key);
        if (c == null) {
            return false;
        }
        return c.contains(value);
    }

    /**
     * 判断是否包含键
     *
     * @param key 键
     * @return 若包含该键，则返回true，否则返回false
     * @see MultiMap#containsKey(Object)
     */
    @Override
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    /**
     * 判断是否包含值
     *
     * @param value 值
     * @return 若包含该值，则返回true，否则返回false
     * @see MultiMap#containsValue(Object)
     */
    @Override
    public boolean containsValue(V value) {
        for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
            if (entry.getValue().contains(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取键所关联的值的集合
     *
     * @param key 键
     * @return 该键所关联的值的集合，若不存在该键，则返回null
     * @see MultiMap#get(Object)
     */
    @Override
    public Collection<V> get(K key) {
        return map.get(key);
    }

    /**
     * 将一个键值对放入容器
     *
     * @param key 键
     * @param value 值
     * @return 若成功，则返回true，否则返回false
     * @see MultiMap#put(Object, Object)
     */
    @Override
    public boolean put(K key, V value) {
        Collection<V> c = get(key);
        if (c == null) {
            c = this.buildEmptyCollection();
            map.put(key, c);
        }
        // 不需要size++，因为这里的c是一个代理，内部实现了size++的逻辑
        return c.add(value);
    }

    /**
     * 从容器中移除一个键，返回键所关联的值的集合
     *
     * @param key 键
     * @return 返回该键所关联的所有值，若键不存在，则返回null
     * @see MultiMap#remove(Object)
     */
    @Override
    public Collection<V> remove(K key) {
        Collection<V> c = map.remove(key);
        if (c != null) {
            size -= c.size();
        }
        return c;
    }

    /**
     * 移除键下面关联的一个值
     *
     * @param key 键
     * @param value 值
     * @return 若移除成功，则返回true，否则返回false
     * @see MultiMap#remove(Object, Object)
     */
    @Override
    public boolean remove(K key, V value) {
        Collection<V> c = map.get(key);
        if (c == null) {
            return false;
        }
        // 不需要size--，因为这里的c是一个代理，内部实现了size--的逻辑
        return c.remove(value);
    }

    /**
     * 批量放置键值对
     *
     * @param m 容器
     * @see MultiMap#putAll(Map)
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 批量放置键值对
     *
     * @param mm 容器
     * @see MultiMap#putAll(MultiMap)
     */
    @Override
    public void putAll(MultiMap<? extends K, ? extends V> mm) {
        for (Entry<? extends K, ? extends V> entry : mm.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 清空键值对
     *
     * @see MultiMap#clear()
     */
    @Override
    public void clear() {
        map.clear();
        size = 0;
    }

    /**
     * 返回键的集合
     * 重复的键只会返回一次
     *
     * @return 键的集合，若集合为空，则返回一个空的集合，不会返回null
     * @see MultiMap#keySet()
     */
    @Override
    @NotNull
    public Set<K> keySet() {
        return map.keySet();
    }

    /**
     * 返回所有值的容器
     * 这个集合将是一个只读容器{@link ReadOnlyCollection ReadOnlyCollection}，
     * 任何在该容器上的写操作，都将抛出UnsupportedOperationException异常
     *
     * @return 值的容器，若容器为空，则返回一个空的容器，不会返回null
     * @see MultiMap#values()
     */
    @Override
    @NotNull
    public MultiCollection values() {
        return new MultiCollection(map.values());
    }

    /**
     * 返回所有键值对的集合
     *
     * @return 键值对的集合，若集合为空，则返回一个空的集合，不会返回null
     * @see MultiMap#entrySet()
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> es = entrySet;
        return es == null ? (entrySet = new MultiMapEntrySet()) : es;
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
        if (o instanceof MultiMapProxy) {
            return map.equals(((MultiMapProxy) o).map);
        }
        return false;
    }

    /**
     * 获取当前对象的散列值
     *
     * @return 散列值
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return map.hashCode();
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
        return map.toString();
    }

    /**
     * 求取当前map对象中值的总数
     */
    private void count() {
        if (map == null) {
            size = 0;
            return;
        }
        int s = 0;
        for (Map.Entry<K, Collection<V>> entry : map.entrySet()) {
            s += entry.getValue().size();
        }
        size = s;
    }

    /**
     * MultiMap键值对的集合
     *
     * @see AbstractSet
     * @see Set
     */
    protected class MultiMapEntrySet
            extends AbstractSet<Entry<K, V>> implements Set<Entry<K, V>> {

        /**
         * 获取键值对的总数
         *
         * @return 键值对的总数
         * @see Set#size()
         */
        @Override
        public int size() {
            return MultiMapProxy.this.size();
        }

        /**
         * 清空键值对
         *
         * @see Set#clear()
         */
        @Override
        public void clear() {
            MultiMapProxy.this.clear();
        }

        /**
         * 判断是否包含键值对
         *
         * @param o 对象
         * @return 若包含，则返回true，否则返回false
         * @see Set#contains(Object)
         */
        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }

            Entry<? extends K, ? extends V> e = (Entry<? extends K, ? extends V>) o;
            Collection<? extends V> c = MultiMapProxy.this.get(e.getKey());
            if (c == null) {
                return false;
            }
            return c.contains(e.getValue());
        }

        /**
         * 移除一个键值对
         *
         * @param o 对象
         * @return 若移除成功，则返回true，否则返回false
         * @see Set#remove(Object)
         */
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Entry)) {
                return false;
            }
            Entry<? extends K, ? extends V> e = (Entry<? extends K, ? extends V>) o;
            return MultiMapProxy.this.remove(e.getKey(), e.getValue());
        }

        /**
         * 获取键值对的迭代器
         *
         * @return 迭代器
         * @see Set#iterator()
         */
        @Override
        public Iterator<Entry<K, V>> iterator() {
            return new MultiMapIterator(MultiMapProxy.this.map.entrySet().iterator());
        }
    }

    /**
     * 简单键值对
     *
     * @param <K> 键的类型
     * @param <V> 值的类型
     * @see Entry
     */
    protected static final class SimpleEntry<K, V>
            implements Entry<K, V> {

        /**
         * 键
         */
        protected volatile K key;

        /**
         * 值
         */
        protected volatile V value;

        /**
         * 构造一个简单键值对
         *
         * @param key 键
         * @param value 值
         */
        public SimpleEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * 获取键
         *
         * @return 键对象
         * @see Entry#getKey()
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * 获取值
         *
         * @return 值对象
         * @see Entry#getValue()
         */
        @Override
        public V getValue() {
            return value;
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
            if (o == this) {
                return true;
            }
            if (o instanceof Entry) {
                Entry<? extends K, ? extends V> e = (Entry<? extends K, ? extends V>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue())) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
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
            return key + "=" + value;
        }
    }

    /**
     * MultiMap键值对迭代器
     *
     * @see ReadOnlyIterator
     * @see Entry
     */
    protected class MultiMapIterator
            implements ReadOnlyIterator<Entry<K, V>> {

        /**
         * Map对象键值对的迭代器
         */
        protected volatile Iterator<Map.Entry<K, Collection<V>>> mapIterator;

        /**
         * 当前的键值对
         */
        protected volatile Map.Entry<K, Collection<V>> currentEntry = null;

        /**
         * 当前键值对值的迭代器
         */
        protected volatile Iterator<V> valueIterator = null;

        /**
         * 构造一个MultiMap键值对迭代器
         *
         * @param mapIterator Map对象键值对的迭代器
         */
        public MultiMapIterator(Iterator<Map.Entry<K, Collection<V>>> mapIterator) {
            this.mapIterator = mapIterator;

            if (this.mapIterator != null && this.mapIterator.hasNext()) {
                currentEntry = this.mapIterator.next();
                valueIterator = currentEntry.getValue().iterator();
            }
        }

        /**
         * 判断是否还有下一个元素
         *
         * @return 若还有下一个元素，则返回true，否则返回false
         * @see Iterator#hasNext()
         */
        @Override
        public boolean hasNext() {
            if (mapIterator == null || valueIterator == null) {
                return false;
            }
            if (valueIterator.hasNext()) {
                return true;
            }

            // 找到下一个迭代器
            while (!valueIterator.hasNext()) {
                if (!mapIterator.hasNext()) {
                    break;
                }
                currentEntry = mapIterator.next();
                valueIterator = currentEntry.getValue().iterator();
            }

            return valueIterator.hasNext();
        }

        /**
         * 返回下一个元素
         *
         * @return 元素对象
         * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
         * @see Iterator#next()
         */
        @Override
        public Entry<K, V> next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return new SimpleEntry(currentEntry.getKey(), valueIterator.next());
        }

    }

    /**
     * 多容器
     *
     * 将多个Collection组合成一个Collection，这个容器是只读的
     * 由于这个集合对于写操作是禁止的（包括迭代器的写操作），因此不存在线程安全的问题，可以不需要派生
     *
     * @see AbstractReadOnlyCollection
     * @see ReadOnlyCollection
     */
    private final class MultiCollection
            extends AbstractReadOnlyCollection<V> implements ReadOnlyCollection<V> {

        /**
         * 值的集合的集合
         */
        protected volatile Collection<Collection<V>> collections;

        /**
         * 构建一个多集合
         *
         * @param collections 值的集合的集合
         */
        public MultiCollection(Collection<Collection<V>> collections) {
            this.collections = collections;
        }

        /**
         * 获取元素的迭代器
         *
         * @return 只读的迭代器，不会返回null
         * @see ReadOnlyIterable#iterator()
         * @see ReadOnlyIterator
         */
        @Override
        @NotNull
        public MultiCollectionIteratorProxy iterator() {
            return new MultiCollectionIteratorProxy(collections.iterator());
        }

        /**
         * 获取元素的总数
         *
         * @return 元素的总数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return MultiMapProxy.this.size;
        }

        /**
         * 判断是否包含了该对象
         *
         * @param o 对象
         * @return 若包含，则返回true，否则返回false
         * @see Collection#contains(Object)
         */
        @Override
        public boolean contains(Object o) {
            return MultiMapProxy.this.containsValue((V) o);
        }

        /**
         * 判断容器是否为空
         *
         * @return 若为空，则返回true，否则返回false
         * @see Collection#isEmpty()
         */
        @Override
        public boolean isEmpty() {
            return MultiMapProxy.this.isEmpty();
        }

        /**
         * 多集合迭代器
         *
         * @see ReadOnlyIterator
         */
        protected class MultiCollectionIteratorProxy implements ReadOnlyIterator<V> {

            /**
             * 值的集合的集合的迭代器
             */
            protected volatile Iterator<Collection<V>> iterators;

            /**
             * 值的集合的迭代器
             */
            protected volatile Iterator<V> iterator = null;

            /**
             * 构造一个多集合迭代器
             *
             * @param iterators 值的集合的集合的迭代器
             */
            public MultiCollectionIteratorProxy(Iterator<Collection<V>> iterators) {
                this.iterators = iterators;
            }

            /**
             * 判断是否还有下一个元素
             *
             * @return 若还有下一个元素，则返回true，否则返回false
             * @see Iterator#hasNext()
             */
            @Override
            public boolean hasNext() {
                return tryNext();
            }

            /**
             * 返回下一个元素
             *
             * @return 元素对象
             * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
             * @see Iterator#next()
             */
            public V next() throws NoSuchElementException {
                if (tryNext()) {
                    return iterator.next();
                }
                throw new NoSuchElementException();
            }

            /**
             * 尝试移动到下一个元素，并返回是否包含了下一个元素
             *
             * @return 若包含下一个元素，则返回true，否则返回false
             */
            private boolean tryNext() {
                if (iterators == null) {
                    return false;
                } else if (iterator == null) {
                    iterator = iterators.next().iterator();
                }

                while (!iterator.hasNext()) {
                    if (iterators.hasNext()) {
                        iterator = iterators.next().iterator();
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }

    }

    /**
     * 一个容器类的代理
     *
     * 无法保证线程安全，因为有些操作并非单纯的调用代理类，
     * 如果对于线程的安全性有要求，则需要派生
     *
     * @param <E> 元素的类型
     * @see Collection
     */
    protected class CollectionProxy<E>
            implements Collection<E> {

        /**
         * 被代理的容器对象
         */
        private volatile Collection<E> subject;

        /**
         * 构造一个集合类的代理对象
         *
         * @param subject 被代理的容器对象
         */
        public CollectionProxy(Collection<E> subject) {
            this.subject = subject;
        }

        /**
         * 获取元素的总数
         *
         * @return 元素的总数
         * @see Collection#size()
         */
        @Override
        public int size() {
            return subject.size();
        }

        /**
         * 判断容器是否为空
         *
         * @return 若为空，则返回true，否则返回false
         * @see Collection#isEmpty()
         */
        @Override
        public boolean isEmpty() {
            return subject.isEmpty();
        }

        /**
         * 判断是否包含了该对象
         *
         * @param o 对象
         * @return 若包含，则返回true，否则返回false
         * @see Collection#contains(Object)
         */
        @Override
        public boolean contains(Object o) {
            return subject.contains(o);
        }

        /**
         * 获取元素的迭代器
         *
         * @return 迭代器，不会返回null
         * @see Iterable#iterator()
         * @see CollectionIteratorProxy
         */
        @Override
        @NotNull
        public CollectionIteratorProxy<E> iterator() {
            return new CollectionIteratorProxy<E>(subject.iterator());
        }

        /**
         * 转换为数组
         *
         * @return 数组，不会返回null
         * @see Collection#toArray()
         */
        @Override
        @NotNull
        public Object[] toArray() {
            return subject.toArray();
        }

        /**
         * 转换为特定元素类型的数组
         *
         * @param a 数组，不允许为null
         * @param <T> 数组元素的类型
         * @return 数组，不会返回null
         * @throws ClassCastException 若元素转换失败，则抛出此异常
         * @see Collection#toArray(Object[])
         */
        @Override
        @NotNull
        public <T> T[] toArray(T[] a) {
            return subject.toArray(a);
        }

        /**
         * 向容器中添加一个元素
         *
         * @param e 元素
         * @return 若添加成功，则返回true，否则返回false
         * @see Collection#add(Object)
         */
        @Override
        public boolean add(E e) {
            MultiMapProxy.this.size -= this.size();
            boolean r = subject.add(e);
            MultiMapProxy.this.size += this.size();
            return r;
        }

        /**
         * 从容器中移除一个对象
         *
         * @param o 对象
         * @return 若移除成功，则返回true，否则返回false
         * @see Collection#remove(Object)
         */
        @Override
        public boolean remove(Object o) {
            MultiMapProxy.this.size -= this.size();
            boolean r = subject.remove(o);
            MultiMapProxy.this.size += this.size();
            return r;
        }

        /**
         * 判断容器中是否包含另一个容器中的所有对象
         *
         * @param c 容器对象
         * @return 若包含该容器中的所有对象，则返回true，否则返回false
         * @see Collection#containsAll(Collection)
         */
        @Override
        public boolean containsAll(Collection<?> c) {
            return subject.containsAll(c);
        }

        /**
         * 向容器中添加若干个元素
         *
         * @param c 元素的容器
         * @return 若添加成功，则返回true，否则返回false
         * @see Collection#addAll(Collection)
         */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            MultiMapProxy.this.size -= this.size();
            boolean r = subject.addAll(c);
            MultiMapProxy.this.size += this.size();
            return r;
        }

        /**
         * 从容器中移除若干个对象
         *
         * @param c 对象的容器
         * @return 若移除成功，则返回true，否则返回false
         * @see Collection#removeAll(Collection)
         */
        @Override
        public boolean removeAll(Collection<?> c) {
            MultiMapProxy.this.size -= this.size();
            boolean r = subject.removeAll(c);
            MultiMapProxy.this.size += this.size();
            return r;
        }

        /**
         * 保留容器中的若干元素，移除其余元素
         *
         * @param c 需要保留的元素的容器
         * @return 若执行成功，则返回true，否则返回false
         * @see Collection#retainAll(Collection)
         */
        @Override
        public boolean retainAll(Collection<?> c) {
            MultiMapProxy.this.size -= this.size();
            boolean r = subject.retainAll(c);
            MultiMapProxy.this.size += this.size();
            return r;
        }

        /**
         * 清空容器中的所有元素
         *
         * @see Collection#clear()
         */
        @Override
        public void clear() {
            MultiMapProxy.this.size -= this.size();
            subject.clear();
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
            return subject.equals(o);
        }

        /**
         * 获取当前对象的散列值
         *
         * @return 散列值
         * @see Object#hashCode()
         */
        @Override
        public int hashCode() {
            return subject.hashCode();
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
            return subject.toString();
        }

        /**
         * 集合代理的迭代器
         *
         * @param <E> 集合元素的类型
         * @see Iterator
         */
        protected class CollectionIteratorProxy<E>
                implements Iterator<E> {

            /**
             * 被代理的集合的迭代器
             */
            private volatile Iterator<E> iterator;

            /**
             * 构造一个集合代理的迭代器
             *
             * @param iterator 被代理的集合的迭代器
             */
            public CollectionIteratorProxy(Iterator<E> iterator) {
                this.iterator = iterator;
            }

            /**
             * 判断是否还有下一个元素
             *
             * @return 若还有下一个元素，则返回true，否则返回false
             * @see Iterator#hasNext()
             */
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            /**
             * 返回下一个元素
             *
             * @return 元素对象
             * @throws NoSuchElementException 若没有下一个元素，则抛出此异常
             * @see Iterator#next()
             */
            @Override
            public E next() throws NoSuchElementException {
                return iterator.next();
            }

            /**
             * 移除next方法调用之后的元素
             *
             * @throws UnsupportedOperationException 若迭代器不支持该方法，则抛出此异常
             * @throws IllegalStateException 若调用该方法前没有调用next方法，则抛出此异常
             * @see Iterator#remove()
             */
            @Override
            public void remove() throws UnsupportedOperationException, IllegalStateException {
                iterator.remove();
                --MultiMapProxy.this.size;
            }
        }
    }

}
