package gnova.data.wrapper;

import gnova.annotation.NotNull;
import gnova.data.AbstractReadOnlyRecord;
import gnova.data.ReadOnlyRecord;
import gnova.util.KeyValue;
import gnova.util.ReadOnlyIterator;
import gnova.util.SimpleKeyValue;
import gnova.function.MapBuilder;
import gnova.util.EmptyIterator;

import java.util.Iterator;
import java.util.Map;

public abstract class AbstractReadOnlyMapRecord
        extends AbstractReadOnlyRecord implements ReadOnlyRecord {

    /**
     * 主键
     */
    protected final Object key;

    protected Map<String, Object> fields;

    @NotNull
    protected final MapBuilder<String, Object> builder;

    public AbstractReadOnlyMapRecord(@NotNull Object key,
                                     Map<String, Object> fields,
                                     @NotNull MapBuilder<String, Object> builder) {
        this.key = key;
        this.fields = fields;
        this.builder = builder;
    }

    @Override
    public Object getPrimaryKey() {
        return key;
    }

    @Override
    public int size() {
        return fields == null ? 0 : fields.size();
    }

    @Override
    public boolean contains(String name) {
        return fields == null ? false : fields.containsKey(name);
    }

    @Override
    public KeyValue get(String name) {
        return fields == null || !fields.containsKey(name) ?
                null : new SimpleKeyValue(name, fields.get(name));
    }

    @Override
    public Iterator<KeyValue> iterator() {
        return fields == null ? new EmptyIterator<>() : new ReadOnlyIter(fields.entrySet().iterator());
    }

    @Override
    public <T> T getValue(String name) {
        return fields == null ? null : (T) fields.get(name);
    }

    @Override
    @NotNull
    public abstract AbstractReadOnlyMapRecord clone();

    protected static class ReadOnlyIter implements ReadOnlyIterator<KeyValue> {

        protected final Iterator<Map.Entry<String, Object>> iter;

        public ReadOnlyIter(@NotNull Iterator<Map.Entry<String, Object>> iter) {
            this.iter = iter;
        }

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public KeyValue next() {
            Map.Entry<String, Object> entry = iter.next();
            return new SimpleKeyValue(entry.getKey(), entry.getValue());
        }
    }
}
