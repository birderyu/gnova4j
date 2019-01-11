package gnova.query;

import gnova.core.ArrayUtil;
import gnova.query.expression.Expression;
import gnova.query.expression.ValuePredicate;
import gnova.query.expression.util.ExpressionFactory;
import gnova.query.expression.util.ExpressionSimplifier;
import gnova.query.expression.parse.ParseException;
import gnova.query.expression.parse.ExpressionParser;

public class App {

    public static void main(String[] args) {

        java.util.Collection<Byte> bytes = new java.util.ArrayList<>();
        for (int i = 0; i < 256; i++) {
            bytes.add((byte) i);
        }
        Expression eee = ExpressionFactory.buildSimple(
                ExpressionFactory.buildKey("a"),
                ValuePredicate.EQ,
                ExpressionFactory.buildBytes(ArrayUtil.unboxing(bytes.toArray(new Byte[bytes.size()])))
        );
        int sasdasd = 0;



        // String str = "a = (1, 2, 3) and ((b like 'sasd' or c > 12) xor d in (1, 2, '3'))";
        // String str = "a intersect {[(30 10, 40 40, 20 40, 10 20, 30 10), (20 30, 35 35, 30 20, 20 30)]}";
        // String str = "a intersect {[(30 10, 40 40, 20 40, 10 20, 30 10), (20 30, 35 35, 30 20, 20 30)], [(15 5, 40 10, 10 20, 5 10, 15 5)]}";
        // String str = "g intersect {[(30 10, 40 40, 20 40, 10 20, 30 10), (20 30, 35 35, 30 20, 20 30)], [(15 5, 40 10, 10 20, 5 10, 15 5)]} and a = (1, 2, 3) and ((b like 'sasd' or c > 12) xor d in (1, 2, '3'))";
        // String str = "!!a = (1, 2, 3) and (b like 'sasd' or !c > 12)";
        // String str = "[FF FF FF FF FF FF FF FE]";
        // String str = "@(\"1991-2-28 20:40:00\", 'yyyy-MM-dd HH:mm:ss')";
        String str = "#" + System.currentTimeMillis();
        Expression exp = null;
        try {
            exp = ExpressionParser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Expression exp2 = ExpressionSimplifier.simplify(exp.asPredicate());
        int stop = 1;
        stop++;
    }

}
