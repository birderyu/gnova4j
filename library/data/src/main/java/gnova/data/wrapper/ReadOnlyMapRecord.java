package gnova.data.wrapper;

import gnova.annotation.NotNull;
import gnova.data.ReadOnlyRecord;
import gnova.function.MapBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ReadOnlyMapRecord
        extends AbstractReadOnlyMapRecord
        implements ReadOnlyRecord {

    public ReadOnlyMapRecord() {
        this(null, HashMap::new);
    }

    public ReadOnlyMapRecord(Map<String, Object> map) {
        this(map, HashMap::new);
    }

    public ReadOnlyMapRecord(MapBuilder<String, Object> builder) {
        this(null, builder);
    }

    public ReadOnlyMapRecord(Map<String, Object> map,
                             MapBuilder<String, Object> builder) {
        this(UUID.randomUUID(), map, builder);
    }

    @Override
    public ReadOnlyMapRecord clone() {
        return fields == null ?
                new ReadOnlyMapRecord((UUID) key, null, builder) :
                new ReadOnlyMapRecord((UUID) key, builder.build(fields), builder);
    }

    @Override
    public MapRecord toWritable() {
        return fields == null ?
                new MapRecord((UUID) key, null, builder) :
                new MapRecord((UUID) key, builder.build(fields), builder);
    }

    protected ReadOnlyMapRecord(@NotNull UUID key,
                                Map<String, Object> map,
                                @NotNull MapBuilder<String, Object> builder) {
        super(key, map, builder);
    }

}
