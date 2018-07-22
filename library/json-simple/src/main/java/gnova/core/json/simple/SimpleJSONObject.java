package gnova.core.json.simple;

import gnova.core.annotation.NotNull;
import gnova.core.json.JsonKeyValue;
import gnova.core.json.JsonObject;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

public class SimpleJSONObject
        implements JsonObject<JSONObject> {

    private final JSONObject subject;

    public SimpleJSONObject() {
        this.subject = new JSONObject();
    }

    public SimpleJSONObject(@NotNull JSONObject subject) {
        this.subject = subject;
    }

    @Override
    public JSONObject getSubject() {
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
    public boolean contains(String key) {
        return subject.containsKey(key);
    }

    @Override
    public <T> T get(String key) {
        return (T) subject.get(key);
    }

    @Override
    public <T> SimpleJSONObject append(String key, T value) {
        subject.put(key, value);
        return this;
    }

    @Override
    public SimpleJSONObject remove(String key) {
        subject.remove(key);
        return this;
    }

    @Override
    public SimpleJSONObject clear() {
        subject.clear();
        return this;
    }

    @Override
    public SimpleJSONObject clone() {
        return new SimpleJSONObject((JSONObject) subject.clone());
    }

    @Override
    public Iterator<JsonKeyValue> iterator() {
        return new Iter(subject.entrySet().iterator());
    }

    private static class JKV implements JsonKeyValue {

        private final Map.Entry entry;

        private JKV(Map.Entry entry) {
            this.entry = entry;
        }

        @Override
        public String getKey() {
            return (String) entry.getKey();
        }

        @Override
        public <T> T getValue() {
            return (T) entry.getValue();
        }
    }

    private static class Iter implements Iterator<JsonKeyValue> {

        private final Iterator iterator;

        public Iter(Iterator iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public JsonKeyValue next() {
            return new JKV((Map.Entry) iterator.next());
        }

        @Override
        public void remove() {
            iterator.remove();
        }

        @Override
        public void forEachRemaining(Consumer<? super JsonKeyValue> action) {
            iterator.forEachRemaining(action);
        }
    }
}
