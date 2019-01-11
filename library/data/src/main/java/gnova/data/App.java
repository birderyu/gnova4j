package gnova.data;

import gnova.data.index.IndexException;
import gnova.data.wrapper.MapRecord;

import java.util.*;

public class App {

    public static void main(String[] args) throws IndexException {

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        UUID uuid3 = UUID.randomUUID();
        UUID uuid4 = UUID.randomUUID();
        UUID uuid5 = UUID.randomUUID();

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            UUID.randomUUID();
        }
        long t2 = System.currentTimeMillis();
        long t = t2 - t1;
    }

    static class TestRecord extends MapRecord {

        public TestRecord() {
            super(null, HashMap::new);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || !(o instanceof Record)) return false;
            Record r = (Record) o;
            return Objects.equals(getValue("id"), r.getValue("id"));
        }

        @Override
        public int hashCode() {
            return Objects.hash((Object) getValue("id"));
        }
    }

}
