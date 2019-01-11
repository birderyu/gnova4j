package gnova.query.expression.util;

import gnova.core.Bytes;
import gnova.core.Endian;
import gnova.core.annotation.NotNull;
import gnova.geometry.model.*;
import gnova.query.expression.*;

import java.io.*;
import java.util.Arrays;

public class ExpressionSerializer {

    /**
     * 表达式的标志位
     *
     * <p>表达式的标志位位于表达式的首部，两个字节，没有任何意义，仅仅作为表达式开始的标识
     */
    private static final byte[] EXPRESSION_FLAG = { 02, 28 };

    /**
     * 值表达式的标志位
     */
    private static final byte VALUE_EXPRESSION_FLAG = 0;

    private static final byte NULL_VALUE_FLAG = ValueType.Null.getType(); // 1
    private static final byte BOOLEAN_VALUE_FLAG = ValueType.Boolean.getType(); // 2
    private static final byte INT32_VALUE_FLAG = ValueType.Int32.getType(); // 3
    private static final byte INT64_VALUE_FLAG = ValueType.Int64.getType(); // 4
    private static final byte DOUBLE_VALUE_FLAG = ValueType.Double.getType(); // 5
    private static final byte STRING_VALUE_FLAG = ValueType.String.getType(); // 6
    private static final byte BYTES_VALUE_FLAG = ValueType.Bytes.getType(); // 7
    private static final byte DATE_VALUE_FLAG = ValueType.Date.getType(); // 8
    private static final byte TIMESTAMP_VALUE_FLAG = ValueType.Timestamp.getType(); // 9
    private static final byte GEOMETRY_VALUE_FLAG = ValueType.Geometry.getType(); // 10
    private static final byte LIST_VALUE_FLAG = ValueType.List.getType(); // 11
    private static final byte KEY_VALUE_FLAG = ValueType.Key.getType(); // 12
    private static final byte PLACEHOLDER_VALUE_FLAG = ValueType.Placeholder.getType(); // 13

    /**
     * 空几何对象的标志位
     */
    private static final byte EMPTY_GEOMETRY_VALUE_FLAG = 14;

    /**
     * 坐标类型的标志位
     */
    private static final byte COORDINATE_VALUE_FLAG = 15;

    /**
     * 线环类型的标志位
     */
    private static final byte LINEAR_RING_VALUE_FLAG = 16;

    /**
     * 多边形类型的标志位
     */
    private static final byte POLYGON_VALUE_FLAG = 17;

    /**
     * 多多边形类型的标志位
     */
    private static final byte MULTI_POLYGON_VALUE_FLAG = 18;

    /**
     * 谓词表达式的标志位
     */
    private static final byte PREDICATE_EXPRESSION_FLAG = 50;

    /**
     * 简单谓词表达式的标志位
     */
    private static final byte SIMPLE_EXPRESSION_FLAG = 51;

    /**
     * 字节顺序
     */
    private static final Endian ENDIAN = Endian.BIG_ENDIAN;

    /**
     * 字符集的名称
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 序列化一个表达式到输出流中
     *
     * @param expression 表达式，不允许为null
     * @param outputStream 输出流，不允许为null
     * @throws IOException 若序列化失败，则抛出此异常
     */
    public void serialize(@NotNull Expression expression,
                          @NotNull OutputStream outputStream)
            throws IOException {

        byte[] bytes = serialize(expression);
        outputStream.write(bytes);

    }

    /**
     * 序列化一个表达式成为一个字节数组
     *
     * @param expression 表达式，不允许为null
     * @return 字节数组，不会返回null
     * @throws IOException 若序列化失败，则抛出此异常
     */
    @NotNull
    public byte[] serialize(@NotNull Expression expression)
            throws IOException {

        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {

            // 字节总数，初始化为0，用于占位
            int size = 0;
            baos.write(EXPRESSION_FLAG);
            baos.write(Bytes.toBytes(size, ENDIAN));

            if (expression.isValue()) {
                // 序列化值表达式
                serialize((ValueExpression) expression, baos);
            } else {
                // 序列化谓词表达式
                serialize((PredicateExpression) expression, baos);
            }

            // 字节总数，等于首部的长度加数据的字节数
            size = EXPRESSION_FLAG.length + 4 + baos.size();
            bytes = baos.toByteArray();
            Bytes.attachToBytes(size, bytes, EXPRESSION_FLAG.length, ENDIAN);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            // 可以不需要，为了编程的完整性，加上这一个步骤
            baos.close();
        }

        if (bytes == null) {
            throw new IOException("表达式序列化失败：" + expression);
        }
        return bytes;
    }

