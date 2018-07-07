package gnova.data.wrapper;

import gnova.annotation.NotNull;
import gnova.data.Record;
import gnova.function.MapBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 使用Map实现的记录对象
 */
public class MapRecord
        extends AbstractMapRecord implements Record {

    public MapRecord() {
        this(null, HashMap::new);
    }

    public MapRecord(Map<String, Object> map) {
        this(map, HashMap::new);
    }

    public MapRecord(MapBuilder<String, Object> builder) {
        this(null, builder);
    }

    public MapRecord(Map<String, Object> map,
                     MapBuilder<String, Object> builder) {
        this(UUID.randomUUID(), map, builder);
    }

    @Override
    public MapRecord clone() {
        return clone0();
    }

    protected MapRecord(@NotNull UUID key,
                        Map<String, Object> map,
                        @NotNull MapBuilder<String, Object> builder) {
        super(key, map, builder);
    }

    protected MapRecord clone0() {
        return fields == null ?
                new MapRecord((UUID) key, null, builder) :
                new MapRecord((UUID) key, builder.build(fields), builder);
    }

}
