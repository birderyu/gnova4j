package gnova.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这个标注作用于容器之上
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configures {

    /**
     * 配置项的名称
     *
     * @return
     */
    String value();

    /**
     * 容器的类型
     *
     * @return
     */
    Container container();

    /**
     * 容器中元素的类型
     *
     * @return
     */
    Class<?> clazz();

}
