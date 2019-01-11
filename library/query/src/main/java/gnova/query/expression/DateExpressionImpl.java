package gnova.query.expression;

import gnova.core.annotation.NotNull;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public final class DateExpressionImpl
        extends AbstractValueExpression implements DateExpression {

    private final static DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    private final DateTime value;
    private final String formatten;

    public DateExpressionImpl(Date date) {

        if (date != null) {
            value = new DateTime(date);
        } else {
            value = DateTime.now();
        }
        formatten = "@('" + value.toString(DEFAULT_DATE_TIME_FORMATTER) + "')";
    }

    public DateExpressionImpl(@NotNull String date)
            throws UnsupportedOperationException, IllegalArgumentException {

        value = DateTime.parse(date, DEFAULT_DATE_TIME_FORMATTER);
        formatten = "@('" + date + "')";
    }

    public DateExpressionImpl(@NotNull String date, String format)
            throws UnsupportedOperationException, IllegalArgumentException {

        DateTimeFormatter dtFormat = DEFAULT_DATE_TIME_FORMATTER;
        if (format != null) {
            dtFormat = DateTimeFormat.forPattern(format);
        }
        value = DateTime.parse(date, dtFormat);
        if (format == null) {
            formatten = "@('" + date + "')";
        } else {
            formatten = "@('" + date + "', '" + format + "')";;
        }

    }

    @Override
    @NotNull
    public Date getValue() {
        return value.toDate();
    }

    @Override
    public long asInt64(long defaultValue) {
        return value.getMillis();
    }

    @Override
    public Date asDate() {
        return getValue();
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof Date) {
            DateTime leftValue = new DateTime(left);
            return leftValue.isEqual(value);
        } else if (left instanceof DateTime) {
            return ((DateTime) left).isEqual(value);
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).asTimestamp() == value.getMillis();
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).getValue().isEqual(value);
        }
        return false;
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (left instanceof Date) {
            DateTime leftValue = new DateTime(left);
            return !leftValue.isEqual(value);
        } else if (left instanceof DateTime) {
            return !((DateTime) left).isEqual(value);
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).asTimestamp() != value.getMillis();
        } else if (left instanceof DateExpression) {
            return !((DateExpression) left).value.isEqual(value);
        }
        return false;
    }

    @Override
    protected boolean lessBy(@NotNull Object left) {
        if (left instanceof Date) {
            DateTime leftValue = new DateTime(left);
            return leftValue.isBefore(value);
        } else if (left instanceof DateTime) {
            return ((DateTime) left).isBefore(value);
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).asTimestamp() < value.getMillis();
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).value.isBefore(value);
        }
        return false;
    }

    @Override
    protected boolean lessEqualsBy(@NotNull Object left) {
        if (left instanceof Date) {
            DateTime leftValue = new DateTime(left);
            return leftValue.isBefore(value) || leftValue.isEqual(value);
        } else if (left instanceof DateTime) {
            DateTime leftValue = (DateTime) left;
            return leftValue.isBefore(value) || leftValue.isEqual(value);
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).asTimestamp() <= value.getMillis();
        } else if (left instanceof DateExpression) {
            DateTime leftValue = ((DateExpression) left).value;
            return leftValue.isBefore(value) || leftValue.isEqual(value);
        }
        return false;
    }

    @Override
    protected boolean greaterBy(@NotNull Object left) {
        if (left instanceof Date) {
            DateTime leftValue = new DateTime(left);
            return leftValue.isAfter(value);
        } else if (left instanceof DateTime) {
            return ((DateTime) left).isAfter(value);
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).asTimestamp() > value.getMillis();
        } else if (left instanceof DateExpression) {
            return ((DateExpression) left).value.isAfter(value);
        }
        return false;
    }

    @Override
    protected boolean greaterEqualsBy(@NotNull Object left) {
        if (left instanceof Date) {
            DateTime leftValue = new DateTime(left);
            return leftValue.isAfter(value) || leftValue.isEqual(value);
        } else if (left instanceof DateTime) {
            DateTime leftValue = (DateTime) left;
            return leftValue.isAfter(value) || leftValue.isEqual(value);
        } else if (left instanceof TimestampExpression) {
            return ((TimestampExpression) left).asTimestamp() >= value.getMillis();
        } else if (left instanceof DateExpression) {
            DateTime leftValue = ((DateExpression) left).value;
            return leftValue.isAfter(value) || leftValue.isEqual(value);
        }
        return false;
    }

    @Override
    public String toString() {
        return formatten;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof DateExpression)) {
            return false;
        }
        DateExpression de = (DateExpression) obj;
        return value.equals(de.value);
    }

    @Override
    protected int hashing() {
        return value.hashCode();
    }

}
