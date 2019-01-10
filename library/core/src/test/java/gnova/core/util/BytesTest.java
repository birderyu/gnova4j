package gnova.core.util;

import gnova.core.Bytes;
import gnova.core.Endian;
import org.junit.Assert;
import org.junit.Test;

public class BytesTest {

    @Test
    public void testBytes() {

        byte byteValue1 = 1;
        byte[] bytes = Bytes.toBytes(byteValue1, Endian.BIG_ENDIAN);
        byte byteValue2 = Bytes.toByte(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(byteValue1, byteValue2);
        bytes = Bytes.toBytes(byteValue2, Endian.LITTLE_ENDIAN);
        byte byteValue3 = Bytes.toByte(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(byteValue2, byteValue3);

        byteValue1 = -1;
        bytes = Bytes.toBytes(byteValue1, Endian.BIG_ENDIAN);
        byteValue2 = Bytes.toByte(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(byteValue1, byteValue2);
        bytes = Bytes.toBytes(byteValue2, Endian.LITTLE_ENDIAN);
        byteValue3 = Bytes.toByte(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(byteValue2, byteValue3);

        char charValue1 = 'A';
        bytes = Bytes.toBytes(charValue1, Endian.BIG_ENDIAN);
        char charValue2 = Bytes.toChar(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(charValue1, charValue2);
        bytes = Bytes.toBytes(charValue2, Endian.LITTLE_ENDIAN);
        char charValue3 = Bytes.toChar(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(charValue2, charValue3);

        short shortValue1 = 12345;
        bytes = Bytes.toBytes(shortValue1, Endian.BIG_ENDIAN);
        short shortValue2 = Bytes.toShort(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(shortValue1, shortValue2);
        bytes = Bytes.toBytes(shortValue2, Endian.LITTLE_ENDIAN);
        short shortValue3 = Bytes.toShort(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(shortValue2, shortValue3);

        shortValue1 = -12345;
        bytes = Bytes.toBytes(shortValue1, Endian.BIG_ENDIAN);
        shortValue2 = Bytes.toShort(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(shortValue1, shortValue2);
        bytes = Bytes.toBytes(shortValue2, Endian.LITTLE_ENDIAN);
        shortValue3 = Bytes.toShort(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(shortValue2, shortValue3);

        int intValue1 = 1934512345;
        bytes = Bytes.toBytes(intValue1, Endian.BIG_ENDIAN);
        int intValue2 = Bytes.toInt(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(intValue1, intValue2);
        bytes = Bytes.toBytes(intValue2, Endian.LITTLE_ENDIAN);
        int intValue3 = Bytes.toInt(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(intValue2, intValue3);

        intValue1 = -1934512345;
        bytes = Bytes.toBytes(intValue1, Endian.BIG_ENDIAN);
        intValue2 = Bytes.toInt(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(intValue1, intValue2);
        bytes = Bytes.toBytes(intValue2, Endian.LITTLE_ENDIAN);
        intValue3 = Bytes.toInt(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(intValue2, intValue3);

        long longValue1 = 12345123451L;
        bytes = Bytes.toBytes(longValue1, Endian.BIG_ENDIAN);
        long longValue2 = Bytes.toLong(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(longValue1, longValue1);
        bytes = Bytes.toBytes(longValue2, Endian.LITTLE_ENDIAN);
        long longValue3 = Bytes.toLong(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(longValue2, longValue3);

        longValue1 = -12345123451L;
        bytes = Bytes.toBytes(longValue1, Endian.BIG_ENDIAN);
        longValue2 = Bytes.toLong(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(longValue1, longValue1);
        bytes = Bytes.toBytes(longValue2, Endian.LITTLE_ENDIAN);
        longValue3 = Bytes.toLong(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(longValue2, longValue3);

        float floatValue1 = 1.234F;
        bytes = Bytes.toBytes(floatValue1, Endian.BIG_ENDIAN);
        float floatValue2 = Bytes.toFloat(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(floatValue1, floatValue2, 0.00001F);
        bytes = Bytes.toBytes(floatValue2, Endian.LITTLE_ENDIAN);
        float floatValue3 = Bytes.toFloat(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(floatValue2, floatValue3, 0.00001F);

        floatValue1 = -1.234F;
        bytes = Bytes.toBytes(floatValue1, Endian.BIG_ENDIAN);
        floatValue2 = Bytes.toFloat(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(floatValue1, floatValue2, 0.00001F);
        bytes = Bytes.toBytes(floatValue2, Endian.LITTLE_ENDIAN);
        floatValue3 = Bytes.toFloat(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(floatValue2, floatValue3, 0.00001F);

        double doubleValue1 = 1.234567890;
        bytes = Bytes.toBytes(doubleValue1, Endian.BIG_ENDIAN);
        double doubleValue2 = Bytes.toDouble(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(doubleValue1, doubleValue2, 0.000000001);
        bytes = Bytes.toBytes(doubleValue2, Endian.LITTLE_ENDIAN);
        double doubleValue3 = Bytes.toDouble(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(doubleValue2, doubleValue3, 0.000000001);

        doubleValue1 = -1.234567890;
        bytes = Bytes.toBytes(doubleValue1, Endian.BIG_ENDIAN);
        doubleValue2 = Bytes.toDouble(bytes, Endian.BIG_ENDIAN);
        Assert.assertEquals(doubleValue1, doubleValue2, 0.000000001);
        bytes = Bytes.toBytes(doubleValue2, Endian.LITTLE_ENDIAN);
        doubleValue3 = Bytes.toDouble(bytes, Endian.LITTLE_ENDIAN);
        Assert.assertEquals(doubleValue2, doubleValue3, 0.000000001);
    }
}
