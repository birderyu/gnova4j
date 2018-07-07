package gnova.data.index.key;

/**
 * 索引的键
 *
 * 索引的键并非记录的字段名，而是记录中将需要构建索引的字段值取出后建立的一个用于索引查询的值
 */
public interface Key {

    @Override
    boolean equals(Object o);
}
