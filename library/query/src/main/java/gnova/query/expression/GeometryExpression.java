package gnova.query.expression;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

public final class GeometryExpression
        extends AbstractValueExpression {

    public static final GeometryExpression EMPTY = new GeometryExpression(Geometry.NONE);

    @NotNull
    private final Geometry value;

    /**
     *
     * @param value 不可以为null，必须为{@link Geometry#NONE 空几何对象}、{@link Polygon 多边形类型}或{@link MultiPolygon 多多边形类型}
     */
    public GeometryExpression(@Checked Geometry value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return this == EMPTY || value.isEmpty();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Geometry;
    }

    @Override
    @NotNull
    public Geometry getValue() {
        return value;
    }

    @Override
    public void checkBy(ValuePredicate predicate) throws IllegalArgumentException {
        // 几何区域值只能和等于、不等于、几何相交、几何包含相匹配
        if (predicate == ValuePredicate.EQ || predicate == ValuePredicate.NEQ ||
                predicate == ValuePredicate.INTERSECT || predicate == ValuePredicate.WITHIN) {
            return;
        }
        throw new IllegalArgumentException("几何区域值只能和等于、不等于、几何相交、几何包含相匹配");
    }

    @Override
    public Geometry asGeometry() {
        return value;
    }

    @Override
    public String toString() {
        if (value.getType() == GeometryType.None) {
            return "{}";
        } else if (value.getType() == GeometryType.Polygon) {
            return "{" + toString((Polygon) value) + "}";
        } else {
            return "{" + toString((MultiPolygon) value) + "}";
        }
    }

    @Override
    protected boolean equalsBy(@NotNull Object left) {
        if (value == null) {
            return false;
        }
        Geometry gl = null;
        if (left instanceof GeometryExpression) {
            gl = ((GeometryExpression) left).value;
        } else if (left instanceof Geometry) {
            gl = (Geometry) left;
        }
        if (gl == null) {
            return false;
        }
        return gl.equals(value);
    }

    @Override
    protected boolean unequalsBy(@NotNull Object left) {
        if (value == null) {
            return false;
        }
        Geometry gl = null;
        if (left instanceof GeometryExpression) {
            gl = ((GeometryExpression) left).value;
        } else if (left instanceof Geometry) {
            gl = (Geometry) left;
        }
        if (gl == null) {
            return false;
        }
        return !gl.equals(value);
    }

    @Override
    protected boolean intersectBy(@NotNull Object left) {
        if (value == null) {
            return false;
        }
        Geometry gl = null;
        if (left instanceof GeometryExpression) {
            gl = ((GeometryExpression) left).value;
        } else if (left instanceof Geometry) {
            gl = (Geometry) left;
        }
        if (gl == null) {
            return false;
        }
        return gl.intersects(value);
    }

    @Override
    protected boolean withinBy(@NotNull Object left) {
        if (value == null) {
            return false;
        }
        Geometry gl = null;
        if (left instanceof GeometryExpression) {
            gl = ((GeometryExpression) left).value;
        } else if (left instanceof Geometry) {
            gl = (Geometry) left;
        }
        if (gl == null) {
            return false;
        }
        return gl.within(value);
    }

    private String toString(LineString ls) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int n = ls.size();
        for (int i = 0; i < n; i++) {
            Point point = ls.getPointAt(i);
            sb.append(point.getX());
            sb.append(" ");
            sb.append(point.getY());
            if (i != n - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    private String toString(Polygon polygon) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (!polygon.isEmpty()) {
            sb.append(toString(polygon.getExteriorRing()));
            int n = polygon.getInteriorRingSize();
            if (n > 0) {
                sb.append(", ");
                for (int i = 0; i < n; i++) {
                    sb.append(toString(polygon.getInteriorRingAt(i)));
                    if (i != n - 1) {
                        sb.append(", ");
                    }
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String toString(MultiPolygon mp) {
        StringBuilder sb = new StringBuilder();
        int n = mp.size();
        for (int i = 0; i < n; i++) {
            sb.append(toString(mp.getPolygonAt(i)));
            if (i != n - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
