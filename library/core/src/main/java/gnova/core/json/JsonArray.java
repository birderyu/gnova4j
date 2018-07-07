package gnova.core.json;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;

/**
 * JSON数组
 *
 * <p>JSON数组通过代理实现，通过调用{@link JsonArray#getSubject()}方法返回被代理的对象
 *
 * @param <JA> 实际JSON数组的类型
 * @see Jsonable
 * @see java.lang.Iterable
 * @author birderyu
 * @version 1.0.0
 */
public interface JsonArray<JA>
        extends Jsonable, Iterable {

    /**
     * 获取被代理的JSON数组本身
     *
     * @return JSON数组
     */
    JA getSubject();

    /**
     * 获取JSON数组的长度
     *
     * @return 长度
     */
    int size();

    /**
     * 判断JSON数组是否为空
     *
     * @return 若为空，则返回true，否则返回false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 获取指定下标位置的元素
     *
     * @param pos 下标位置，必须大于等于0且小于JSON数组的长度，即位于[0, size)区间
     * @param <T> 元素的类型
     * @return 元素的值
     */
    <T> T getAt(@Checked int pos);

    /**
     * 判断是否包含某个元素
     *
     * @param val 元素的值
     * @param <T> 元素的类型
     * @return 若包含该元素，则返回true，否则返回false
     */
    <T> boolean contains(T val);

    /**
     * 添加到JSON数组中
     *
     * @param val 元素的值
     * @param <T> 元素的类型，支持以下几种类型：
     *           <br>空值，使用null表示；
     *           <br>布尔值，使用一个{@link Boolean 布尔类型}的值表示；
     *           <br>数字值，使用一个{@link Number 数字类型}的值表示；
     *           <br>字符串值，使用一个{@link String 字符串类型}的值表示；
     *           <br>数组值，使用一个{@link JsonArray JSON数组类型}的值表示；
     *           <br>对象值，使用一个{@link JsonObject JSON对象类型}的值表示。
     * @return 该JSON数组本身
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    <T> JsonArray<JA> add(T val) throws IllegalArgumentException;

    /**
     * 从数组中移除指定下标位置的元素
     *
     * @param pos 标位置，必须大于等于0且小于JSON数组的长度，即位于[0, {@link JsonArray#size() size})区间
     * @return 该JSON数组本身
     */
    @NotNull
    JsonArray<JA> removeAt(@Checked int pos);

    /**
     * 从数组中移除第一个符合条件的元素
     *
     * @param val 元素的值
     * @param <T> 元素的类型，支持以下几种类型：
     *           <br>空值，使用null表示；
     *           <br>布尔值，使用一个{@link Boolean 布尔类型}的值表示；
     *           <br>数字值，使用一个{@link Number 数字类型}的值表示；
     *           <br>字符串值，使用一个{@link String 字符串类型}的值表示；
     *           <br>数组值，使用一个{@link JsonArray JSON数组类型}的值表示；
     *           <br>对象值，使用一个{@link JsonObject JSON对象类型}的值表示。
     * @return 该JSON数组本身
     */
    @NotNull
    <T> JsonArray<JA> remove(T val);

    /**
     * 从数组中移除所有符合条件的元素
     *
     * @param val 元素的值
     * @param <T> 元素的类型，支持以下几种类型：
     *           <br>空值，使用null表示；
     *           <br>布尔值，使用一个{@link Boolean 布尔类型}的值表示；
     *           <br>数字值，使用一个{@link Number 数字类型}的值表示；
     *           <br>字符串值，使用一个{@link String 字符串类型}的值表示；
     *           <br>数组值，使用一个{@link JsonArray JSON数组类型}的值表示；
     *           <br>对象值，使用一个{@link JsonObject JSON对象类型}的值表示。
     * @return 该JSON数组本身
     */
    @NotNull
    <T> JsonArray<JA> removeAll(T val);

    /**
     * 克隆当前的对象
     *
     * @return 克隆之后的对象
     * @see Object#clone()
     */
    @Override
    @NotNull
    JsonArray<JA> clone();

    /**
     * 是否是JSON数组
     *
     * @return 若是JSON数组，则返回true，否则返回false
     * @see Jsonable#isArray()
     */
    @Override
    default boolean isArray() {
        return true;
    }
}
