package gnova.query.value;

import gnova.annotation.NotNull;
import gnova.query.CompareOperator;
import gnova.query.ValueExpression;
import gnova.query.ValueType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringExpression
        extends ValueExpression {

    public static final StringExpression EMPTY = new StringExpression("");

    private final String value;
    private Pattern pattern = null;

    public StringExpression(@NotNull String value) {
        this.value = value;
    }

    public Pattern getPattern() {
        return pattern == null ?
                pattern = Pattern.compile("^.*" + value + ".*$", Pattern.CASE_INSENSITIVE) :
                pattern;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Double;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String checkBy(CompareOperator co) {
        // 字符串值只能和等于、不等于、字符串模糊匹配相匹配
        if (co == CompareOperator.EQ || co == CompareOperator.NEQ ||
                co == CompareOperator.LIKE) {
            return null;
        }
        return "字符串值只能和等于、不等于、字符串模糊匹配相匹配";
    }

    @Override
    public boolean asBoolean(boolean defaultValue) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        } else if ("false".equalsIgnoreCase(value)) {
            return false;
        } else {
            return defaultValue;
        }
    }

    @Override
    public int asInt32(int defaultValue) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    public long asInt64(long defaultValue) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    public double asDouble(double defaultValue) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof StringExpression) {
            return ((StringExpression)left).value.equals(value);
        } else if (left instanceof String) {
            return left.equals(value);
        } else {
            return left.toString().equals(value);
        }
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (left instanceof StringExpression) {
            return !((StringExpression)left).value.equals(value);
        } else if (left instanceof String) {
            return !left.equals(value);
        } else {
            return !left.toString().equals(value);
        }
    }

    @Override
    protected boolean likeBy(@NotNull Object left) {
        String sl;
        if (left instanceof StringExpression) {
            sl = ((StringExpression) left).value;
        } else if (left instanceof String) {
            sl = (String) left;
        } else {
            sl = left.toString();
        }
        Matcher matcher = getPattern().matcher(sl);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }
}
