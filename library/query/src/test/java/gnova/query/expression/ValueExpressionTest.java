package gnova.query.expression;

import gnova.query.expression.parse.ParseException;
import gnova.query.expression.parse.Parser;
import gnova.query.expression.value.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ValueExpressionTest {

    @Test
    public void testKeyExpression() {

        // 合法的键
        legalKeyExpression("key");
        legalKeyExpression("_key");
        legalKeyExpression("__key");
        legalKeyExpression("key_");
        legalKeyExpression("key__");
        legalKeyExpression("_key__");
        legalKeyExpression("__key__");
        legalKeyExpression("___key_");
        legalKeyExpression("k0y");
        legalKeyExpression("ke0");
        legalKeyExpression("k00");
        legalKeyExpression("_k00");

        // 非法的键
        illegalKeyExpression("ke y"); // 键中包含了空格
        illegalKeyExpression("1key"); // 以数字作为开头
        illegalKeyExpression(".key"); // 以特殊符号作为开头
        illegalKeyExpression("$key"); // 以特殊符号作为开头

    }

    @Test
    public void testBooleanExpression() throws ParseException {

        // 合法的布尔表达式
        legalBooleanExpression("true", Builder.buildBoolean(true));
        legalBooleanExpression("false", Builder.buildBoolean(false));

        // 非法的布尔表达式
        illegalBooleanExpression("True");
        illegalBooleanExpression("tRue");
        illegalBooleanExpression("trUe");
        illegalBooleanExpression("truE");
        illegalBooleanExpression("tRUe");

    }

    @Test
    public void testExpression() throws ParseException {

        KeyExpression key = (KeyExpression) Parser.parse("key");
        NullExpression n = (NullExpression) Parser.parse("null");
        BooleanExpression bool = (BooleanExpression) Parser.parse("true");
        NumberExpression i32 = (NumberExpression) Parser.parse("123");
        NumberExpression i64 = (NumberExpression) Parser.parse("1234567890987654321");
        NumberExpression d = (NumberExpression) Parser.parse("123.456");
        StringExpression string = (StringExpression) Parser.parse("'key'");
        ListExpression list = (ListExpression) Parser.parse(" (1, \"abc\"  ) ");


        int stop = 1;
        stop++;

    }

    private void legalKeyExpression(String keyString) {

        List<KeyExpression> keys = new ArrayList<>();
        keys.add(Builder.buildKey(keyString));
        try {
            parseValue(keys, keyString);
        } catch (ParseException e) {
            Assert.fail();
        }
        equalsTest(keys);
    }

    private void illegalKeyExpression(String keyString) {

        try {
            Expression key = Parser.parse(keyString);
            if (key.isValue() && key.asValue().isKey()) {
                Assert.fail();
            }
        } catch (ParseException pe) {
            // do nothing
        }
    }

    private void legalBooleanExpression(String booleanString, BooleanExpression be) {

        List<BooleanExpression> booleans = new ArrayList<>();
        booleans.add(be);
        try {
            parseValue(booleans, booleanString);
        } catch (ParseException e) {
            Assert.fail();
        }
        equalsTest(booleans);
    }

    private void illegalBooleanExpression(String booleanString) {

        try {
            Expression key = Parser.parse(booleanString);
            if (key.isValue() && key.asValue().isBoolean()) {
                Assert.fail();
            }
        } catch (ParseException pe) {
            // do nothing
        }
    }

    private <T> void parseValue(List<T> ves, String string) throws ParseException {
        ves.add((T) Parser.parse(string));
        ves.add((T) Parser.parse(" " + string));
        ves.add((T) Parser.parse("  " + string));
        ves.add((T) Parser.parse(string + " "));
        ves.add((T) Parser.parse(string + "  "));
        ves.add((T) Parser.parse(" " + string + " "));
        ves.add((T) Parser.parse("  " + string + "  "));
        ves.add((T) Parser.parse("   " + string + "    "));
    }

    private <T> void equalsTest(List<T> ves) {
        for (T ve : ves) {
            for (T _ve : ves) {
                Assert.assertEquals(ve, _ve);
            }
        }
    }
}
