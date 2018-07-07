package gnova.math;

import gnova.annotation.Immutable;
import gnova.annotation.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * 二维向量
 *
 * 该类是一个不可变的类，除了构造器之外的所有方法都不会改变向量本身
 *
 * @author birderyu
 * @version 1.0.0
 */
@Immutable
public final class Vector2D
        implements Cloneable, Serializable {

    /**
     * 零向量
     */
    public static final Vector2D ZERO = new Vector2D(0, 0);

    /**
     * X分量
     */
    private final double x;

    /**
     * Y分量
     */
    private final double y;

    /**
     * 构造一个向量
     *
     * @param x X分量
     * @param y Y分量
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 获取X分量
     *
     * @return X分量
     */
    public double getX() {
        return x;
    }

    /**
     * 获取Y分量
     *
     * @return Y分量
     */
    public double getY() {
        return y;
    }

    /**
     * 获取向量的弧度
     *
     * @return 弧度值
     */
    public double radian() {
        return Math.atan2(y, x);
    }

    /**
     * 获取向量的角度
     *
     * @return 角度值
     */
    public double angle() {
        return Radian.radian2Angle(radian());
    }

    /**
     * 获取向量的长度
     *
     * @return 长度值
     */
    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * 获取向量的和
     *
     * @param v 向量
     * @return 两个向量之和
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    /**
     * 获取向量的差
     *
     * @param v 向量
     * @return 两个向量之差
     */
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    /**
     * 获取向量与另一个数字的乘积
     *
     * @param v 数字
     * @return 向量
     */
    public Vector2D multiply(double v) {
        return new Vector2D(x * v, y * v);
    }

    /**
     * 获取向量与另一个数字的商
     *
     * @param v 数字
     * @return 向量
     */
    public Vector2D divide(double v) {
        return new Vector2D(x / v, y / v);
    }

    /**
     * 获取向量的点乘积（数量积）
     *
     * @param v 向量
     * @return 点乘积
     */
    public double dotProduct(Vector2D v) {
        return x * v.x + y * v.y;
    }

    /**
     * 获取向量的叉乘积（向量积）
     *
     * @param v 向量
     * @return 叉乘积
     */
    public double crossProduct(Vector2D v) {
        return x * v.y - y * v.x;
    }

    /**
     * 获取两个向量夹角的弧度
     *
     * @param v 向量
     * @return 弧度
     */
    public double radianBetween(Vector2D v) {
        Vector2D v1 = normalize();
        Vector2D v2 = v.normalize();
        return Math.acos(v1.dotProduct(v2));
    }

    /**
     * 获取两个向量夹角的角度
     *
     * @param v 向量
     * @return 角度
     */
    public double angleBetween(Vector2D v) {
        return Radian.radian2Angle(radianBetween(v));
    }

    /**
     * 当前向量是否为零向量
     *
     * @return 若当前向量为零向量，则返回true，否则返回false
     */
    public boolean isZero() {
        if (this == ZERO) {
            return true;
        }
        return x == 0 && y == 0;
    }

    /**
     * 当前向量是否为标准向量
     *
     * 标准向量即长度为1的向量
     *
     * @return 若当前向量为标准向量，则返回true，否则返回false
     */
    public boolean isNormalize() {
        return length() == 1;
    }

    /**
     * 设置向量的长度
     *
     * @param length 长度
     * @return 设置后的向量
     */
    public Vector2D lengthOf(double length) {
        double a = angle();
        return new Vector2D(Math.cos(a) * length, Math.sin(a) * length);
    }

    /**
     * 标准化向量
     *
     * @return 标准后的向量
     */
    public Vector2D normalize() {
        double l = length();
        if (l == 1) {
            return clone();
        }
        return divide(l);
    }

    /**
     * 翻转向量
     *
     * @return 翻转后的向量
     */
    public Vector2D reverse() {
        return new Vector2D(-x, -y);
    }

    /**
     * 克隆向量
     *
     * @return 克隆后的向量
     * @see Object#clone()
     */
    @Override
    public Vector2D clone() {
        return new Vector2D(x, y);
    }

    /**
     * 判断两个对象是否相等
     *
     * @param o 对象
     * @return 若对象相等，则返回true，否则返回false
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2D vector2D = (Vector2D) o;
        return Double.compare(vector2D.x, x) == 0 &&
                Double.compare(vector2D.y, y) == 0;
    }

    /**
     * 获取当前对象的散列值
     *
     * @return 散列值
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * 转换为字符串
     *
     * @return 字符串
     * @see Object#toString()
     */
    @Override
    @NotNull
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
