package gnova.geometry.model;

import gnova.annotation.Immutable;
import gnova.annotation.NotNull;
import gnova.util.Equals;

import java.io.Serializable;
import java.util.Objects;

/**
 * 包围盒
 *
 * <p>包围盒是一个矩形或长方体的区域，用以表示几何对象的外包。
 * <p>包围盒是一个{@link Immutable 不可变的}对象，所有返回包围盒自身的方法，都会返回自身的一个拷贝。
 *
 * @see Comparable
 * @see Cloneable
 * @see Serializable
 * @author birderyu
 * @date 2017/6/21
 * @version 1.0.0
 */
@Immutable
public final class BoundingBox
        implements Comparable<BoundingBox>, Cloneable, Serializable {

    /**
     * 一个空的包围盒
     */
    public static BoundingBox NONE = new BoundingBox(
            Coordinate.NULL_ORDINATE_VALUE,
            Coordinate.NULL_ORDINATE_VALUE,
            Coordinate.NULL_ORDINATE_VALUE,
            Coordinate.NULL_ORDINATE_VALUE);

    /**
     * 最小X坐标值
     */
    private final double minX;

    /**
     * 最大X坐标值
     */
    private final double maxX;

    /**
     * 最小Y坐标值
     */
    private final double minY;

    /**
     * 最大Y坐标值
     */
    private final double maxY;

    /**
     * 最小Z坐标值
     */
    private final double minZ;

    /**
     * 最大Z坐标值
     */
    private final double maxZ;

    /**
     * 构造一个包围盒
     *
     * @param bbox 字符串表示的包围盒，应该符合以下格式：
     *             [x1, y1, z1, x2, y2, z2]或[x1, y1, x2, y2]
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     * @throws NumberFormatException 若参数无法转换成为数字，则抛出此异常
     */
    public BoundingBox(String bbox)
            throws IllegalArgumentException, NumberFormatException {

        if (bbox == null || bbox.isEmpty()) {
            throw new IllegalArgumentException("bbox is empty.");
        }
        bbox = bbox.trim();
        if (!bbox.startsWith("[") || !bbox.endsWith("]")) {
            throw new IllegalArgumentException("bbox must start with [ and end with ].");
        }
        bbox = bbox.substring(1, bbox.length() - 1);
        String[] values = bbox.split(",");
        if (values.length != 4 && values.length != 6) {
            throw new IllegalArgumentException("values must be 4 doubles or 6 doubles");
        }

        if (values.length == 4) {
            minX = Double.valueOf(values[0].trim());
            maxX = Double.valueOf(values[2].trim());
            minY = Double.valueOf(values[1].trim());
            maxY = Double.valueOf(values[3].trim());
            minZ = Coordinate.NULL_ORDINATE_VALUE;
            maxZ = Coordinate.NULL_ORDINATE_VALUE;
        } else {
            minX = Double.valueOf(values[0].trim());
            maxX = Double.valueOf(values[4].trim());
            minY = Double.valueOf(values[1].trim());
            maxY = Double.valueOf(values[5].trim());
            minZ = Double.valueOf(values[2].trim());
            maxZ = Double.valueOf(values[3].trim());
        }
    }

    /**
     * 构造一个包围盒
     *
     * @param vs 数组表示的包围盒，应该符合以下格式：
     *             [x1, y1, z1, x2, y2, z2]或[x1, y1, x2, y2]
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    public BoundingBox(double... vs) throws IllegalArgumentException {

        if (vs == null || (vs.length != 4 && vs.length != 6)) {
            throw new IllegalArgumentException("values must be 4 doubles or 6 doubles");
        }
        if (vs.length == 4) {
            minX = vs[0];
            maxX = vs[2];
            minY = vs[1];
            maxY = vs[3];
            minZ = Coordinate.NULL_ORDINATE_VALUE;
            maxZ = Coordinate.NULL_ORDINATE_VALUE;
        } else {
            minX = vs[0];
            maxX = vs[4];
            minY = vs[1];
            maxY = vs[5];
            minZ = vs[2];
            maxZ = vs[3];
        }
    }

    /**
     * 构造一个包围盒
     *
     * @param x X坐标值
     * @param y Y坐标值
     */
    public BoundingBox(double x, double y) {
        minX = maxX = x;
        minY = maxY = y;
        minZ = Coordinate.NULL_ORDINATE_VALUE;
        maxZ = Coordinate.NULL_ORDINATE_VALUE;
    }

    /**
     * 构造一个包围盒
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     */
    public BoundingBox(double x, double y, double z) {
        minX = maxX = x;
        minY = maxY = y;
        minZ = maxZ = z;
    }

    /**
     * 构造一个包围盒
     *
     * @param x1 X坐标值1
     * @param x2 X坐标值2
     * @param y1 Y坐标值1
     * @param y2 Y坐标值2
     */
    public BoundingBox(double x1, double x2,
                       double y1, double y2) {
        minX = x1 < x2 ? x1 : x2;
        maxX = x1 > x2 ? x1 : x2;
        minY = y1 < y2 ? y1 : y2;
        maxY = y1 > y2 ? y1 : y2;
        minZ = Coordinate.NULL_ORDINATE_VALUE;
        maxZ = Coordinate.NULL_ORDINATE_VALUE;
    }

    /**
     * 构造一个包围盒
     *
     * @param x1 X坐标值1
     * @param x2 X坐标值2
     * @param y1 Y坐标值1
     * @param y2 Y坐标值2
     * @param z1 Z坐标值1
     * @param z2 Z坐标值2
     */
    public BoundingBox(double x1, double x2,
                       double y1, double y2,
                       double z1, double z2) {
        minX = x1 < x2 ? x1 : x2;
        maxX = x1 > x2 ? x1 : x2;
        minY = y1 < y2 ? y1 : y2;
        maxY = y1 > y2 ? y1 : y2;
        minZ = z1 < z2 ? z1 : z2;
        maxZ = z1 > z2 ? z1 : z2;
    }

    /**
     * 构造一个包围盒
     *
     * @param c1 坐标1，不允许为null
     * @param c2 坐标2，不允许为null
     */
    public BoundingBox(@NotNull Coordinate c1, @NotNull Coordinate c2) {
        minX = c1.getX() < c2.getX() ? c1.getX() : c2.getX();
        maxX = c1.getX() > c2.getX() ? c1.getX() : c2.getX();
        minY = c1.getY() < c2.getY() ? c1.getY() : c2.getY();
        maxY = c1.getY() > c2.getY() ? c1.getY() : c2.getY();
        if (c1.hasZ() && c2.hasZ()) {
            minZ = c1.getZ() < c2.getZ() ? c1.getZ() : c2.getZ();
            maxZ = c1.getZ() > c2.getZ() ? c1.getZ() : c2.getZ();
        } else {
            minZ = Coordinate.NULL_ORDINATE_VALUE;
            maxZ = Coordinate.NULL_ORDINATE_VALUE;
        }
    }

    /**
     * 构造一个包围盒
     *
     * @param bbox 包围盒，不允许为null
     */
    public BoundingBox(@NotNull BoundingBox bbox) {
        minX = bbox.minX;
        maxX = bbox.maxX;
        minY = bbox.minY;
        maxY = bbox.maxY;
        minZ = bbox.minZ;
        maxZ = bbox.maxZ;
    }

    /**
     * 判断当前包围盒是否包含Z坐标
     *
     * @return 若包含Z坐标，则返回true，否则返回false
     */
    private boolean hasZ() {
        //return !Double.isNaN(minZ) && !Double.isNaN(maxz);
        return !Double.isNaN(minZ);
    }

    /**
     * 获取最小X坐标值
     *
     * @return 最小X坐标值
     */
    public double getMinX() {
        return minX;
    }

    /**
     * 获取最大X坐标值
     *
     * @return 最大X坐标值
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * 获取最小Y坐标值
     *
     * @return 最小Y坐标值
     */
    public double getMinY() {
        return minY;
    }

    /**
     * 获取最大Y坐标值
     *
     * @return 最大Y坐标值
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * 获取最小Z坐标值
     *
     * @return 最小Z坐标值
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     * 获取最大Z坐标值
     *
     * @return 最大Z坐标值
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * 获取长度值
     *
     * <p>长度值即X方向的跨度
     *
     * @return 长度值
     */
    public double getLength() {
        return maxX - minX;
    }

    /**
     * 获取宽度值
     *
     * <p>宽度值即Y方向的跨度
     *
     * @return 宽度值
     */
    public double getWidth() {
        return maxY - minY;
    }

    /**
     * 获取高度值
     *
     * <p>高度值即Z方向的跨度
     *
     * @return 高度值
     */
    public double getHeight() {
        return maxZ - minZ;
    }

    /**
     * 获取面积
     *
     * <p>X和Y方向的平面的面积
     *
     * @return 面积值
     */
    public double getArea() {
        return getLength() * getWidth();
    }

    /**
     * 获取体积值
     *
     * @return 体积值
     */
    public double getVolume() {
        return getArea() * getHeight();
    }

    /**
     * 获取中心点的坐标
     *
     * @return 坐标值，不会返回null
     */
    @NotNull
    public Coordinate centre() {
        return new Coordinate(
                (minX + maxX) / 2.0,
                (minY + maxY) / 2.0,
                (minZ + maxZ) / 2.0);
    }

    /**
     * 判断坐标是否完全位于包围盒内
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @return 若坐标完全位于包围盒内，则返回true，否则返回false
     */
    public boolean intersects(double x, double y) {
        return ! (x > maxX || x < minX ||
                y > maxY || y < minY);
    }

    /**
     * 判断坐标是否完全位于包围盒内
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     * @return 若坐标完全位于包围盒内，则返回true，否则返回false
     */
    public boolean intersects(double x, double y, double z) {
        if (!hasZ()) {
            return false;
        }
        return ! (x > maxX || x < minX ||
                y > maxY || y < minY ||
                z > maxZ || z < minZ);
    }

    /**
     * 判断坐标是否完全位于包围盒内
     *
     * @param coord 坐标值，不允许为null
     * @return 若坐标完全位于包围盒内，则返回true，否则返回false
     */
    public boolean intersects(@NotNull Coordinate coord) {
        return coord.hasZ() ?
                intersects(coord.getX(), coord.getY(), coord.getZ()) :
                intersects(coord.getX(), coord.getY());
    }

    /**
     * 判断另一个包围盒是否完全位于当前包围盒内
     *
     * @param bbox 包围盒，不允许为null
     * @return 若另一个包围盒完全位于当前包围盒内，则返回true，否则返回false
     */
    public boolean intersects(@NotNull BoundingBox bbox) {

        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            return false;
        }

        if (hasZ()) {
            return !(bbox.minX > maxX || bbox.maxX < minX ||
                    bbox.minY > maxY || bbox.maxY < minY ||
                    bbox.minZ > maxZ || bbox.maxZ < minZ);
        } else {
            return !(bbox.minX > maxX || bbox.maxX < minX ||
                    bbox.minY > maxY || bbox.maxY < minY);
        }
    }

    /**
     * 判断包围盒是否包含坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @return 若包围盒包含了坐标，则返回true，否则返回false
     */
    public boolean contains(double x, double y) {
        return covers(x, y);
    }

    /**
     * 判断包围盒是否包含坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     * @return 若包围盒包含了坐标，则返回true，否则返回false
     */
    public boolean contains(double x, double y, double z) {
        return covers(x, y, z);
    }

    /**
     * 判断包围盒是否包含坐标
     *
     * @param coord 坐标值，不允许为null
     * @return 若包围盒包含了坐标，则返回true，否则返回false
     */
    public boolean contains(@NotNull Coordinate coord) {
        return covers(coord);
    }

    /**
     * 判断包围盒是否包含另一个包围盒
     *
     * @param bbox 包围盒，不允许为null
     * @return 若包围盒包含了另一个包围盒，则返回true，否则返回false
     */
    public boolean contains(@NotNull BoundingBox bbox) {
        return covers(bbox);
    }

    /**
     * 判断包围盒是否覆盖坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @return 若包围盒覆盖了坐标，则返回true，否则返回false
     */
    public boolean covers(double x, double y) {
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY;
    }

    /**
     * 判断包围盒是否覆盖坐标
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     * @return 若包围盒覆盖了坐标，则返回true，否则返回false
     */
    public boolean covers(double x, double y, double z) {
        if (!hasZ()) {
            return false;
        }
        return x >= minX && x <= maxX &&
                y >= minY && y <= maxY &&
                z >= minZ && z <= maxZ;
    }

    /**
     * 判断包围盒是否覆盖坐标
     *
     * @param coord 坐标值，不允许为null
     * @return 若包围盒覆盖了坐标，则返回true，否则返回false
     */
    public boolean covers(@NotNull Coordinate coord) {
        return coord.hasZ() ?
                covers(coord.getX(), coord.getY(), coord.getZ()) :
                covers(coord.getX(), coord.getY());
    }

    /**
     * 判断包围盒是否覆盖另一个包围盒
     *
     * @param bbox 包围盒，不允许为null
     * @return 若包围盒覆盖了另一个包围盒，则返回true，否则返回false
     */
    public boolean covers(@NotNull BoundingBox bbox) {
        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            return false;
        }
        if (hasZ()) {
            return bbox.minX >= minX && bbox.maxX <= maxX &&
                    bbox.minY >= minY && bbox.maxY <= maxY &&
                    bbox.minZ >= minZ && bbox.maxZ <= maxZ;
        } else {
            return bbox.minX >= minX && bbox.maxX <= maxX &&
                    bbox.minY >= minY && bbox.maxY <= maxY;
        }
    }

    /**
     * 获取与另一个包围盒之间的距离
     *
     * @param bbox 包围盒，不允许为null
     * @return 距离值
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    public double distance(@NotNull BoundingBox bbox) throws IllegalArgumentException {

        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            throw new IllegalArgumentException("this bbox " + this
                    + " can not distance to giving bbox " + bbox);
        }

        if (intersects(bbox)) return 0;

        double dx = 0.0;
        if (maxX < bbox.minX) dx = bbox.minX - maxX;
        else if (minX > bbox.maxX) dx = minX - bbox.maxX;

        double dy = 0.0;
        if (maxY < bbox.minY) dy = bbox.minY - maxY;
        else if (minY > bbox.maxY) dy = minY - bbox.maxY;

        if (hasZ()) {
            double dz = 0.0;
            if (maxZ < bbox.minZ) dz = bbox.minZ - maxZ;
            else if (minZ > bbox.maxZ) dz = minZ - bbox.maxZ;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        } else {
            // if either is zero, the envelopes overlap either vertically or horizontally
            if (dx == 0.0) return dy;
            if (dy == 0.0) return dx;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }

    /**
     * 扩展当前包围盒的大小，生成一个能够覆盖坐标的新的包围盒
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox expandToInclude(double x, double y) {
        double newMinX = x > minX ? minX : x;
        double newMaxX = x < maxX ? maxX : x;
        double newMinY = y > minY ? minY : y;
        double newMaxY = y < maxY ? maxY : y;
        return new BoundingBox(newMinX, newMaxX,
                newMinY, newMaxY,
                minZ, maxZ);
    }

    /**
     * 扩展当前包围盒的大小，生成一个能够覆盖坐标的新的包围盒
     *
     * @param x X坐标值
     * @param y Y坐标值
     * @param z Z坐标值
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox expandToInclude(double x, double y, double z) {
        if (!hasZ()) {
            throw new IllegalArgumentException("this bbox do not has z: " + this);
        }
        double newMinX = x > minX ? minX : x;
        double newMaxX = x < maxX ? maxX : x;
        double newMinY = y > minY ? minY : y;
        double newMaxY = y < maxY ? maxY : y;
        double newMinZ = z > minZ ? minZ : z;
        double newMaxZ = z < maxZ ? maxZ : z;
        return new BoundingBox(newMinX, newMaxX,
                newMinY, newMaxY,
                newMinZ, newMaxZ);
    }

    /**
     * 扩展当前包围盒的大小，生成一个能够覆盖坐标的新的包围盒
     *
     * @param coord 坐标值，不允许为null
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox expandToInclude(@NotNull Coordinate coord) {
        return coord.hasZ() ?
                expandToInclude(coord.getX(), coord.getY(), coord.getZ()) :
                expandToInclude(coord.getX(), coord.getY());
    }

    /**
     * 扩展当前包围盒的大小，生成一个能够覆盖另一个包围盒的新的包围盒
     *
     * @param bbox 包围盒，不允许为null
     * @return 包围盒，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    public BoundingBox expandToInclude(@NotNull BoundingBox bbox) throws IllegalArgumentException {
        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            throw new IllegalArgumentException("this bbox " + this
                    + " can not expand to giving bbox " + bbox);
        }
        double newMinX = bbox.minX > minX ? minX : bbox.minX;
        double newMaxX = bbox.maxX < maxX ? maxX : bbox.maxX;
        double newMinY = bbox.minY > minY ? minY : bbox.minY;
        double newMaxY = bbox.maxY < maxY ? maxY : bbox.maxY;
        double newminZ = minZ;
        double newMaxZ = maxZ;
        if (hasZ()) {
            newminZ = bbox.minZ > minZ ? minZ : bbox.minZ;
            newMaxZ = bbox.maxZ < maxZ ? maxZ : bbox.maxZ;
        }
        return new BoundingBox(newMinX, newMaxX,
                newMinY, newMaxY,
                newminZ, newMaxZ);
    }

    /**
     * 扩展当前包围盒成为一个新的包围盒
     *
     * @param delta 扩展的大小
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox expandBy(double delta) {
        return expandBy(delta, delta, delta);
    }

    /**
     * 扩展当前包围盒成为一个新的包围盒
     *
     * @param deltaX X方向扩展的大小
     * @param deltaY Y方向扩展的大小
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox expandBy(double deltaX, double deltaY) {
        return new BoundingBox(minX - deltaX, maxX + deltaX,
                minY - deltaY, maxY + deltaY,
                minZ, maxZ);
    }

    /**
     * 扩展当前包围盒成为一个新的包围盒
     *
     * @param deltaX X方向扩展的大小
     * @param deltaY Y方向扩展的大小
     * @param deltaZ Z方向扩展的大小
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox expandBy(double deltaX, double deltaY, double deltaZ) {

        if (hasZ()) {
            return expandBy(deltaX, deltaY);
        }
        return new BoundingBox(minX - deltaX, maxX + deltaX,
                minY - deltaY, maxY + deltaY,
                minZ - deltaZ, maxZ + deltaZ);
    }

    /**
     * 平移当前包围盒成为一个新的包围盒
     *
     * @param trans 平移的距离
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox translate(double trans) {
        return translate(trans, trans, trans);
    }

    /**
     * 平移当前包围盒成为一个新的包围盒
     *
     * @param transX X方向平移的距离
     * @param transY Y方向平移的距离
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox translate(double transX, double transY) {
        return new BoundingBox(minX + transX, maxX + transX,
                minX + transY, maxX + transY,
                minZ, maxZ);
    }

    /**
     * 平移当前包围盒成为一个新的包围盒
     *
     * @param transX X方向平移的距离
     * @param transY Y方向平移的距离
     * @param transZ Z方向平移的距离
     * @return 包围盒，不会返回null
     */
    @NotNull
    public BoundingBox translate(double transX, double transY, double transZ) {
        if (!hasZ()) {
            return translate(transX, transY);
        }
        return new BoundingBox(minX + transX, maxX + transX,
                minX + transY, maxX + transY,
                minZ + transZ, maxZ + transZ);
    }

    /**
     * 获取当前包围盒与另一个包围盒之间的交集
     *
     * @param bbox 包围盒，不允许为null
     * @return 包围盒，不会返回null
     * @throws IllegalArgumentException 若参数不合法，则抛出此异常
     */
    @NotNull
    public BoundingBox intersection(@NotNull BoundingBox bbox) throws IllegalArgumentException {

        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            throw new IllegalArgumentException("this bbox " + this
                    + " can not intersects to giving bbox " + bbox);
        }
        double nexMinX = minX > bbox.minX ? minX : bbox.minX;
        double nexMaxX = maxX < bbox.maxX ? maxX : bbox.maxX;
        double nexMinY = minY > bbox.minY ? minY : bbox.minY;
        double nexMaxY = maxY < bbox.maxY ? maxY : bbox.maxY;
        double nexminZ = minZ;
        double nexMaxZ = maxZ;
        if (hasZ()) {
            nexminZ = minZ > bbox.minZ ? minZ : bbox.minZ;
            nexMaxZ = maxZ < bbox.maxZ ? maxZ : bbox.maxZ;
        }
        return new BoundingBox(nexMinX, nexMaxX, nexMinY, nexMaxY, nexminZ, nexMaxZ);
    }

    /**
     * 判断与另一个包围盒是否相等
     *
     * @param bbox 包围盒，不允许为null
     * @param tolerance 浮点比较的精度值，若两个浮点数之差的绝对值不大于该精度，则认为这两个浮点数相等
     * @return 若相等，则返回true，否则返回false
     */
    @NotNull
    public boolean equals(@NotNull BoundingBox bbox, double tolerance) {
        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            return false;
        }
        if (hasZ()) {
            return Equals.doubleEquals(minX, bbox.minX, tolerance) &&
                    Equals.doubleEquals(maxX, bbox.maxX, tolerance) &&
                    Equals.doubleEquals(minY, bbox.minY, tolerance) &&
                    Equals.doubleEquals(maxY, bbox.maxY, tolerance) &&
                    Equals.doubleEquals(minZ, bbox.minZ, tolerance) &&
                    Equals.doubleEquals(maxZ, bbox.maxZ, tolerance);
        } else {
            return Equals.doubleEquals(minX, bbox.minX, tolerance) &&
                    Equals.doubleEquals(maxX, bbox.maxX, tolerance) &&
                    Equals.doubleEquals(minY, bbox.minY, tolerance) &&
                    Equals.doubleEquals(maxY, bbox.maxY, tolerance);
        }

    }

    /**
     * 判断与另一个包围盒是否相等
     *
     * @param bbox 包围盒，不允许为null
     * @return 若相等，则返回true，否则返回false
     */
    public boolean equals(@NotNull BoundingBox bbox) {
        return equals(bbox, Coordinate.DEFAULT_PRECISION);
    }

    /**
     * 比较两个包围盒的大小
     *
     * @param bbox 包围盒，不允许为null
     * @return 若当前包围盒小于另一个包围盒，则返回值小于0；
     *          <br>若当前包围盒等于另一个包围盒，则返回值等于0；
     *          <br>若当前包围盒大于另一个包围盒，则返回值大于0。
     * @see Comparable#compareTo(Object)
     */
    @Override
    public int compareTo(@NotNull BoundingBox bbox) throws IllegalArgumentException {
        if ((hasZ() && !bbox.hasZ()) || (!hasZ() && bbox.hasZ())) {
            throw new IllegalArgumentException("this bbox " + this
                    + " can not compare to giving bbox " + bbox);
        }
        if (minX < bbox.minX) return -1;
        if (minX > bbox.minX) return 1;
        if (minY < bbox.minY) return -1;
        if (minY > bbox.minY) return 1;
        if (hasZ()) {
            if (minZ < bbox.minZ) return -1;
            if (minZ > bbox.minZ) return 1;
        }
        if (maxX < bbox.maxX) return -1;
        if (maxX > bbox.maxX) return 1;
        if (maxY < bbox.maxY) return -1;
        if (maxY > bbox.maxY) return 1;
        if (hasZ()) {
            if (maxZ < bbox.maxZ) return -1;
            if (maxZ > bbox.maxZ) return 1;
        }
        return 0;
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
        if (!(obj instanceof BoundingBox)) {
            return false;
        }
        return equals((BoundingBox) obj);
    }

    /**
     * 获取包围盒的散列码
     *
     * @return 散列码
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * 将包围盒转换成字符串
     *
     * @return 字符串
     * @see Object#toString()
     */
    @Override
    @NotNull
    public String toString() {
        if (hasZ()) {
            return "[" + minX + ", " + minY + ", " + minZ + ", "
                    + maxX + ", " + maxY + ", " + maxZ + "]";
        } else {
            return "[" + minX + ", " + minY + ", "
                    + maxX + ", " + maxY + "]";
        }
    }

    /**
     * 克隆当前包围盒
     *
     * @return 包围盒，不会返回null
     * @see Object#clone()
     */
    @Override
    @NotNull
    public BoundingBox clone() {
        return new BoundingBox(this);
    }
}
