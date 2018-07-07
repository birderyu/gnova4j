package gnova.data.index;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.data.index.key.Key;
import gnova.core.function.Getter;
import gnova.core.function.MultiMapBuilder;

public abstract class AbstractUniqueIndex<E extends Getter>
        extends AbstractGeneralIndex<E> implements UniqueIndex<E> {

    public AbstractUniqueIndex(@NotNull MultiMapBuilder<Key, E> mmb,
                               boolean ordered,
                               @Checked String ...fields) {
        super(mmb, ordered, fields);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public void insert(E e) {
        if (multiMap == null) {
            multiMap = buildMultiMap();
        }
        Key key = buildKey(e);
        if (multiMap.containsKey(key)) {
            throw new IllegalArgumentException("不允许向唯一索引中添加一个已经存在的值：" + key);
        }
        multiMap.put(key, e);
    }
}
