package gnova.core.orm;

import gnova.core.annotation.NotNull;
import gnova.core.function.Getter;

/**
 * ORM：Object-Relational Mapping，对象关系映射
 *
 * 对象关系映射即构建文档与对象之间的映射关系
 *
 * @author birderyu
 * @version 1.0.0
 */
public interface ORM {

    /**
     * 将一个文档转换成为对象
     *
     * @param doc 文档，不允许为null
     * @param clazz 对象的class，不允许为null
     * @param params 转换过程中需要使用的参数，根据实际情况，可以为null
     * @param <T> 对象的类型
     * @return 对象，不会返回null
     * @throws ORMException 若转换失败，则抛出此异常
     */
    @NotNull
    <T> T read(@NotNull Object doc, @NotNull Class<? extends T> clazz, Getter params)
            throws ORMException;

}
