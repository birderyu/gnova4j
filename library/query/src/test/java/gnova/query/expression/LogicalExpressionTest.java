package gnova.query.expression;

import gnova.query.expression.parse.ParseException;
import gnova.query.expression.parse.Parser;
import org.junit.Test;

/**
 * 逻辑表达式测试类
 */
public class LogicalExpressionTest {

    /**
     * 测试简单逻辑表达式
     */
    @Test
    public void testSimpleExpression() {

        try {
            Expression exp = Parser.parse("1 = 1");
            boolean b = exp.asLogical().isAlwaysTrue();

            int stop = 1;
            stop++;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
