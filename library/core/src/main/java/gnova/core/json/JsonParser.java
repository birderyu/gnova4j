package gnova.core.json;

import gnova.core.annotation.NotNull;

import java.io.Reader;

/**
 * JSON解析器
 *
 * JSON解析器用于解析一个JSON字符串成为一个对象或JSON对象
 *
 * @author birderyu
 * @version 1.0.0
 */
public interface JsonParser {

    /**
     * 将一个JSON字符串解析成为Json实体
     *
     * @param json JSON字符串，不允许为null
     * @return JSON实体，可能为以下类型：
     *          <br>空值，使用null表示；
     *          <br>布尔值，使用一个{@link Boolean 布尔类型}的值表示；
     *          <br>数字值，使用一个{@link Number 数字类型}的值表示；
     *          <br>字符串值，使用一个{@link String 字符串类型}的值表示；
     *          <br>数组值，使用一个{@link JsonArray JSON数组类型}的值表示；
     *          <br>对象值，使用一个{@link JsonObject JSON对象类型}的值表示。
     * @throws JsonParseException 若JSON解析失败，则抛出该异常
     */
    Object parse(@NotNull String json) throws JsonParseException;

    /**
     * 将一个JSON字符串解析成为Json实体
     *
     * @param json JSON字符流，不允许为null
     * @return JSON实体
     * @throws JsonParseException 若JSON解析失败，则抛出该异常
     */
    Object parse(@NotNull Reader json) throws JsonParseException;

    /**
     * 将一个JSON字符串解析成为对象
     *
     * @param json JSON字符串，不允许为null
     * @param clazz 实体对象的类，不允许为null
     * @param <T> 实体对象的类型
     * @return 实体对象，不会返回null
     * @throws JsonParseException 若JSON解析失败，则抛出该异常
     */
    @NotNull
    <T> T parse(@NotNull String json, @NotNull Class<T> clazz) throws JsonParseException;

}
