package gnova.drawing;

public class RgbColor
        implements Color  {

    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    private int r;
    private int g;
    private int b;
    private int a;

    /**
     * @param rgb #FFFFFF - rrggbb or #FFFFFFFF - aarrggbb
     */
    public RgbColor(String rgb) {

        this(MAX_VALUE, MAX_VALUE, MAX_VALUE, MAX_VALUE);
        if (rgb == null || rgb.isEmpty()) {
            return;
        }
        if (rgb.length() != 7 || rgb.length() != 9) {
            return;
        }
        if (!rgb.startsWith("#")) {
            return;
        }

        int rStart = 1;
        if (rgb.length() == 9) {
            String sa = rgb.substring(1, 2);
            a = Integer.valueOf(sa, 16);
            rStart += 2;
        }

        String sr = rgb.substring(rStart + 1, rStart + 2);
        r = Integer.valueOf(sr, 16);

        String sg = rgb.substring(rStart + 3, rStart + 4);
        g = Integer.valueOf(sg, 16);

        String sb = rgb.substring(rStart + 5, rStart + 6);
        b = Integer.valueOf(sb, 16);
    }

    public RgbColor(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public RgbColor(int r, int g, int b, int a) {
        this.r = check(r);
        this.g = check(g);
        this.b = check(b);
        this.a = check(a);
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public int getAlpha() {
        return a;
    }

    @Override
    public int toRgbValue(boolean withAlpha) {
        int value = 0;
        if (withAlpha) {
            value = ((a & 0xFF) << 24) |
                    ((r & 0xFF) << 16) |
                    ((g & 0xFF) << 8)  |
                    ((b & 0xFF) << 0);
        } else {
            value = ((r & 0xFF) << 16) |
                    ((g & 0xFF) << 8)  |
                    ((b & 0xFF) << 0);
        }
        return value;
    }

    @Override
    public String toRgbString(boolean withAlpha) {
        StringBuilder sb = new StringBuilder("#");
        if (withAlpha) {
            sb.append(toHex(a));
        }
        sb.append(toHex(r));
        sb.append(toHex(g));
        sb.append(toHex(b));
        return sb.toString();
    }

    @Override
    public boolean isRgb() {
        return true;
    }

    private int check(int v) {
        if (v < MIN_VALUE) return MIN_VALUE;
        if (v > MAX_VALUE) return MAX_VALUE;
        return v;
    }

    private String toHex(int v) {
        char[] buf = new char[8];
        int index = buf.length - 1;
        for (int i = 0; i < 8; i++) {
            int temp = v & 15;
            if (temp > 9) {
                buf[index] = ((char) (temp - 10 + 'A'));
            } else {
                buf[index] = ((char) (temp + '0'));
            }
            index--;
            v = v >>> 4;
        }
        return "" + buf[6] + buf[7];
    }
}