    /**
     * 从输入流中反序列化出一个表达式
     *
     * @param inputStream 输入流，不允许为null
     * @return 表达式，不会返回null
     * @throws IOException 若反序列化失败，则抛出此异常
     */
    @NotNull
    public Expression deserialize(@NotNull InputStream inputStream)
            throws IOException {
        // TODO
        byte[] buffer = new byte[1024];
        inputStream.read(buffer, 0, EXPRESSION_FLAG.length);
        for (int i = 0; i < EXPRESSION_FLAG.length; i++) {
            if (buffer[i] != EXPRESSION_FLAG[i]) {
                throw new IOException("反序列化失败，首部不合法：" + Arrays.toString(buffer));
            }
        }
        inputStream.read(buffer, 0, 4);
        int size = Bytes.toInt(buffer, ENDIAN);
        return null;
    }

    @NotNull
    public Expression deserialize(@NotNull byte[] bytes)
            throws IOException {
        return deserialize(bytes, 0);
    }

    @NotNull
    public Expression deserialize(@NotNull byte[] bytes, int offset)
            throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes, offset, bytes.length);
        try {
            return deserialize(bais);
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            bais.close();
        }

    }

    private void serialize(@NotNull ValueExpression valueExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(VALUE_EXPRESSION_FLAG);
        switch (valueExpression.getValueType()) {
            case Null:
                serialize((NullExpression) valueExpression, outputStream);
                return;
            case Boolean:
                serialize((BooleanExpression) valueExpression, outputStream);
                return;
            case Int32:
                serialize((Int32Expression) valueExpression, outputStream);
                return;
            case Int64:
                serialize((Int64Expression) valueExpression, outputStream);
                return;
            case Double:
                serialize((DoubleExpression) valueExpression, outputStream);
                return;
            case String:
                serialize((StringExpression) valueExpression, outputStream);
                return;
            case Bytes:
                serialize((BytesExpression) valueExpression, outputStream);
                return;
            case Date:
                serialize((DateExpression) valueExpression, outputStream);
                return;
            case Timestamp:
                serialize((TimestampExpression) valueExpression, outputStream);
                return;
            case Geometry:
                serialize((GeometryExpression) valueExpression, outputStream);
                return;
            case List:
                serialize((ListExpression) valueExpression, outputStream);
                return;
            case Key:
                serialize((KeyExpression) valueExpression, outputStream);
                return;
            case Placeholder:
                serialize((PlaceholderExpression) valueExpression, outputStream);
                return;
        }
    }

    private void serialize(@NotNull NullExpression nullExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(NULL_VALUE_FLAG);
        // do nothing
    }

    private void serialize(@NotNull BooleanExpression booleanExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Boolean.getType());
        outputStream.write(Bytes.toBytes(booleanExpression.asBoolean(false), ENDIAN));
    }

    private void serialize(@NotNull Int32Expression int32Expression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Int32.getType());
        outputStream.write(Bytes.toBytes(int32Expression.asInt32(0), ENDIAN));
    }

    private void serialize(@NotNull Int64Expression int64Expression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Int64.getType());
        outputStream.write(Bytes.toBytes(int64Expression.asInt64(0L), ENDIAN));
    }

    private void serialize(@NotNull DoubleExpression doubleExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Double.getType());
        outputStream.write(Bytes.toBytes(doubleExpression.asDouble(0.0), ENDIAN));
    }

    private void serialize(@NotNull StringExpression stringExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.String.getType());
        serialize(stringExpression.getValue(), outputStream);
    }

    private void serialize(@NotNull BytesExpression bytesExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Bytes.getType());
        serialize(bytesExpression.getValue(), outputStream);
    }

    private void serialize(@NotNull DateExpression dateExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Date.getType());
        // 日期格式首先转换成时间戳格式后，再传输
        outputStream.write(Bytes.toBytes(dateExpression.getValue().getTime(), ENDIAN));
    }

    private void serialize(@NotNull TimestampExpression timestampExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Timestamp.getType());
        outputStream.write(Bytes.toBytes(timestampExpression.asTimestamp(), ENDIAN));
    }

    private void serialize(@NotNull GeometryExpression geometryExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Geometry.getType());
        serialize(geometryExpression.getValue(), outputStream);
    }

    private void serialize(@NotNull ListExpression listExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.List.getType());
        if (listExpression.isEmpty()) {
            outputStream.write(Bytes.toBytes(0, ENDIAN));
        } else {
            for (ValueExpression valueExpression : listExpression) {
                serialize(valueExpression, outputStream);
            }
        }
    }

    private void serialize(@NotNull KeyExpression keyExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Key.getType());
        serialize(keyExpression.getValue(), outputStream);
    }

    private void serialize(@NotNull PlaceholderExpression placeholderExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        outputStream.write(ValueType.Placeholder.getType());
    }

    private void serialize(@NotNull String value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        if (value.isEmpty()) {
            outputStream.write(Bytes.toBytes(0, ENDIAN));
        } else {
            byte[] encode = value.getBytes(CHARSET_NAME);
            int length = encode.length;
            outputStream.write(Bytes.toBytes(length, ENDIAN));
            outputStream.write(encode);
        }
    }

    private void serialize(@NotNull byte[] value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        if (value.length == 0) {
            outputStream.write(Bytes.toBytes(0, ENDIAN));
        } else {
            int length = value.length;
            outputStream.write(Bytes.toBytes(length, ENDIAN));
            outputStream.write(value);
        }
    }

    private void serialize(@NotNull Geometry value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        if (value.getType() == GeometryType.None) {
            // 空几何直接赋一个标志位
            outputStream.write(Bytes.toBytes(EMPTY_GEOMETRY_VALUE_FLAG, ENDIAN));
        } else if (value.getType() == GeometryType.Polygon) {
            serialize((Polygon) value, outputStream);
        } else if (value.getType() == GeometryType.MultiPolygon) {
            serialize((MultiPolygon) value, outputStream);
        } else {
            throw new IOException("不支持的几何类型：" + value.getType());
        }
    }

    private void serialize(@NotNull Coordinate value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(Bytes.toBytes(COORDINATE_VALUE_FLAG, ENDIAN));

        int ordinateSize = 2;
        if (value.hasZ()) {
            ++ordinateSize;
        }
        if (value.hasM()) {
            ++ordinateSize;
        }
        outputStream.write(Bytes.toBytes(ordinateSize, ENDIAN));
        outputStream.write(Bytes.toBytes(value.getX(), ENDIAN));
        outputStream.write(Bytes.toBytes(value.getY(), ENDIAN));
        if (value.hasZ()) {
            outputStream.write(Bytes.toBytes(value.getZ(), ENDIAN));
        }
        if (value.hasM()) {
            outputStream.write(Bytes.toBytes(value.getM(), ENDIAN));
        }
    }

    private void serialize(@NotNull LinearRing value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(Bytes.toBytes(LINEAR_RING_VALUE_FLAG, ENDIAN));

        // 坐标的个数
        outputStream.write(Bytes.toBytes(value.size(), ENDIAN));

        // 序列化每一个坐标
        for (Point point : value) {
            serialize(point.getCoordinate(), outputStream);
        }

    }

    private void serialize(@NotNull Polygon value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(Bytes.toBytes(POLYGON_VALUE_FLAG, ENDIAN));

        // 线环的个数
        outputStream.write(Bytes.toBytes(value.size(), ENDIAN));

        // 序列化每一个线环
        for (LinearRing linearRing : value) {
            serialize(linearRing, outputStream);
        }

    }

    private void serialize(@NotNull MultiPolygon value,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(Bytes.toBytes(MULTI_POLYGON_VALUE_FLAG, ENDIAN));

        // 多边形的个数
        outputStream.write(Bytes.toBytes(value.size(), ENDIAN));

        // 序列化每一个多边形
        for (Polygon polygon : value) {
            serialize(polygon, outputStream);
        }
    }

    private void serialize(@NotNull PredicateExpression predicateExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(PREDICATE_EXPRESSION_FLAG);
        if (predicateExpression.isAlwaysTrue()) {
            // TODO
        } else if (predicateExpression.isAlwaysFalse()) {
            // TODO
        } else if (predicateExpression.isSimple()) {
            serialize(predicateExpression.asSimple(), outputStream);
        } else if (predicateExpression.isMultiple()) {
            // TODO
        } else if (predicateExpression.isNegative()) {
            // TODO
        }

    }

    private void serialize(@NotNull SimpleExpression simpleExpression,
                           @NotNull OutputStream outputStream)
            throws IOException {

        // 标志位
        outputStream.write(SIMPLE_EXPRESSION_FLAG);
        // 序列化左值
        serialize(simpleExpression.getLeft(), outputStream);
        // 序列化值谓词
        // TODO
        switch (simpleExpression.getPredicate()) {
            case EQ:
                break;
            case NEQ:
                break;
            case LT:
                break;
            case LTE:
                break;
            case GT:
                break;
            case GTE:
                break;
            case LIKE:
                break;
            case IN:
                break;
            case NIN:
                break;
            case INTERSECT:
                break;
            case WITHIN:
                break;
        }
        // 序列化右值
        serialize(simpleExpression.getRight(), outputStream);
    }

}
