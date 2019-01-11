package gnova.data.index.key;

import gnova.core.annotation.Checked;

import java.util.Arrays;

/**
 * 多键值，用于构建多键索引
 */
public abstract class MultiKey
        implements Key {

    protected final Object[] values;

    /**
     * 构造器
     *
     * @param values 不允许为null，也不允许为空
     */
    public MultiKey(@Checked Object... values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MultiKey)) return false;

        MultiKey multiKey = (MultiKey) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(values, multiKey.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
