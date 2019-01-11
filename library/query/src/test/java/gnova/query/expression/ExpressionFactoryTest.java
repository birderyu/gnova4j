package gnova.query.expression;

import gnova.query.expression.util.ExpressionFactory;
import org.junit.Assert;
import org.junit.Test;

public class ExpressionFactoryTest {

    @Test
    public void testBuildNull() {

        NullExpression nullExpression = null;
        try {
            nullExpression = ExpressionFactory.buildNull();
        } catch (Exception e) {
            Assert.fail("空值表达式构造失败");
        }
        Assert.assertNotNull("空值表达式构造失败", nullExpression);

        // 测试这个空值表达式是否合法
        testNullExpression(nullExpression);
    }

    private void testNullExpression(NullExpression nullExpression) {
        Assert.assertNull("空值表达式的值不为空", nullExpression.getValue());
        Assert.assertTrue("空值表达式的类型不为空值类型", nullExpression.isNull());
        Assert.assertEquals("空值表达式的类型不为空值类型", ValueType.Null,
                nullExpression.getValueType());
    }

}
