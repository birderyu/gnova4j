package gnova.query.expression;

import gnova.query.expression.parse.ParseException;
import gnova.query.expression.parse.ExpressionParser;
import org.junit.Test;

/**
 * 逻辑表达式测试类
 */
public class PredicateExpressionTest {

    /**
     * 测试简单逻辑表达式
     */
    //@Test
    public void testSimpleExpression() {

        try {
            Expression exp = ExpressionParser.parse("1 = 1");
            boolean b = exp.asPredicate().isAlwaysTrue();

            int stop = 1;
            stop++;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
