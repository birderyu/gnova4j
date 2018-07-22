package gnova.core.json.simple;

import gnova.core.json.JsonObjectBuilder;
import org.json.simple.JSONObject;

public class SimpleJSONObjectBuilder
        implements JsonObjectBuilder<JSONObject> {

    @Override
    public SimpleJSONObject build() {
        return new SimpleJSONObject();
    }
}
