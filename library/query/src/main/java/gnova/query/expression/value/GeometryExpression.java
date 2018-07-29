package gnova.query.expression.value;

import gnova.core.annotation.Checked;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;
import gnova.query.expression.CompareOperator;
import gnova.query.expression.ValueExpression;
import gnova.query.expression.ValueType;

public final class GeometryExpression
        extends ValueExpression {

    public static final GeometryExpression EMPTY = new GeometryExpression(Geometry.NONE);

    private final Geometry value;

    /**
     *
     * @param value 不可以为null，必须为{@link Geometry#NONE 空几何对象}、{@link Polygon 多边形类型}或{@link MultiPolygon 多多边形类型}
     */
    public GeometryExpression(@Checked Geometry value) {
        this.value = value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.Geometry;
    }

    @Override
    public Geometry getValue() {
        return value;
    }

    @Override
    public String checkBy(CompareOperator co) {
        // 几何区域值只能和等于、不等于、几何相交、几何包含相匹配
        if (co == CompareOperator.EQ || co == CompareOperator.NEQ ||
                co == CompareOperator.INTERSECT || co == CompareOperator.WITHIN) {
            return null;
        }
        return "几何区域值只能和等于、不等于、几何相交、几何包含相匹配";
    }

    @Override
    public Geometry asGeometry() {
        return value;
    }

    @Override
    public String toString() {
        if (value.getType() == GeometryType.None) {
            return "[]";
        } else if (value.getType() == GeometryType.Polygon) {
            return "[" + toString((Polygon) value) + "]";
        } else {
            return "[" + toString((MultiPolygon) value) + "]";
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
        return sb.toString();
    }

    private String toString(Polygon polygon) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(toString(polygon.getExteriorRing()));
        sb.append(")");
        int n = polygon.getInteriorRingSize();
        if (n > 0) {
            sb.append(", ");
            for (int i = 0; i < n; i++) {
                sb.append("(");
                sb.append(toString(polygon.getInteriorRingAt(i)));
                sb.append(")");
                if (i != n - 1) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

    private String toString(MultiPolygon mp) {
        StringBuilder sb = new StringBuilder();
        int n = mp.size();
        for (int i = 0; i < n; i++) {
            sb.append("(");
            sb.append(toString(mp.getPolygonAt(i)));
            sb.append(")");
            if (i != n - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
