package gnova.drawing.d2;

import gnova.core.annotation.Immutable;
import gnova.drawing.Radian;

import java.util.Objects;

/**
 * 二维变换矩阵
 */
@Immutable
public class Matrix {

    public static Matrix IDENTITY = new Matrix();

    /*
     * | m11  m12 | = x scale, y shear
     * | m21  m22 | = x shear, y scale
     * | dx   dy  | = x translation, y translation
     */
    private final float m11;
    private final float m12;
    private final float dx;
    private final float m21;
    private final float m22;
    private final float dy;

    /**
     * 构造一个变换矩阵
     */
    public Matrix() {
        this(1, 0, 0, 1, 0, 0);
    }

    /**
     * 构造一个变换矩阵
     *
     * @param m11 r1 c1
     * @param m12 r1 c2
     * @param m21 r2 c1
     * @param m22 r2 c2
     * @param dx r3 c1
     * @param dy r3 c2
     */
    public Matrix(float m11, float m12, float m21, float m22, float dx, float dy) {
        this.m11 = m11;
        this.m12 = m12;
        this.dx = dx;
        this.m21 = m21;
        this.m22 = m22;
        this.dy = dy;
    }

    public float[] getElements() {
       return new float[] { m11, m12, m21, m22, dx, dy };
    }

    public float getOffsetX() {
        return dx;
    }

    public float getOffsetY() {
        return dy;
    }

    public float getScaleX() {
        return m11;
    }

    public float getScaleY() {
        return m22;
    }

    public float getShearX() {
        return m21;
    }

    public float getShearY() {
        return m12;
    }

    public boolean isIdentity() {
        return Float.compare(m11, 1) == 0 &&
                Float.compare(m12, 0) == 0 &&
                Float.compare(m21, 0) == 0 &&
                Float.compare(m22, 1) == 0 &&
                Float.compare(dx, 0) == 0 &&
                Float.compare(dy, 0) == 0;
    }

    /**
     * 是否是可逆的
     *
     * @return
     */
    public boolean isInvertable() {
        return Float.compare(determinant(), 0) != 0;
    }

    public Matrix invert() {

        float det = determinant();

        // A 0 determinant matrix cannot be inverted
        if (isInvertable()) {
            // And her's why! div by zero...
            double oodet = 1.0 / det;

            // Use doubles for intermediate calcs. Precision goes way up
            double m11 = this.m22 * oodet;
            double m12 = -this.m12 * oodet;

            double m21 = -this.m21 * oodet;
            double m22 = this.m11 * oodet;

            double dx = -(this.dx * m11 + this.dy * m21);
            double dy = -(this.dx * m12 + this.dy * m22);

            //double dx = (this.dy * m21) - (this.dx * m11);
            //double dy = (this.dx * m12) - (this.dy * m22);

            return new Matrix((float) m11, (float) m12, (float) m21, (float) m22, (float) dx, (float) dy);
        } else {
            throw new IllegalArgumentException("The matrix cannot be inverted (Neo would fall on his head). Use IsInvertable to check whether or not a matrix can be inverted.");
        }
    }

    public Matrix normalize() {

        float m11 = this.m11, m12 = this.m12, dx = this.dx, m21 = this.m21, m22 = this.m22, dy = this.dy;

        float mta = m11 * m11 + m12 * m12;
        if (mta != 1 && mta != 0)
        {
            float na = 1.0f / (float) Math.sqrt(mta);
            m11 *= na;
            m12 *= na;
        }

        float mtb = m21 * m21 + m22 * m22;
        if (mtb != 1 && mtb != 0)
        {
            float nb = 1.0f / (float) Math.sqrt(mtb);
            m11 *= nb;
            m12 *= nb;
        }
        return new Matrix(m11, m12, m21, m22, dx, dy);
    }

    public Matrix multiply(Matrix other) {
        return multiply(this, other);
    }

    public Matrix multiplyBy(Matrix other) {
        return multiply(other, this);
    }

    public Matrix translate(float offsetX, float offsetY) {
        return multiplyBy(new Matrix(1, 0, 0, 1, offsetX, offsetY));
    }

    public Matrix scale(float scaleX, float scaleY) {
        return multiplyBy(new Matrix(scaleX, 0, 0, scaleY, 0, 0));
    }

    public Matrix shear(float shearX, float shearY) {
        return multiplyBy(new Matrix(1, shearY, shearX, 1, 0, 0));
    }

    /**
     *
     * @param angle 角度值
     * @return
     */
    public Matrix rotate(float angle) {
        float r = Radian.angle2Radian(angle);
        return multiplyBy(new Matrix((float)Math.cos(r), (float)Math.sin(r),
                        -(float)Math.sin(r), (float)Math.cos(r), 0, 0));
    }

    public Matrix rotateAt(float centerX, float centerY, float angle) {
        Matrix m = new Matrix();
        m = m.multiply(new Matrix(1, 0, 0, 1, -centerX, -centerY));
        double r = Radian.angle2Radian(angle);
        m = m.multiply(new Matrix((float)Math.cos(r), (float)Math.sin(r),
                -(float)Math.sin(r), (float)Math.cos(r), 0, 0));
        m = m.multiply(new Matrix(1, 0, 0, 1, centerX, centerY));
        return multiplyBy(m);
    }

    public Vector transform(Vector vector) {
        return new Vector((vector.getX() * m11 + vector.getY() * m21),
                (vector.getY() * m12 + vector.getY() * m22));
    }

    public Point transform(Point point) {
        return new Point((point.getX() * m11 + point.getY() * m21) + dx,
                (point.getY() * m12 + point.getY() * m22) + dy);
    }



    /**
     * 计算矩阵的行列式
     *
     * @return
     */
    private float determinant() {
        return (m11 * m22) - (m12 * m21);
    }

    public static Matrix multiply(Matrix a, Matrix b) {
        double m11 = (a.m11 * b.m11) + (a.m12 * b.m21);
        double m12 = (a.m11 * b.m12) + (a.m12 * b.m22);

        double m21 = (a.m21 * b.m11) + (a.m22 * b.m21);
        double m22 = (a.m21 * b.m12) + (a.m22 * b.m22);

        double dx = (a.dx * b.m11) + (a.dy * b.m21) + b.dx;
        double dy = (a.dx * b.m12) + (a.dy * b.m22) + b.dy;

        return new Matrix((float) m11, (float) m12, (float) m21, (float) m22, (float) dx, (float) dy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Float.compare(matrix.m11, m11) == 0 &&
                Float.compare(matrix.m12, m12) == 0 &&
                Float.compare(matrix.dx, dx) == 0 &&
                Float.compare(matrix.m21, m21) == 0 &&
                Float.compare(matrix.m22, m22) == 0 &&
                Float.compare(matrix.dy, dy) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(m11, m12, dx, m21, m22, dy);
    }
}
