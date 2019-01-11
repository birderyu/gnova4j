package gnova.data.index;

import gnova.core.annotation.NotNull;
import gnova.query.expression.PredicateExpression;
import gnova.query.expression.LogicalPredicate;
import gnova.query.expression.MultipleExpression;
import gnova.query.expression.SimpleExpression;
import gnova.core.function.MapBuilder;
import gnova.core.function.MultiMapBuilder;
import gnova.core.EmptyIterator;
import gnova.core.multimap.MultiMap;
import gnova.data.Index;
import gnova.data.Indices;

import java.util.*;

public abstract class AbstractIndices<E>
        implements Indices<E> {

    /**
     * 索引名 - 索引
     */
    private Map<String, Index<E>> indices = null;

    /**
     * 字段名 - 单键索引
     */
    private Map<String, SingleIndex<E>> singles = null;

    /**
     * 字段名 - 多键索引
     */
    private MultiMap<String, MultiIndex<E>> multis = null;

    private final MapBuilder<String, Index<E>> midb;
    private final MapBuilder<String, SingleIndex<E>> msidb;
    private final MultiMapBuilder<String, MultiIndex<E>> mmmidb;
    private final GeneralIndexBuilder<E> gidb;
    private final UniqueIndexBuilder<E> uidb;
    private final SpatialIndexBuilder<E> sidb;

    public AbstractIndices(@NotNull MapBuilder<String, Index<E>> midb,
                           @NotNull MapBuilder<String, SingleIndex<E>> msidb,
                           @NotNull MultiMapBuilder<String, MultiIndex<E>> mmmidb,
                           @NotNull GeneralIndexBuilder<E> gidb,
                           @NotNull UniqueIndexBuilder<E> uidb,
                           @NotNull SpatialIndexBuilder<E> sidb) {
        this.midb = midb;
        this.msidb = msidb;
        this.mmmidb = mmmidb;
        this.gidb = gidb;
        this.uidb = uidb;
        this.sidb = sidb;
    }

    @Override
    public int size() {
        if (indices == null) {
            return 0;
        }
        return indices.size();
    }

    @Override
    public Index<E> get(String name) {
        if (indices == null) {
            return null;
        }
        return indices.get(name);
    }

    @Override
    public Index<E> build(String name, String[] fieldNames, boolean ordered) {

        if (fieldNames.length <= 0) {
            throw new IllegalArgumentException("fieldNames is empty.");
        }

        // 构建索引
        Index<E> index = gidb.build(ordered, fieldNames);

        // 将索引添加进列表
        addToCache(name, fieldNames, index);

        return index;

    }

    @Override
    public UniqueIndex<E> buildUnique(String name, String[] fieldNames, boolean ordered) {

        if (fieldNames.length <= 0) {
            throw new IllegalArgumentException("fieldNames is empty.");
        }

        // 构建索引
        UniqueIndex<E> index = uidb.build(ordered, fieldNames);

        // 将索引添加进列表
        addToCache(name, fieldNames, index);

        return index;
    }

    @Override
    public SpatialIndex<E> buildSpatial(String name, String fieldName) {

        if (fieldName.isEmpty()) {
            throw new IllegalArgumentException("fieldName is empty.");
        }

        // 构建索引
        SpatialIndex<E> index = sidb.build(fieldName);

        // 将索引添加进列表
        addToCache(name, new String[] {fieldName}, index);

        return index;
    }

    @Override
    public void drop(String name) {
        if (indices == null) {
            return;
        }
        Index<E> index = indices.remove(name);
        if (index.isSingle()) {
            String field = index.asSingle().getFieldName();
            singles.remove(field);
        } else {
            String[] fields = index.asMulti().getFieldNames();
            for (String field : fields) {
                Collection<MultiIndex<E>> _indices = multis.get(field);
                _indices.remove(index);
            }
        }
    }

    @Override
    public void dropAll() {
        indices = null;
        singles = null;
        multis = null;
    }

    @Override
    public void clear() {
        if (indices == null) {
            return;
        }
        for (Map.Entry<String, Index<E>> index : indices.entrySet()) {
            index.getValue().clear();
        }
    }

    @Override
    public void insert(E e) {
        if (indices == null) {
            return;
        }
        for (Map.Entry<String, Index<E>> entry : indices.entrySet()) {
            entry.getValue().insert(e);
        }
    }

    @Override
    public void replace(E before, E after) {
        if (indices == null) {
            return;
        }
        for (Map.Entry<String, Index<E>> entry : indices.entrySet()) {
            entry.getValue().replace(before, after);
        }
    }

    @Override
    public void delete(E e) {
        if (indices == null) {
            return;
        }
        for (Map.Entry<String, Index<E>> entry : indices.entrySet()) {
            entry.getValue().delete(e);
        }
    }

    @Override
    public QueryResult<E> query(PredicateExpression expression) {

        if (indices == null || indices.isEmpty()) {
            // 索引为空，无法完成任何查询
            return new QueryResult<>(null, null, expression);
        }

        if (expression.isAlwaysTrue() || expression.isAlwaysFalse()) {
            return new QueryResult<>(null, null, expression);
        } else if (expression.isSimple()) {
            // 简单表达式，可以匹配上一个单字段索引
            SimpleExpression keyValue = expression.asSimple();
            if (singles == null || singles.isEmpty()) {
                // 单字段索引为空，无法完成任何查询
                return new QueryResult<E>(null, null, expression);
            } else {
                Index<E> index = singles.get(keyValue.getLeft().asKey());
                if (index == null) {
                    // 找不到对应字段的单字段索引，无法完成任何查询
                    return new QueryResult<E>(null, null, expression);
                } else {
                    // 完成索引查询
                    Iterator<E> left = index.query(expression);
                    return new QueryResult<E>(left, null, null);
                }
            }
        } else {
            // 复合表达式，需要判断是否能够匹配索引
            MultipleExpression multi = expression.asMultiple();
            LogicalPredicate link = multi.getPredicate();

            if (link.isXor()) {
                // TODO，亦或，无法使用索引
                return new QueryResult<>(null, null, expression);
            }

            PredicateExpression left = multi.getLeft();
            PredicateExpression right = multi.getRight();

            if (left.isAlwaysTrue() || left.isAlwaysFalse()) {
                // 若left为恒真，此时操作符一定是and，因为如果是or，则二者恒真
                // 若left为恒假，此时操作符一定是or，因为如果是and，则二者恒假
                // 无论如何，只需要查询右侧即可
                return query(right);
            } else if (right.isAlwaysTrue() || right.isAlwaysFalse()) {
                // 同理，只需要查询左侧即可
                return query(left);
            }

            // 左右皆不为恒真 or 恒假
            if (left.isSimple() && right.isSimple() && link.isAnd()) {
                // 左右皆为简单表达式，查看是否可以匹配索引
                if (multis == null || multis.isEmpty()) {
                    // 复合字段索引为空，无法完成任何查询
                    // do nothing
                } else {
                    SimpleExpression _left = left.asSimple();
                    Collection<MultiIndex<E>> _indices = multis.get(_left.getLeft().asKey());
                    if (_indices == null) {
                        // 找不到对应字段的复合字段索引，无法完成任何查询
                        // do nothing
                    } else {

                        SimpleExpression _right = right.asSimple();

                        // 找到最佳的索引
                        Index<E> index = null;
                        for (MultiIndex<E> _index : _indices) {
                            if (_index.indexOf(_right.getLeft().asKey()) != -1
                                    && _index.getFieldNames().length == 2) {
                                index = _index;
                                break;
                            }
                        }

                        if (index == null) {
                            // 找不到对应字段的复合字段索引，无法完成任何查询
                            // do nothing
                        } else {
                            // 完成索引查询
                            Iterator<E> __left = index.query(expression);
                            return new QueryResult<E>(__left, null, null);
                        }
                    }
                }
            }

            // 优先查询左侧
            QueryResult<E> result = query(left);
            if (result.getLeft() == null) {
                // 左侧未能查询出来，重新全部查询
                return new QueryResult<E>(null, null, expression);
            } else if (result.getRight() == null) {
                // 左侧完全查询出来
                return new QueryResult<E>(result.getLeft(), link, right);
            } else {
                // 左侧部分查询出来
                // TODO，重新全部查询
                return new QueryResult<E>(null, null, expression);
            }

        }
    }

    @Override
    public AbstractIndices<E> cloneEmpty() {
        AbstractIndices<E> ids = new AbstractIndices<E>(midb, msidb, mmmidb, gidb, uidb, sidb) {};
        if (indices == null) {
            return ids;
        }
        for (Map.Entry<String, Index<E>> entry : indices.entrySet()) {
            Index<E> index = entry.getValue();
            if (index.isSpatial()) {
                ids.buildSpatial(entry.getKey(), index.asSingle().getFieldName());
            } else if (index.isUnique()) {
                if (index.isSingle()) {
                    ids.buildUnique(entry.getKey(),
                            new String[] {index.asSingle().getFieldName()},
                            ((UniqueIndex<E>) index).isOrdered());
                } else {
                    ids.buildUnique(entry.getKey(),
                            index.asMulti().getFieldNames(),
                            ((UniqueIndex<E>) index).isOrdered());
                }
            } else {
                if (index.isSingle()) {
                    ids.build(entry.getKey(),
                            new String[] {index.asSingle().getFieldName()},
                            ((GeneralIndex<E>) index).isOrdered());
                } else {
                    ids.build(entry.getKey(),
                            index.asMulti().getFieldNames(),
                            ((GeneralIndex<E>) index).isOrdered());
                }
            }
        }
        return ids;
    }

    @Override
    public Iterator<Index<E>> iterator() {
        if (indices == null) {
            return new EmptyIterator<>();
        }
        return indices.values().iterator();
    }

    /**
     * 将索引本身添加进缓存
     *
     * @param name
     * @param fieldNames
     * @param index
     * @throws IllegalArgumentException 若存在同名索引，则抛出此异常
     */
    protected void addToCache(String name, String[] fieldNames, Index<E> index) {

        if (indices == null) {
            indices = midb.build();
        } else if (indices.get(name) != null) {
            throw new IllegalArgumentException("index has already exist: " + name);
        }

        indices.put(name, index);

        if (fieldNames.length == 1) {
            if (singles == null) {
                singles = msidb.build();
            }
            singles.put(fieldNames[0], index.asSingle());
        } else {
            if (multis == null) {
                multis = mmmidb.build();
            }
            for (String fieldName : fieldNames) {
                multis.put(fieldName, index.asMulti());
            }
        }
    }

}
