package gnova.annotation;

import java.lang.annotation.*;

/**
 * 线程安全的
 *
 * 线程安全的类是一种接口或类的编写者，对继承该类和实现该接口的所有子类的约束，也是一种对类的使用者的保证。
 * 如果继承了一个线程安全的接口或类，则必须保证子类也是线程安全的。
 *
 * @author birderyu
 * @version 1.0.0
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface ThreadSafe {

}
