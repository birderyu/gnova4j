package gnova.geometry.io;

import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;

/**
 * 几何对象输入接口
 *
 * <p>该接口用于将其他对象转化为几何对象，比如将字符串对象转化成几何对象；或从输入流中读取一个几何对象。
 */
public interface GeometryReader {

    /**
     * 将其他对象转换为几何对象或从输入流中读取一个几何对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为几何对象，如将字符串对象转换为几何对象；
     *            <br>若参数为输入流，那么表示从流中读取一个几何对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 几何对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    Geometry read(@NotNull Object obj) throws GeometryIOException;

    /**
     * 将其他对象转换为点对象或从输入流中读取一个点对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为点对象，如将字符串对象转换为点对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个点对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 点对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default Point readPoint(@NotNull Object obj) throws GeometryIOException {
        try {
            return (Point) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为线串对象或从输入流中读取一个线串对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为线串对象，如将字符串对象转换为线串对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个线串对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 线串对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default LineString readLineString(@NotNull Object obj) throws GeometryIOException {
        try {
            return (LineString) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为线环对象或从输入流中读取一个线环对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为线环对象，如将字符串对象转换为线环对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个线环对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 线环对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default LinearRing readLinearRing(@NotNull Object obj) throws GeometryIOException {
        try {
            return (LinearRing) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多边形对象或从输入流中读取一个多边形对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为多边形对象，如将字符串对象转换为多边形对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个多边形对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default Polygon readPolygon(@NotNull Object obj) throws GeometryIOException {
        try {
            return (Polygon) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为几何集合对象或从输入流中读取一个几何集合对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为几何集合对象，如将字符串对象转换为几何集合对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个几何集合对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 几何集合对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default GeometryCollection readGeometryCollection(@NotNull Object obj) throws GeometryIOException {
        try {
            return (GeometryCollection) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多点对象或从输入流中读取一个多点对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为多点对象，如将字符串对象转换为多点对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个多点对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 多点对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default MultiPoint readMultiPoint(@NotNull Object obj) throws GeometryIOException {
        try {
            return (MultiPoint) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多线串对象或从输入流中读取一个多线串对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为多线串对象，如将字符串对象转换为多线串对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个多线串对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 多线串对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default MultiLineString readMultiLineString(@NotNull Object obj) throws GeometryIOException {
        try {
            return (MultiLineString) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

    /**
     * 将其他对象转换为多多边形对象或从输入流中读取一个多多边形对象
     *
     * @param obj 其他对象或输入流，不允许为null，
     *            <br>若参数为其他对象，则将该对象转换为多多边形对象，如将字符串对象转换为多多边形对象；
     *            <br>若参数为输入流，那么表示从流中读取若干元素成为一个多多边形对象。
     *            <br>若参数为输入流，那么它应该是{@link java.io.InputStream}或{@link java.io.Reader}，视具体情况而定。
     * @return 多多边形对象，不会返回null
     * @throws GeometryIOException 若转换失败或读取失败，则抛出异常
     */
    @NotNull
    default MultiPolygon readMultiPolygon(@NotNull Object obj) throws GeometryIOException {
        try {
            return (MultiPolygon) read(obj);
        } catch (ClassCastException e) {
            throw new GeometryIOException(e);
        }
    }

}
