package gnova.core.annotation;

import java.lang.annotation.*;

/**
 * 不可变类的注解
 *
 * 一个不可变的类，一定是线程安全的
 * 如果接口上打上了该注解，那么子类也应该是不可变的
 *
 * @author birderyu
 * @version 1.0.0
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface Immutable {

}
