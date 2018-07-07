package gnova.annotation;

import java.lang.annotation.*;

/**
 * 不再支持的方法
 *
 * 不再支持的方法不可以被调用，否则会抛出一个UnsupportedOperationException，
 * 若基类的方法不再支持，那么子类有权利也有义务不再支持此方法。
 *
 * @author birderyu
 * @version 1.0.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface Unsupported {

}
