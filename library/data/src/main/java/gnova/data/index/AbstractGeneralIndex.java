package gnova.data.index;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.data.index.key.*;
import gnova.query.expression.CompareOperator;
import gnova.query.expression.LogicalExpression;
import gnova.query.expression.MultiExpression;
import gnova.query.expression.SimpleExpression;
import gnova.core.function.MultiMapBuilder;
import gnova.core.EmptyIterator;
import gnova.core.PredicateIterator;
import gnova.core.MultiIterator;
import gnova.core.multimap.MultiMap;
import gnova.core.multimap.NavigableMultiMap;
import gnova.core.function.Getter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractGeneralIndex<E extends Getter>
        extends AbstractIndex<E> implements GeneralIndex<E> {

    private final MultiMapBuilder<Key, E> mmb;
    protected volatile MultiMap<Key, E> multiMap = null;
    protected final boolean ordered;

    /**
     * 构造器
     *
     * @param mmb MultiMap构造器，不允许为null
     * @param ordered
     * @param fieldNames
     */
    public AbstractGeneralIndex(@NotNull MultiMapBuilder<Key, E> mmb,
                                boolean ordered,
                                @Checked String ...fieldNames) {
        super(fieldNames);
        this.mmb = mmb;
        this.ordered = ordered;
    }

    @Override
    public boolean isOrdered() {
        return ordered;
    }

    @Override
    public boolean isUnique() {
        // 默认情况下，普通字段索引不是一个唯一索引
        return false;
    }

    @Override
    public boolean isSpatial() {
        // 普通字段索引一定不会是一个空间索引
        return false;
    }

    @Override
    public void clear() {
        multiMap = null;
    }

    @Override
    public void insert(E e) {
        if (multiMap == null) {
            multiMap = buildMultiMap();
        }
        Key key = buildKey(e);
        multiMap.put(key, e);
    }

    @Override
    public void delete(E e) {
        if (multiMap == null) {
            return;
        }
        Key key = buildKey(e);
        multiMap.remove(key);
    }

    @Override
    public Iterator<E> search(@Checked Object... values) {

        if (multiMap == null || multiMap.isEmpty()) {
            // 索引还没有构建起来
            return new EmptyIterator<>();
        }
        Key key = buildKeyByValue(values);
        Collection<E> result = multiMap.get(key);
        if (result == null) {
            // 没有查询到数据
            return new EmptyIterator<>();
        }
        return result.iterator();
    }

    @Override
    public Iterator<E> query(LogicalExpression expression) {

        if (multiMap == null || multiMap.isEmpty()) {
            // 索引还没有构建起来
            return new EmptyIterator<>();
        }

        if (isSingle()) {
            // 单字段索引
            SimpleExpression keyValue = expression.asSimple();

            if (keyValue.getCompareOperator() == CompareOperator.EQ) {
                // 等于
                return multiMap.get(buildKeyByValue(keyValue.getRightValue().getValue())).iterator();
            } else if (keyValue.getCompareOperator() == CompareOperator.IN) {
                // IN，TODO
                Object[] values = ((List) keyValue.getRightValue().getValue()).toArray();
                Iterator<E>[] iterators = new Iterator[values.length];
                int index = 0;
                for (Object value : values) {
                    iterators[index++] = multiMap.get(buildKeyByValue(value)).iterator();
                }
                return new MultiIterator<>(iterators);
            }

            if (isOrdered()) {
                // 有序索引
                NavigableMultiMap<Key, E> navigableMultiMap = (NavigableMultiMap<Key, E>) multiMap;
                Key key = buildKeyByValue(keyValue.getRightValue().getValue());
                if (keyValue.getCompareOperator() == CompareOperator.GT) {
                    // 大于
                    return navigableMultiMap.tailMultiMap(key, false).values().iterator();
                } else if (keyValue.getCompareOperator() == CompareOperator.GTE) {
                    // 大于等于
                    return navigableMultiMap.tailMultiMap(key, true).values().iterator();
                } else if (keyValue.getCompareOperator() == CompareOperator.LT) {
                    // 小于
                    return navigableMultiMap.headMultiMap(key, false).values().iterator();
                } else if (keyValue.getCompareOperator() == CompareOperator.LTE) {
                    // 小于等于
                    return navigableMultiMap.headMultiMap(key, true).values().iterator();
                } else if (keyValue.getCompareOperator() == CompareOperator.NEQ) {
                    // 不等于
                    return new MultiIterator<>(navigableMultiMap.headMultiMap(key, false).values().iterator(),
                            navigableMultiMap.tailMultiMap(key, false).values().iterator());
                }
            }

        } else {
            // 复合字段索引
            MultiExpression multi = expression.asMulti();
            // TODO
        }

        return new PredicateIterator<>(multiMap.values().iterator(),
                val -> expression.fit(val));
    }

    /**
     * 构建一个索引的Key
     *
     * @param e 数据，不允许为null
     * @return
     */
    protected Key buildKey(@NotNull E e) {
        Object[] values = new Object[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            values[i] = e.getValue(fieldNames[i]);
        }
        return buildKeyByValue(values);
    }

    /**
     * 根据值构建一个索引的Key
     *
     * @param values 值，不允许为null或空
     * @return
     */
    protected Key buildKeyByValue(@Checked Object ...values) {
        if (values.length == 1) {
            // 单字段
            if (ordered) {
                // 有序
                return new SingleOrderKey(values[0]);
            } else {
                // 无序
                return new SingleUnorderKey(values[0]);
            }
        } else {
            // 多字段
            if (ordered) {
                // 有序
                return new MultiOrderKey(values);
            } else {
                // 无序
                return new MultiUnorderKey(values);
            }
        }
    }

    protected MultiMap<Key, E> buildMultiMap() {
        return mmb.build();
    }
}
