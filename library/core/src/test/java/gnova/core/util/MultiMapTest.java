package gnova.core.util;

import gnova.core.multimap.MultiMap;
import gnova.core.multimap.proxy.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class MultiMapTest {

    private List<String[]> items = new ArrayList<String[]>();
    private List<MultiMap<String, String>> maps = new ArrayList<MultiMap<String, String>>();

    @Before
    public void initItemAndMap() {
        items.clear();
        int row = 10;
        int column = 20;
        for (int i = 0; i < row; i++) {
            String item_1 = String.valueOf(i + 1);
            for (int j = 0; j < column; j++) {
                String item_2 = item_1 + "_" + String.valueOf(j + 1);
                items.add(new String[] {item_1, item_2});
            }
            column++;
        }

        maps.add(new HashArrayMultiMap<>());
        maps.add(new HashLinkedMultiMap<>());
        maps.add(new DoubleHashMultiMap<>());
        maps.add(new TreeArrayMultiMap<>());
        maps.add(new TreeLinkedMultiMap<>());
        maps.add(new TreeHashMultiMap<>());
        // maps.add(new ConcurrentHashArrayMultiMap<>());
        // maps.add(new ConcurrentHashLinkedMultiMap<>());
        // maps.add(new ConcurrentDoubleHashMultiMap<>());
        // maps.add(new ConcurrentTreeArrayMultiMap<>());
        // maps.add(new ConcurrentTreeLinkedMultiMap<>());
        // maps.add(new ConcurrentTreeHashMultiMap<>());
    }

    @Test
    public void multiMapTest() {
        for (MultiMap<String, String> mm : maps) {
            doMultiMapTest(mm);
        }
    }

    private void doMultiMapTest(MultiMap<String, String> mm) {

        // test for isEmpty() and put()
        Assert.assertTrue(mm.isEmpty());
        for (String[] item : items) {
            mm.put(item[0], item[1]);
        }
        Assert.assertFalse(mm.isEmpty());

        // test for size()
        Assert.assertEquals(mm.size(), items.size());

        // test for get()
        for (String[] item : items) {
            int row = Integer.valueOf(item[0]);
            int column = columnOf(row);
            Collection<String> v = mm.get(item[0]);
            Assert.assertNotEquals(v, null);
            Assert.assertEquals(v.size(), column);
        }

        // test for containsKey() and containsValue() and contains()
        for (String[] item : items) {
            Assert.assertTrue(mm.containsKey(item[0]));
            Assert.assertTrue(mm.containsValue(item[1]));
            Assert.assertTrue(mm.contains(item[0], item[1]));
        }

        // test for keySet() and values()
        Set<String> keySet = mm.keySet();
        Collection<String> values = mm.values();
        Assert.assertEquals(keySet.size(), 10);
        Assert.assertEquals(values.size(), items.size());
        for (String[] item : items) {
            Assert.assertTrue(keySet.contains(item[0]));
            Assert.assertTrue(values.contains(item[1]));
        }

        // test for anto mapIterator()
        int count = 0;
        for (MultiMap.Entry<String, String> entry : mm.entrySet()) {
            count++;
        }
        Assert.assertEquals(count, items.size());

        // test for remove()
        int size = items.size();
        Collection<String> v1 = mm.remove("1");
        size -= columnOf(1);
        Assert.assertEquals(mm.size(), size);
        Assert.assertNotEquals(v1, null);

        Collection<String> v2 = mm.remove("2");
        size -= columnOf(2);
        Assert.assertEquals(mm.size(), size);
        Assert.assertNotEquals(v1, null);

        Collection<String> v100 = mm.remove("100");
        Assert.assertEquals(mm.size(), size);
        Assert.assertEquals(v100, null);

        Assert.assertTrue(mm.remove("3", "3_1"));
        Assert.assertTrue(mm.remove("3", "3_2"));
        Assert.assertTrue(mm.remove("3", "3_3"));
        Assert.assertFalse(mm.remove("3", "3_100"));
        Assert.assertFalse(mm.remove("100", "100_1"));
        size -= 3;
        Assert.assertEquals(mm.size(), size);


        // test for anto mapIterator()
    }

    private int columnOf(int row) {
        return 20 + row - 1;
    }
}
