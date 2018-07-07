package gnova.annotation;

import java.lang.annotation.*;

/**
 * 已经检查过的参数
 *
 * <p>已经检查过的参数，表示参数已经在外部检查过合法性（非空检查、合法性校验等），
 * 在方法内部不会再对数据进行检查
 *
 * @author birderyu
 * @version 1.0.0
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.SOURCE)
public @interface Checked {

}
