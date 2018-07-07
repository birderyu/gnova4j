package gnova.json;

import gnova.function.ObjectBuilder;

/**
 * JSON数组的构造器
 *
 * @param <JA> 实际JSON数组的类型
 * @see ObjectBuilder
 * @see JsonArray
 * @author birderyu
 * @version 1.0.0
 */
@FunctionalInterface
public interface JsonArrayBuilder<JA>
        extends ObjectBuilder<JsonArray<JA>> {

}

