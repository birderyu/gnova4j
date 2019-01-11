package gnova.data.index;

import gnova.core.annotation.Checked;
import gnova.data.Index;
import gnova.query.expression.PredicateExpression;
import gnova.core.function.Getter;

import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractIndex<E extends Getter>
        implements Index<E> {

    /**
     * 字段名称
     */
    protected final String[] fieldNames;

    /**
     * 等效的一个单字段索引
     */
    private SingleIndex<E> singleIndex = null;

    /**
     * 等效的一个多字段索引
     */
    private MultiIndex<E> multiIndex = null;

    /**
     * 构造器
     *
     * @param fieldNames 字段名称，不允许为null或为空，请在外部作出检查，内部不会再次检查数据的合法性
     */
    public AbstractIndex(@Checked String ... fieldNames) {
        this.fieldNames = fieldNames;
    }

    @Override
    public boolean isSingle() {
        return fieldNames.length == 1;
    }

    @Override
    public SingleIndex<E> asSingle() {

        if (!isSingle()) {
            return null;
        }
        if (singleIndex == null) {
            singleIndex = new SingleIndex<E>() {

                @Override
                public boolean isSingle() {
                    return true;
                }

                @Override
                public boolean isUnique() {
                    return AbstractIndex.this.isUnique();
                }

                @Override
                public boolean isSpatial() {
                    return AbstractIndex.this.isSpatial();
                }

                @Override
                public SingleIndex<E> asSingle() {
                    return this;
                }

                @Override
                public MultiIndex<E> asMulti() {
                    return null;
                }

                @Override
                public void clear() {
                    AbstractIndex.this.clear();
                }

                @Override
                public void insert(E e) {
                    AbstractIndex.this.insert(e);
                }

                @Override
                public void delete(E e) {
                    AbstractIndex.this.delete(e);
                }

                @Override
                public Iterator<E> search(Object... values) {
                    return AbstractIndex.this.search(values);
                }

                @Override
                public Iterator<E> query(PredicateExpression expression) {
                    return AbstractIndex.this.query(expression);
                }

                @Override
                public String getFieldName() {
                    return fieldNames[0];
                }

                @Override
                public int hashCode() {
                    return AbstractIndex.this.hashCode();
                }

                @Override
                public boolean equals(Object obj) {
                    return AbstractIndex.this.equals(obj);
                }

                @Override
                public String toString() {
                    return AbstractIndex.this.toString();
                }
            };
        }
        return singleIndex;
    }

    @Override
    public MultiIndex<E> asMulti() {

        if (isSingle()) {
            return null;
        }
        if (multiIndex == null) {
            multiIndex = new MultiIndex<E>() {

                @Override
                public boolean isSingle() {
                    return false;
                }

                @Override
                public boolean isUnique() {
                    return AbstractIndex.this.isUnique();
                }

                @Override
                public boolean isSpatial() {
                    return AbstractIndex.this.isSpatial();
                }

                @Override
                public SingleIndex<E> asSingle() {
                    return null;
                }

                @Override
                public MultiIndex<E> asMulti() {
                    return this;
                }

                @Override
                public void clear() {
                    AbstractIndex.this.clear();
                }

                @Override
                public void insert(E e) {
                    AbstractIndex.this.insert(e);
                }

                @Override
                public void delete(E e) {
                    AbstractIndex.this.delete(e);
                }

                @Override
                public Iterator<E> search(Object... values) {
                    return AbstractIndex.this.search(values);
                }

                @Override
                public Iterator<E> query(PredicateExpression expression) {
                    return AbstractIndex.this.query(expression);
                }

                @Override
                public String[] getFieldNames() {
                    return AbstractIndex.this.fieldNames;
                }

                @Override
                public int indexOf(String fieldName) {
                    for (int i = 0; i < AbstractIndex.this.fieldNames.length; i++) {
                        if (AbstractIndex.this.fieldNames[i].equalsIgnoreCase(fieldName)) {
                            return i;
                        }
                    }
                    return -1;
                }

                @Override
                public int hashCode() {
                    return AbstractIndex.this.hashCode();
                }

                @Override
                public boolean equals(Object obj) {
                    return AbstractIndex.this.equals(obj);
                }

                @Override
                public String toString() {
                    return AbstractIndex.this.toString();
                }
            };
        }
        return multiIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractIndex<?> that = (AbstractIndex<?>) o;
        return Objects.equals(fieldNames, that.fieldNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldNames);
    }
}
