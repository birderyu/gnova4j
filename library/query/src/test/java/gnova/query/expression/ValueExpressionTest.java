package gnova.query.expression;

import gnova.geometry.model.*;
import gnova.query.expression.parse.ParseException;
import gnova.query.expression.parse.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 值表达式测试类
 */
public class ValueExpressionTest {

    private final NullExpression nullExp = Builder.buildNull();
    private final BooleanExpression trueExp = Builder.buildTrue();
    private final BooleanExpression falseExp = Builder.buildFalse();
    private final GeometryExpression emptyGeoExp = Builder.buildEmptyGeometry();

    /**
     * 测试键值表达式
     */
    @Test
    public void testKeyExpression() {

        // 合法的键值表达式
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

        // 非法的键值表达式
        illegalKeyExpression("ke y"); // 键中包含了空格
        illegalKeyExpression("1key"); // 以数字作为开头
        illegalKeyExpression(".key"); // 以特殊符号作为开头
        illegalKeyExpression("$key"); // 以特殊符号作为开头

    }

    /**
     * 测试空值表达式
     */
    @Test
    public void testNullExpression() {

        // 合法的空值表达式
        legalValueExpression("null", nullExp);

        // 非法的空值表达式
        illegalNullExpression("Null");
        illegalNullExpression("nUll");
        illegalNullExpression("nuLl");
        illegalNullExpression("nulL");
        illegalNullExpression("NUll");
        illegalNullExpression("nULl");
        illegalNullExpression("nuLL");
        illegalNullExpression("NuLl");
        illegalNullExpression("NulL");
        illegalNullExpression("nUlL");
        illegalNullExpression("NULl");
        illegalNullExpression("NUlL");
        illegalNullExpression("NuLL");
        illegalNullExpression("nULL");
        illegalNullExpression("NULL");
    }

    /**
     * 测试布尔值表达式
     */
    @Test
    public void testBooleanExpression() {

        // 合法的布尔值表达式
        legalValueExpression("true", trueExp);
        legalValueExpression("false", falseExp);

        // 非法的布尔值表达式
        illegalBooleanExpression("True");
        illegalBooleanExpression("tRue");
        illegalBooleanExpression("trUe");
        illegalBooleanExpression("truE");
        illegalBooleanExpression("TRue");
        illegalBooleanExpression("TrUe");
        illegalBooleanExpression("TruE");
        illegalBooleanExpression("tRUe");
        illegalBooleanExpression("tRuE");
        illegalBooleanExpression("trUE");
        illegalBooleanExpression("TRUe");
        illegalBooleanExpression("TRuE");
        illegalBooleanExpression("TrUE");
        illegalBooleanExpression("tRUE");
        illegalBooleanExpression("TRUE");
        illegalBooleanExpression("False");
        illegalBooleanExpression("fAlse");
        illegalBooleanExpression("faLse");
        illegalBooleanExpression("falSe");
        illegalBooleanExpression("falsE");
        illegalBooleanExpression("FAlse");
        illegalBooleanExpression("FaLse");
        illegalBooleanExpression("FalSe");
        illegalBooleanExpression("FalsE");
        illegalBooleanExpression("fALse");
        illegalBooleanExpression("fAlSe");
        illegalBooleanExpression("fAlsE");
        illegalBooleanExpression("faLSe");
        illegalBooleanExpression("faLsE");
        illegalBooleanExpression("falSE");
        illegalBooleanExpression("FALse");
        illegalBooleanExpression("FAlSe");
        illegalBooleanExpression("FAlsE");
        illegalBooleanExpression("FaLSe");
        illegalBooleanExpression("FaLsE");
        illegalBooleanExpression("FalSE");
        illegalBooleanExpression("fALSe");
        illegalBooleanExpression("fALsE");
        illegalBooleanExpression("fAlSE");
        illegalBooleanExpression("faLSE");
        illegalBooleanExpression("FALSe");
        illegalBooleanExpression("FALsE");
        illegalBooleanExpression("FAlSE");
        illegalBooleanExpression("FaLSE");
        illegalBooleanExpression("fALSE");
        illegalBooleanExpression("FALSE");

    }

