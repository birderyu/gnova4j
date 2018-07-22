package gnova.core.json.simple;

import gnova.core.json.JsonArrayBuilder;
import org.json.simple.JSONArray;

public class SimpleJSONArrayBuilder
        implements JsonArrayBuilder<JSONArray> {

    @Override
    public SimpleJSONArray build() {
        return new SimpleJSONArray();
    }
}
