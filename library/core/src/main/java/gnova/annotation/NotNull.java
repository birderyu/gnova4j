package gnova.annotation;

import java.lang.annotation.*;

/**
 * 不允许为null的
 *
 * <p>若该注解位于方法上，说明该方法的返回值不允许为null，这是方法的编写者对方法的使用者的承诺；
 * <br>若该注解位于字段上，说明该字段不允许为null，这是对类维护者的要求；
 * <br>若该注解位于方法的参数上，说明该参数不接受一个null，这是对方法使用者的要求；
 * <br>此外，该注解是可以被继承的，这意味者子类或子接口的方法、字段和参数也需要满足上述要求。
 * <br>一旦违反了上述要求，那么可能会产生意外的后果。
 *
 * @author birderyu
 * @version 1.0.0
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface NotNull {

}
