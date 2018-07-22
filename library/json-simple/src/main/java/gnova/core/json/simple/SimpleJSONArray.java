package gnova.core.json.simple;

import gnova.core.annotation.NotNull;
import gnova.core.json.JsonArray;
import org.json.simple.JSONArray;

import java.util.Iterator;

public class SimpleJSONArray
        implements JsonArray<JSONArray> {

    private final JSONArray subject;

    public SimpleJSONArray() {
        this.subject = new JSONArray();
    }

    public SimpleJSONArray(@NotNull JSONArray subject) {
        this.subject = subject;
    }

    @Override
    public JSONArray getSubject() {
        return subject;
    }

    @Override
    public int size() {
        return subject.size();
    }

    @Override
    public boolean isEmpty() {
        return subject.isEmpty();
    }

    @Override
    public <T> T getAt(int pos) {
        return (T) subject.get(pos);
    }

    @Override
    public <T> boolean contains(T val) {
        return subject.contains(val);
    }

    @Override
    public <T> SimpleJSONArray add(T val) {
        subject.add(val);
        return this;
    }

    @Override
    public SimpleJSONArray removeAt(int pos) {
        subject.remove(pos);
        return this;
    }

    @Override
    public <T> SimpleJSONArray remove(T val) {
        subject.remove(val);
        return this;
    }

    @Override
    public <T> SimpleJSONArray removeAll(T val) {
        while (subject.remove(val)) { }
        return this;
    }

    @Override
    public SimpleJSONArray clone() {
        return new SimpleJSONArray((JSONArray) subject.clone());
    }

    @Override
    public Iterator iterator() {
        return subject.iterator();
    }
}
