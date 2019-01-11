package gnova.data.index.key;

import gnova.core.annotation.NotNull;

/**
 * 单值键
 */
public abstract class SingleKey
        implements Key {

    /**
     *
     * @param value 不允许为null
     * @param ordered
     * @return
     * @throws IllegalArgumentException 若ordered为true且值是无法排序的，则抛出此异常
     */
    public static SingleKey build(@NotNull Object value, boolean ordered)
            throws IllegalArgumentException {
        if (ordered) {
            return new SingleOrderedKey(value);
        } else {
            return new SingleUnorderedKey(value);
        }
    }

    protected final Object value;

    public SingleKey(@NotNull Object value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SingleKey)) return false;

        SingleKey singleKey = (SingleKey) o;

        return value != null ? value.equals(singleKey.value) : singleKey.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
