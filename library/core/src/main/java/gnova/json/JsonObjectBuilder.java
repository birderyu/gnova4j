package gnova.json;

import gnova.function.ObjectBuilder;

/**
 * JSON对象的构造器
 *
 * @param <JO> 实际JSON对象的类型
 * @see ObjectBuilder
 * @see JsonObject
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface JsonObjectBuilder<JO>
        extends ObjectBuilder<JsonObject<JO>> {

}
