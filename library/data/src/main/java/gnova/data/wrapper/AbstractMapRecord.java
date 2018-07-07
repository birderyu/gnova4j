package gnova.data.wrapper;

import gnova.annotation.NotNull;
import gnova.data.Record;
import gnova.util.KeyValue;
import gnova.function.MapBuilder;
import gnova.util.EmptyIterator;
import gnova.util.ReadOnlyIterator;

import java.util.Iterator;
import java.util.Map;

public abstract class AbstractMapRecord
        extends AbstractReadOnlyMapRecord implements Record {

    public AbstractMapRecord(@NotNull Object key,
                             Map<String, Object> map,
                             @NotNull MapBuilder<String, Object> builder) {
        super(key, map, builder);
    }

    @Override
    public <T> AbstractMapRecord append(String name, T value) {
        if (fields == null) {
            fields = builder.build();
        }
        fields.put(name, value);
        return this;
    }

    @Override
    public Record append(KeyValue keyValue) {
        if (fields == null) {
            fields = builder.build();
        }
        fields.put(keyValue.getKey(), keyValue.getValue());
        return this;
    }

    @Override
    public AbstractMapRecord remove(String name) {
        if (fields != null) {
            fields.remove(name);
        }
        return this;
    }

    @Override
    public AbstractMapRecord clear() {
        fields = null;
        return this;
    }

    @Override
    public Iterator<KeyValue> iterator() {
        return fields == null ? new EmptyIterator<>() : new Iter(fields.entrySet().iterator());
    }

    @Override
    public <T> void setValue(String name, T value) {
        if (fields != null && fields.containsKey(name)) {
            fields.put(name, value);
        }
    }

    @Override
    @NotNull
    public abstract AbstractMapRecord clone();

    protected static class Iter extends ReadOnlyIter {

        public Iter(@NotNull Iterator<Map.Entry<String, Object>> iter) {
            super(iter);
        }
    }

}
