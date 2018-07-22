package gnova.geometry.model;

import gnova.core.annotation.Immutable;
import gnova.core.annotation.NotNull;
import gnova.core.Equals;

import java.io.Serializable;
import java.util.Objects;

/**
 * 坐标
 *
 * <p>坐标表示一个坐标对象，可以包含X、Y、Z坐标值和一个度量值（M值）。
 * <p>坐标是一个{@link Immutable 不可变的}对象。
 *
 * @see Comparable
 * @see Cloneable
 * @see Serializable
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
@Immutable
public final class Coordinate
        implements Comparable<Coordinate>, Cloneable, Serializable {

    /**
     * 一个空的坐标
     */
    public static Coordinate NONE = new Coordinate(
            Coordinate.NULL_ORDINATE_VALUE,
            Coordinate.NULL_ORDINATE_VALUE,
            Coordinate.NULL_ORDINATE_VALUE,
            Coordinate.NULL_ORDINATE_VALUE);

    /**
     * 空坐标值，等于{@link Double#NaN}
     */
    public static final double NULL_ORDINATE_VALUE = Double.NaN;

    /**
     * X坐标的纵轴
     */
    public static final int ORDINATE_X = 0;

    /**
     * Y坐标的纵轴
     */
    public static final int ORDINATE_Y = 1;

    /**
     * Z坐标的纵轴
     */
    public static final int ORDINATE_Z = 2;

    /**
     * 度量值的纵轴
     */
    public static final int ORDINATE_M = 3;

    /**
     * X坐标值
     */
    private final double x;

    /**
     * Y坐标值
     */
    private final double y;

    /**
     * Z坐标值
     */
    private final double z;

    /**
     * 度量值
     */
    private final double m;

    /**
     * 构造一个坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     */
    public Coordinate(double x, double y) {
        this(x, y, NULL_ORDINATE_VALUE, NULL_ORDINATE_VALUE);
    }

    /**
     * 构造一个坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     */
    public Coordinate(double x, double y, double z) {
        this(x, y, z, NULL_ORDINATE_VALUE);
    }

    /**
     * 构造一个坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     * @param m 度量值
     */
    public Coordinate(double x, double y, double z, double m) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.m = m;
    }

    /**
     * 构造一个坐标
     *
     * @param c 坐标值，不允许为null
     */
    public Coordinate(@NotNull Coordinate c) {
        this(c.x, c.y, c.z, c.m);
    }

    /**
     * 是否包含Z坐标值
     *
     * @return 若包含Z坐标值，则返回true，否则返回false
     */
    public boolean hasZ() {
        return !Double.isNaN(z);
    }

    /**
     * 是否包含度量值
     *
     * @return 若包含度量值，则返回true，否则返回false
     */
    public boolean hasM() {
        return !Double.isNaN(m);
    }

    /**
     * 获取X坐标值
     *
     * @return X坐标值
     */
    public double getX() {
        return x;
    }

    /**
     * 获取Y坐标值
     *
     * @return Y坐标值
     */
    public double getY() {
        return y;
    }

    /**
     * 获取Z坐标值
     *
     * @return Z坐标值
     */
    public double getZ() {
        return z;
    }

    /**
     * 获取度量值
     *
     * @return 度量值
     */
    public double getM() {
        return m;
    }

    /**
     * 根据纵轴获取坐标值
     *
     * @param ordinateId 纵轴
     * @return 坐标值
     * @throws IllegalArgumentException 纵轴必须是0、1、2、3这四个值，否则抛出此异常
     */
    public double getOrdinate(int ordinateId) throws IllegalArgumentException {
        switch (ordinateId) {
            case ORDINATE_X: return x;
            case ORDINATE_Y: return y;
            case ORDINATE_Z: return z;
            case ORDINATE_M: return m;
        }
        throw new IllegalArgumentException("Invalid ordinate index: " + ordinateId);
    }

    /**
     * 获取与另一个坐标在二维平面上的距离
     *
     * <p>二维平面上的距离是指X与Y坐标构成的平面上的距离，即不考虑Z坐标
     *
     * @param c 坐标值，不允许为null
     * @return 距离值
     */
    public double distance2D(@NotNull Coordinate c) {
        double dx = x - c.x;
        double dy = y - c.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 获取与另一个坐标在三维空间上的距离
     *
     * <p>三维空间上的距离是指X、Y、Z坐标构成的立体空间中的距离
     *
     * @param c 坐标值，不允许为null
     * @return 距离值
     */
    public double distance3D(@NotNull Coordinate c) {
        double dx = x - c.x;
        double dy = y - c.y;
        double dz = z - c.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * 获取与另一个坐标的距离
     *
     * <p>若两个坐标都有Z坐标值，则返回三维空间上的距离，否则返回二维平面上的距离
     *
     * @param c 坐标值，不允许为null
     * @return 距离值
     */
    public double distance(@NotNull Coordinate c) {
        if (!hasZ() || !c.hasZ()) {
            return distance2D(c);
        }
        return distance3D(c);
    }

    /**
     * 判断与另一个坐标在二维平面上是否相等
     *
     * <p>二维平面是指X与Y坐标构成的平面，即不考虑Z坐标
     *
     * @param c 坐标值，不允许为null
     * @return 若在二维平面上相等，则返回true，否则返回false
     */
    public boolean equals2D(@NotNull Coordinate c) {
        if (this == c) {
            return true;
        } else if (this == NONE || c == NONE) {
            return false;
        }
        return Double.compare(x, c.x) == 0 &&
                Double.compare(y, c.y) == 0;
    }

    /**
     * 判断与另一个坐标在二维平面上是否相等
     *
     * <p>二维平面是指X与Y坐标构成的平面，即不考虑Z坐标
     *
     * @param c 坐标值，不允许为null
     * @param tolerance 浮点比较的精度值，若两个浮点数之差的绝对值不大于该精度，则认为这两个浮点数相等
     * @return 若在二维平面上相等，则返回true，否则返回false
     */
    public boolean equals2D(@NotNull Coordinate c, double tolerance) {
        if (this == c) {
            return true;
        } else if (this == NONE || c == NONE) {
            return false;
        }
        return Equals.doubleEquals(x, c.x, tolerance) &&
                Equals.doubleEquals(y, c.y, tolerance);
    }

    /**
     * 判断与另一个坐标在三维空间上是否相等
     *
     * <p>三维空间是指X、Y、Z坐标构成的立体空间
     *
     * @param c 坐标值，不允许为null
     * @return 若在三维空间上相等，则返回true，否则返回false
     */
    public boolean equals3D(@NotNull Coordinate c) {
        if (this == c) {
            return true;
        } else if (this == NONE || c == NONE) {
            return false;
        }
        return Double.compare(x, c.x) == 0 &&
                Double.compare(y, c.y) == 0 &&
                ((!hasZ() && !c.hasZ()) || Double.compare(z, c.z) == 0);
    }

    /**
     * 判断与另一个坐标在三维空间上是否相等
     *
     * <p>三维空间是指X、Y、Z坐标构成的立体空间
     *
     * @param c 坐标值，不允许为null
     * @param tolerance 浮点比较的精度值，若两个浮点数之差的绝对值不大于该精度，则认为这两个浮点数相等
     * @return 若在三维空间上相等，则返回true，否则返回false
     */
    public boolean equals3D(@NotNull Coordinate c, double tolerance) {
        if (this == c) {
            return true;
        } else if (this == NONE || c == NONE) {
            return false;
        }
        return Equals.doubleEquals(x, c.x, tolerance) &&
                Equals.doubleEquals(y, c.y, tolerance) &&
                ((!hasZ() && !c.hasZ()) || Equals.doubleEquals(z, c.z, tolerance));
    }

    /**
     * 判断与另一个坐标是否相等
     *
     * <p>若两个坐标X、Y、Z坐标和度量值都相等，则认为二者相等
     *
     * @param c 坐标值，不允许为null
     * @return 若相等，则返回true，否则返回false
     */
    public boolean equals(@NotNull Coordinate c) {
        if (this == c) {
            return true;
        } else if (this == NONE || c == NONE) {
            return false;
        }
        return Double.compare(x, c.x) == 0 &&
                Double.compare(y, c.y) == 0 &&
                ((!hasZ() && !c.hasZ()) || Double.compare(z, c.z) == 0) &&
                ((!hasM() && !c.hasM()) || Double.compare(m, c.m) == 0);
    }

    /**
     * 判断与另一个坐标是否相等
     *
     * <p>若两个坐标X、Y、Z坐标和度量值都相等，则认为二者相等
     *
     * @param c 坐标值，不允许为null
     * @param tolerance 浮点比较的精度值，若两个浮点数之差的绝对值不大于该精度，则认为这两个浮点数相等
     * @return 若相等，则返回true，否则返回false
     */
    public boolean equals(@NotNull Coordinate c, double tolerance) {
        if (this == c) {
            return true;
        } else if (this == NONE || c == NONE) {
            return false;
        }
        return Equals.doubleEquals(x, c.x, tolerance) &&
                Equals.doubleEquals(y, c.y, tolerance) &&
                ((!hasZ() && !c.hasZ()) || Equals.doubleEquals(z, c.z, tolerance)) &&
                ((!hasM() && !c.hasM()) || Equals.doubleEquals(m, c.m, tolerance));
    }

    /**
     * 判断与对象是否相等
     *
     * @param obj 对象
     * @return 若相等，则返回{@code true}，否则返回{@code false}
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinate)) {
            return false;
        }
        return equals((Coordinate) obj);
    }

    /**
     * 比较两个坐标值在二维平面上的大小
     *
     * <p>二维平面是指X与Y坐标构成的平面，即不考虑Z坐标
     *
     * @param c 坐标值，不允许为null
     * @return 若当前坐标值在二维平面上小于另一个坐标值，则返回值小于0；
     *          <br>若当前坐标值在二维平面上等于另一个坐标值，则返回值等于0；
     *          <br>若当前坐标值在二维平面上大于另一个坐标值，则返回值大于0。
     * @see Comparable#compareTo(Object)
     */
    public int compareTo2D(@NotNull Coordinate c) {
        int c2x = Double.compare(x, c.x);
        if (c2x != 0) return c2x;
        return Double.compare(y, c.y);
    }

    /**
     * 比较两个坐标值在三维空间上的大小
     *
     * <p>三维空间是指X、Y、Z坐标构成的立体空间
     *
     * @param c 坐标值，不允许为null
     * @return 若当前坐标值在三维空间上小于另一个坐标值，则返回值小于0；
     *          <br>若当前坐标值在三维空间上等于另一个坐标值，则返回值等于0；
     *          <br>若当前坐标值在三维空间上大于另一个坐标值，则返回值大于0。
     * @see Comparable#compareTo(Object)
     */
    public int compareTo3D(@NotNull Coordinate c) {
        int c2x = Double.compare(x, c.x);
        if (c2x != 0) return c2x;
        int c2y = Double.compare(y, c.y);
        if (c2y != 0) return c2y;
        return compareToZM(z, c.z);
    }

    /**
     * 比较两个坐标值的大小
     *
     * @param c 坐标值，不允许为null
     * @return 若当前坐标值小于另一个坐标值，则返回值小于0；
     *          <br>若当前坐标值等于另一个坐标值，则返回值等于0；
     *          <br>若当前坐标值大于另一个坐标值，则返回值大于0。
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@NotNull Coordinate c) {
        int c2x = Double.compare(x, c.x);
        if (c2x != 0) return c2x;
        int c2y = Double.compare(y, c.y);
        if (c2y != 0) return c2y;
        int c2z = compareToZM(z, c.z);
        if (c2z != 0) return c2z;
        return compareToZM(m, c.m);
    }

    /**
     * 获取坐标值的散列码
     *
     * @return 散列码
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, m);
    }

    /**
     * 将坐标值转换成字符串
     *
     * @return 字符串
     * @see Object#toString()
     */
    @Override
    @NotNull
    public String toString() {
        if (!hasZ()) {
            return "[" + x + ", " + y + "]";
        } else if (!hasM()) {
            return "[" + x + ", " + y + ", " + z + "]";
        } else {
            return "[" + x + ", " + y + ", " + z + ", " + m + "]";
        }
    }

    /**
     * 克隆当前坐标值
     *
     * @return 坐标值，不会返回null
     * @see Object#clone()
     */
    @Override
    @NotNull
    public Coordinate clone() {
        if (this == NONE) {
            return NONE;
        }
        return new Coordinate(this);
    }

    /**
     * 比较Z坐标和M值大小的方法
     *
     * @param d1 第一个参数
     * @param d2 第二个参数
     * @return 若第一个参数小于第二个参数，则返回值小于0；
     *          <br>若第一个参数等于第二个参数，则返回值等于0；
     *          <br>若第一个参数大于第二个参数，则返回值大于0。
     */
    private static int compareToZM(double d1, double d2) {
        if (Double.isNaN(d1) && Double.isNaN(d2)) return 0;
        if (Double.isNaN(d1)) return -1;
        if (Double.isNaN(d2)) return 1;
        return Double.compare(d1, d2);
    }

}