    /**
     * 测试数字值表达式
     */
    @Test
    public void testNumberExpression() {

        // 合法的32位整型值表达式
        legalValueExpression("123", Builder.buildInt32(123));
        legalValueExpression("2018", Builder.buildInt32(2018));
        legalValueExpression("-321", Builder.buildInt32(-321));
        legalValueExpression("0", Builder.buildInt32(0));
        legalValueExpression("-0", Builder.buildInt32(0));
        legalValueExpression(String.valueOf(Integer.MIN_VALUE), Builder.buildInt32(Integer.MIN_VALUE));
        legalValueExpression(String.valueOf(Integer.MAX_VALUE), Builder.buildInt32(Integer.MAX_VALUE));

        // 合法的64位整型值表达式
        legalValueExpression("123", Builder.buildInt64(123));
        legalValueExpression("2018", Builder.buildInt64(2018));
        legalValueExpression("-321", Builder.buildInt64(-321));
        legalValueExpression("0", Builder.buildInt64(0));
        legalValueExpression("-0", Builder.buildInt64(0));
        legalValueExpression(String.valueOf(Integer.MIN_VALUE), Builder.buildInt64(Integer.MIN_VALUE));
        legalValueExpression(String.valueOf(Integer.MAX_VALUE), Builder.buildInt64(Integer.MAX_VALUE));
        legalValueExpression(String.valueOf("123456789876"), Builder.buildInt64(123456789876L));
        legalValueExpression(String.valueOf("-123456789876"), Builder.buildInt64(-123456789876L));
        legalValueExpression(String.valueOf(Long.MIN_VALUE), Builder.buildInt64(Long.MIN_VALUE));
        legalValueExpression(String.valueOf(Long.MAX_VALUE), Builder.buildInt64(Long.MAX_VALUE));

        // 合法的双精度浮点型值表达式
        legalValueExpression("123", Builder.buildDouble(123));
        legalValueExpression("2018", Builder.buildDouble(2018));
        legalValueExpression("-321", Builder.buildDouble(-321));
        legalValueExpression("0", Builder.buildDouble(0));
        legalValueExpression("-0", Builder.buildDouble(0));
        legalValueExpression("0.0", Builder.buildDouble(0));
        legalValueExpression(String.valueOf("1.23456789"), Builder.buildDouble(1.23456789));
        legalValueExpression(String.valueOf("-1.23456789"), Builder.buildDouble(-1.23456789));
        legalValueExpression(String.valueOf("98765432.1"), Builder.buildDouble(98765432.1));
        legalValueExpression(String.valueOf("-98765432.1"), Builder.buildDouble(-98765432.1));
        legalValueExpression(String.valueOf(String.valueOf(Math.E)), Builder.buildDouble(Math.E));
        legalValueExpression(String.valueOf(String.valueOf(Math.PI)), Builder.buildDouble(Math.PI));
        legalValueExpression(String.valueOf(Double.MIN_VALUE), Builder.buildDouble(Double.MIN_VALUE));
        legalValueExpression(String.valueOf(Double.MAX_VALUE), Builder.buildDouble(Double.MAX_VALUE));

    }

    /**
     * 测试字符串值表达式
     */
    @Test
    public void testStringExpression() {

        // 合法的字符串值表达式
        String[] values = { "key",
                "null",
                "true", "false",
                "0", "123456", "1234567890987654", "123.321", "0.0", String.valueOf(Math.PI),
                "This is Tom!", "How is going?",
                "one\ntwo\nthree\n" };
        for (String value : values) {
            // 字符串表达式需要使用引号括起来
            legalStringExpression("\"" + value + "\"");
            legalStringExpression("\'" + value + "\'");
            legalStringExpression("'" + value + "'");
        }

        // 同样类型的引号不能够嵌套
        values = new String[]{"Hello, I'm Jim", "Don't do that!", "Hi, What's up man?"};
        for (String value : values) {
            legalStringExpression("\"" + value + "\"");
        }
        values = new String[]{"You say you are \"The One\"?"};
        for (String value : values) {
            legalStringExpression("\'" + value + "\'");
            legalStringExpression("'" + value + "'");
        }

        // 同样类型的引号如果需要嵌套，必须使用转义字符
        values = new String[]{ "Hello, I\\'m Jim", "Don\\'t do that!", "Hi, What\\'s up man?" };
        for (String value : values) {
            legalStringExpression("\'" + value + "\'");
            legalStringExpression("'" + value + "'");
        }
    }

