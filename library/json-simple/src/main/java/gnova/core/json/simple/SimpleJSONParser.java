package gnova.core.json.simple;

import gnova.core.json.JsonParseException;
import gnova.core.json.JsonParser;
import gnova.core.json.Jsonable;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.Reader;

public class SimpleJSONParser
        implements JsonParser {

    private final JSONParser subject;

    public SimpleJSONParser() {
        this.subject = new JSONParser();
    }

    public SimpleJSONParser(JSONParser subject) {
        this.subject = subject;
    }

    @Override
    public Object parse(String json) throws JsonParseException {
        try {
            return toJsonable(subject.parse(json));
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public Object parse(Reader json) throws JsonParseException {
        try {
            return toJsonable(subject.parse(json));
        } catch (ParseException e) {
            throw new JsonParseException(e);
        } catch (IOException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public <T> T parse(String json, Class<T> clazz) throws JsonParseException {
        throw new UnsupportedOperationException("parse to object.");
    }

    private Object toJsonable(Object obj) {
        if (obj instanceof org.json.simple.JSONObject) {
            return new SimpleJSONObject((org.json.simple.JSONObject) obj);
        } else if (obj instanceof org.json.simple.JSONArray) {
            return new SimpleJSONArray((org.json.simple.JSONArray) obj);
        } else {
            return obj;
        }
    }

}
