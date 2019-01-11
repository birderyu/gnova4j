package gnova.query.expression;

import gnova.core.ArrayUtil;
import gnova.core.annotation.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public final class BytesExpressionImpl
        extends AbstractValueExpression implements BytesExpression {

    public static BytesExpression EMPTY = new BytesExpressionImpl(new byte[0]);

    @NotNull
    private final byte[] value;

    public BytesExpressionImpl(@NotNull byte[] value) {
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return this == EMPTY || value.length == 0;
    }

    @Override
    @NotNull
    public byte[] getValue() {
        return value;
    }

    @Override
    public byte[] asBytes() {
        return value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (byte b : value) {
            int i = b;
            if (i < 0) {
                // 由于java是有符号的，当i小于0时，会显示补码
                i += 256;
            }
            String s = Integer.toString(i, 16).toUpperCase();
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s);
            sb.append(" ");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.append("]").toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BytesExpression)) {
            return false;
        }
        BytesExpression be = (BytesExpression) obj;
        return Arrays.equals(value, be.getValue());
    }

    @Override
    protected int hashing() {
        return Arrays.hashCode(value);
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (left instanceof BytesExpression) {
            return Arrays.equals(value, ((BytesExpression) left).asBytes());
        } else if (left.getClass().isArray()) {
            if (left.getClass().getComponentType() == byte.class) {
                return Arrays.equals(value, (byte[]) left);
            } else if (left.getClass().getComponentType() == Byte.class) {
                return ArrayUtil.equals(value, (Byte[]) left);
            }
        } else if (left instanceof Iterable) {
            Iterable iterable = (Iterable) left;
            Collection<Byte> _bytes = new ArrayList<>();
            for (Object object : iterable) {
                if (!(object instanceof Byte)) {
                    return false;
                }
                _bytes.add((Byte) object);
            }
            return ArrayUtil.equals(value, _bytes.toArray(new Byte[_bytes.size()]));
        } else if (left instanceof Iterator) {
            Iterator iterator = (Iterator) left;
            Collection<Byte> _bytes = new ArrayList<>();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                if (!(object instanceof Byte)) {
                    return false;
                }
                _bytes.add((Byte) object);
            }
            return ArrayUtil.equals(value, _bytes.toArray(new Byte[_bytes.size()]));
        }
        return false;
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        return !equalsBy(left);
    }

}