    /**
     * 测试字符串值表达式
     */
    @Test
    public void testGeometryExpression() {

        // 空的几何对象
        legalValueExpression("[]", emptyGeoExp);

        GeometryFactory gf = FactoryFinder.getDefaultGeometryFactory();
        String sRing = "(-4 4, -7 0, -4 -4, 0 -4, 3 0, 0 4, -4 4)";
        LinearRing ring = gf.createLinearRing(new Coordinate[] {
                new Coordinate(-4, 4),
                new Coordinate(-7, 0),
                new Coordinate(-4, -4),
                new Coordinate(0, -4),
                new Coordinate(3, 0),
                new Coordinate(0, 4),
                new Coordinate(-4, 4) });
        String sHole1 = "(0 0, 0 -2, -2 -2, -2 0, 0 0)";
        LinearRing hole1 = gf.createLinearRing(new Coordinate[] {
                new Coordinate(0, 0),
                new Coordinate(0, -2),
                new Coordinate(-2, -2),
                new Coordinate(-2, 0),
                new Coordinate(0, 0) });
        String sHole2 = "(-5 1, -1 1, -2 3, -3 3, -5 1)";
        LinearRing hole2 = gf.createLinearRing(new Coordinate[] {
                new Coordinate(-5, 1),
                new Coordinate(-1, 1),
                new Coordinate(-2, 3),
                new Coordinate(-3, 3),
                new Coordinate(-5, 1) });

        // 具有一个环的多边形对象
        Polygon g1 = gf.createPolygon(ring);
        legalValueExpression("[" + sRing + "]", Builder.buildGeometry(g1));

        // 具有一个环和一个洞的多边形对象
        Polygon g2 = gf.createPolygon(ring, new LinearRing[] { hole1 });
        legalValueExpression("[" + sRing + ", " + sHole1 + "]", Builder.buildGeometry(g2));

        // 具有一个环和两个洞的多边形对象
        Polygon g3 = gf.createPolygon(ring, new LinearRing[] { hole1, hole2 });
        legalValueExpression("[" + sRing + ", " + sHole1 + ", " + sHole2 + "]", Builder.buildGeometry(g3));

        // 多多边形
        MultiPolygon g4 = gf.createMultiPolygon(new Polygon[] {
                gf.createPolygon(hole1),
                gf.createPolygon(hole2)});
        legalValueExpression("[[" + sHole1 + "], [" + sHole2 + "]]", Builder.buildGeometry(g4));
    }

    /**
     * 合法的值表达式
     *
     * @param string
     * @param ve
     */
    private void legalValueExpression(String string, ValueExpression ve) {

        Collection<ValueExpression> values = new ArrayList<>();
        values.add(ve);
        try {
            parseValue(values, string);
        } catch (ParseException e) {
            Assert.fail();
        }
        equalsTest(values);
    }

    /**
     * 合法的键值表达式
     *
     * @param keyString
     */
    private void legalKeyExpression(String keyString) {
        legalValueExpression(keyString, Builder.buildKey(keyString));
    }

    /**
     * 合法的字符串值表达式
     *
     * @param stringString
     */
    private void legalStringExpression(String stringString) {
        legalValueExpression(stringString,
                Builder.buildString(stringString.substring(1, stringString.length() - 1)));
    }


    /**
     * 不合法的键值表达式
     *
     * @param keyString
     */
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

    /**
     * 不合法的空值表达式
     *
     * @param nullString
     */
    private void illegalNullExpression(String nullString) {

        try {
            Expression key = Parser.parse(nullString);
            if (key.isValue() && key.asValue().isNull()) {
                Assert.fail();
            }
        } catch (ParseException pe) {
            // do nothing
        }
    }

    /**
     * 不合法的空值表达式
     *
     * @param booleanString
     */
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

    /**
     * 根据字符串解析表达式，并将其放入列表中
     *
     * @param ves
     * @param string
     * @param <T>
     * @throws ParseException
     */
    private <T extends ValueExpression> void parseValue(Collection<T> ves, String string) throws ParseException {
        ves.add((T) Parser.parse(string));
        ves.add((T) Parser.parse(" " + string));
        ves.add((T) Parser.parse("  " + string));
        ves.add((T) Parser.parse(string + " "));
        ves.add((T) Parser.parse(string + "  "));
        ves.add((T) Parser.parse(" " + string + " "));
        ves.add((T) Parser.parse("  " + string + "  "));
        ves.add((T) Parser.parse("   " + string + "    "));
    }

    /**
     * 测试列表中的所有元素是否两两相等
     *
     * @param ves
     * @param <T>
     */
    private <T extends ValueExpression> void equalsTest(Iterable<T> ves) {
        for (T ve : ves) {
            for (T _ve : ves) {
                Assert.assertEquals(ve, _ve);
            }
        }
    }
}
