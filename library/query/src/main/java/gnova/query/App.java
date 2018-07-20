package gnova.query;

import gnova.query.expression.Expression;
import gnova.query.expression.Simplifier;
import gnova.query.expression.parse.ParseException;
import gnova.query.expression.parse.Parser;

public class App {

    public static void main(String[] args) {
        // String str = "a = (1, 2, 3) and ((b like 'sasd' or c > 12) xor d in (1, 2, '3'))";
        // String str = "a intersect [(30 10, 40 40, 20 40, 10 20, 30 10), (20 30, 35 35, 30 20, 20 30)]";
        // String str = "g intersect [[(30 10, 40 40, 20 40, 10 20, 30 10), (20 30, 35 35, 30 20, 20 30)], [(15 5, 40 10, 10 20, 5 10, 15 5)]] and a = (1, 2, 3) and ((b like 'sasd' or c > 12) xor d in (1, 2, '3'))";
        String str = "!!a = (1, 2, 3) and (b like 'sasd' or !c > 12)";
        Expression exp = null;
        try {
            exp = Parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Expression exp2 = Simplifier.simplify(exp);
        int stop = 1;
        stop++;
    }

}
