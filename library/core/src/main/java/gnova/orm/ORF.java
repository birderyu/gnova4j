package gnova.orm;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Object-Relational Field：对象映射字段
 *
 * 对象映射字段用于注解类中的字段，以和文档中同名的属性相匹配
 *
 * @author birderyu
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ORF {

    /**
     * 字段的名称
     *
     * @return 字段的名称
     */
    String name();

    /**
     * 字段的类型
     *
     * @return 字段的名称
     */
    ORT type() default ORT.Normal;

    /**
     * 容器中数据元素的类型
     *
     * 仅当type不为{@link ORT#Normal Normal}时有效
     * 如List（元素为String）类型，其component为String.class
     * int[]类型，其component为int.class
     *
     * @return 数据元素的类型
     */
    Class<?> component() default Object.class;
}
