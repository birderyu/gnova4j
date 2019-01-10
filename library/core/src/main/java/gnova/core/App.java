package gnova.core;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class App {

    public static void main(String[] args) {


        LocalDate today = LocalDate.now();
        
        LocalDate yr = LocalDate.of(2013, 11, 25);
        long interval = ChronoUnit.YEARS.between(yr, today);

        java.util.function.UnaryOperator uo;
        java.util.stream.Stream<String> s;

        java.util.Map m;

        float f1 = Float.NaN;
        float f2 = Float.NaN;
        float f3 = f1 - f2;
        boolean b01 = f3 < 1f;
        boolean b02 = f3 <= 1f;
        boolean b03 = f3 > 1f;
        boolean b04 = f3 >= 1f;
        boolean b05 = f3 == 1f;
        boolean b06 = f3 != 1f;
        boolean b07 = 1f < f3;
        boolean b08 = 1f <= f3;
        boolean b09 = 1f > f3;
        boolean b10 = 1f >= f3;
        boolean b11 = 1f == f3;
        boolean b12 = 1f != f3;
        boolean b13 = f1 < f2;
        boolean b14 = f1 <= f2;
        boolean b15 = f1 > f2;
        boolean b16 = f1 >= f2;
        boolean b17 = f1 == f2;
        boolean b18 = f1 != f2;

        int aaa = 129;
        byte bbb = (byte) aaa;
        int ccc = bbb;

        int stop = 1;
        stop++;

        //
    }

}
