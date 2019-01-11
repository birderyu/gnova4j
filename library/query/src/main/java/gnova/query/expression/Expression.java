package gnova.query.expression;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.query.expression.util.ExpressionFactory;
import gnova.query.expression.util.ExpressionInstancer;
import gnova.query.expression.parse.ExpressionParser;
import gnova.query.expression.parse.ParseException;
import gnova.query.expression.util.ExpressionSimplifier;

/**
 * 表达式
 *
 * <p>表达式是使用变量、值和符号组合而成的，包含具体含义的语句，如下面五个表达式：
 * <pre>{@code
 *      'birderyu'
 *      18
 *      name = 'birderyu'
 *      age > 18
 *      name = 'birderyu' and age > 18
 * }</pre>
 *
 * <p>表达式分为两种，一种是{@link ValueExpression 值表达式}，另一种是{@link PredicateExpression 谓词表达式}。

 * <p>表达式的构建：
 * <br>使用{@link ExpressionFactory 表达式工厂类}构建不同类型的表达式，不推荐使用表达式的构造方法，因为在工厂类的构建方法中，
 * 会对参数进行合法性校验，若参数不合法，将会抛出{@link IllegalArgumentException 参数异常}，如：
 * <pre>{@code
 *      try {
 *          // build a simple predicate expression
 *          ValueExpression left = ExpressionFactory.buildKey("name");
 *          ValueExpression right = ExpressionFactory.buildString("birderyu");
 *          ValuePredicate predicate = ValuePredicate.EQ;
 *          PredicateExpression predicateExpression = ExpressionFactory.buildSimple(left, predicate, right);
 *      } catch (IllegalArgumentException e) {
 *          // build error
 *      }
 * }</pre>
 *
 * <p>表达式的解析：
 * <br>表达式接近自然语言，因此更常见的场景是通过一个文本字符串直接解析成为表达式，如：
 * <pre>{@code
 *      String s1 = "'birderyu'";
 *      String s2 = "18";
 *      String s3 = "name = 'birderyu'";
 *      String s4 = "age > 18";
 *      String s5 = "name = 'birderyu' and age > 18";
 * }</pre>
 * 这五个字符串都可以解析成为不同类型的表达式对象，使用{@link ExpressionParser 表达式解析类}将{@link String 字符串对象}解析成为一个表达式，
 * 若解析失败，将会抛出{@link ParseException 解析失败异常}，如：
 * <pre>{@code
 *      try {
 *          // parse a simple predicate expression
 *          String s = "name = 'birderyu'";
 *          Expression expression = ExpressionParser.parse(s);
 *      } catch (ParseException e) {
 *          // parse error
 *      }
 * }</pre>
 *
 * <p>表达式的实例化：
 * <br>表达式中允许包含若干数量的占位符，如：
 * <pre>{@code
 *      name = ? and age > ?
 * }</pre>
 * 将一个表达式中的占位符用实际的值去代替，这一过程被称作表达式的实例化。使用{@link ExpressionInstancer 表达式实例化类}将一个包含占位符的表达式实例化，
 * 若实例化失败，将会抛出{@link IllegalArgumentException 参数异常}，如：
 * <pre>{@code
 *      try {
 *          // instantiate a multiple predicate expression
 *          String s = "name = ? and age > ?";
 *          Expression expression = ExpressionParser.parse(s);
 *          expression = ExpressionInstancer.instantiate(expression, new Object[] {"birderyu", 18});
 *      } catch (ParseException e) {
 *          // parse error
 *      } catch (IllegalArgumentException e) {
 *          // instantiate error
 *      }
 * }</pre>
 *
 * <p>表达式的二进制化：
 * 1 ~ 60：值类型
 * 61 ~ 80：值谓词
 * 81 ~ 100：逻辑谓词
 * 101 ~ 150：值表达式
 * 151 ~ 200：逻辑表达式
 *
 * @see ValueExpression
 * @see PredicateExpression
 * @see ExpressionFactory
 * @see ExpressionParser
 * @see ExpressionInstancer
 * @see ExpressionSimplifier
 * @author birderyu
 * @version 1.0.0
 */
@Immutable
public interface Expression {

    /**
     * 获取表达式中占位符的数量
     *
     * @return 占位符的数量，若不包含占位符，则返回0
     */
    int placeholderSize();

    /**
     * 是否是{@link ValueExpression 值表达式}
     *
     * <p>若表达式不是值表达式{@link ValueExpression 值表达式}，那么它一定是{@link PredicateExpression 谓词表达式}。
     *
     * @return 若表达式是值表达式，则返回true，否则返回false
     * @see ValueExpression
     * @see PredicateExpression
     */
    boolean isValue();

    /**
     * 转换为值表达式
     *
     * <p>若表达式是值表达式，则返回当前表达式，否则返回null
     *
     * @return 值表达式
     * @see ValueExpression
     */
    default ValueExpression asValue() {
        return isValue() ? (ValueExpression) this : null;
    }

    /**
     * 转换为谓词表达式
     *
     * <p>若表达式是谓词表达式，则返回当前表达式，否则返回null
     *
     * @return 谓词表达式
     * @see PredicateExpression
     */
    default PredicateExpression asPredicate() {
        return isValue() ? null : (PredicateExpression) this;
    }

    /**
     * 将表达式转换成字符串
     *
     * <p>表达式转换成为的字符串，可以通过{@link ExpressionParser 表达式解析类}解析成为一个与原表达式含义相同的表达式对象
     *
     * @return 字符串对象，不会返回null
     */
    @Override
    @NotNull
    String toString();

    @Override
    int hashCode();

    @Override
    boolean equals(Object obj);

}
