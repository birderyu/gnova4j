package gnova.config.reader.json;

import gnova.core.annotation.NotNull;
import gnova.config.ConfigException;
import gnova.config.ConfigModule;
import gnova.config.annotation.Configure;
import gnova.config.annotation.Configures;
import gnova.config.reader.InstanceHelper;
import gnova.config.reader.StrategyReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class JsonStrategyReader implements StrategyReader {

    @Override
    public <T> T read(ConfigModule module) throws ConfigException {

        Object json = null;
        try {
            json = (new JSONParser()).
                    parse(new InputStreamReader(module.getInputStream()));
        } catch (IOException e) {
            throw new ConfigException(e);
        } catch (ParseException e) {
            throw new ConfigException(e);
        }

        if (json instanceof JSONObject && module.getNode() != null) {

            // 子节点
            return build(module.getClazz(), ((JSONObject) json).get(module.getNode()));
        } else {

            // 根节点
            return build(module.getClazz(), json);
        }
    }

    private <T> T build(Object json)
            throws ConfigException {
        if (json == null) {
            return null;
        } else if (json instanceof JSONObject) {
            throw new ConfigException("无法将一个JSON对象转换为一个未知的结构。");
        } else if (json instanceof JSONArray) {
            throw new ConfigException("无法将一个JSON数组转换为一个未知的结构。");
        } else {
            // 直接强制转换
            try {
                return (T) json;
            } catch (ClassCastException e) {
                throw new ConfigException(e);
            }
        }
    }

    private <T> T build(@NotNull Class<?> clazz, Object json)
            throws ConfigException {
        if (json == null) {
            return null;
        } else if (json instanceof JSONObject) {
            return buildObject(clazz, (JSONObject) json);
        } else if (json instanceof JSONArray) {
            return buildArray(clazz, (JSONArray) json);
        } else if (json instanceof Number) {
            if (clazz == byte.class || clazz == Byte.class) {
                return (T) (Byte) ((Number) json).byteValue();
            } else if (clazz == short.class || clazz == Short.class) {
                return (T) (Short) ((Number) json).shortValue();
            } else if (clazz == int.class || clazz == Integer.class) {
                return (T) (Integer) ((Number) json).intValue();
            } else if (clazz == long.class || clazz == Long.class) {
                return (T) (Long) ((Number) json).longValue();
            } else if (clazz == float.class || clazz == Float.class) {
                return (T) (Float) ((Number) json).floatValue();
            } else if (clazz == double.class || clazz == Double.class) {
                return (T) (Double) ((Number) json).doubleValue();
            } else if (clazz == BigInteger.class) {
                return (T) new BigInteger(json.toString());
            } else if (clazz == BigDecimal.class) {
                return (T) new BigDecimal(json.toString());
            } else if (clazz == String.class) {
                return (T) json.toString();
            } else {
                throw new ConfigException("无法将一个Number类型的值（"
                        + json
                        + "）转换为"
                        + clazz
                        + "类型");
            }
        } else {
            return buildSimple(clazz, json);
        }
    }

    private <T> T buildSimple(@NotNull Class<?> clazz, Object json)
            throws ConfigException {

        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) Boolean.valueOf(json.toString());
        } else if (clazz == byte.class || clazz == Byte.class) {
            try {
                return (T) Byte.valueOf(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == short.class || clazz == Short.class) {
            try {
                return (T) Short.valueOf(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == int.class || clazz == Integer.class) {
            try {
                return (T) Integer.valueOf(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == long.class || clazz == Long.class) {
            try {
                return (T) Long.valueOf(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == float.class || clazz == Float.class) {
            try {
                return (T) Float.valueOf(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == double.class || clazz == Double.class) {
            try {
                return (T) Double.valueOf(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == BigInteger.class) {
            try {
                return (T) new BigInteger(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == BigDecimal.class) {
            try {
                return (T) new BigDecimal(json.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == String.class) {
            return (T) json.toString();
        } else {
            try {
                return (T) json;
            } catch (ClassCastException e) {
                throw new ConfigException(e);
            }
        }
    }

    private <T> T buildObject(@NotNull Class<?> clazz, JSONObject json)
            throws ConfigException {

        T instance = InstanceHelper.instance(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Configure configure = field.getAnnotation(Configure.class);
            Configures configures = field.getAnnotation(Configures.class);
            if (configure != null) {
                Object o = json.get(configure.value());
                if (o != null) {
                    Object v = build(field.getType(), o);
                    InstanceHelper.write(instance, clazz, field, v);
                }
            } else if (configures != null) {
                Object o = json.get(configures.value());
                if (o != null) {
                    JSONArray a;
                    if (o instanceof JSONArray) {
                        a = (JSONArray) o;
                    } else {
                        a = new JSONArray();
                        a.add(o);
                    }
                    writeMultiField(instance, clazz, field, a, configures);
                }
            }
        }
        return instance;

    }

    private <T> T buildArray(@NotNull Class<?> clazz, JSONArray json)
            throws ConfigException {
        if (clazz.isArray()) {
            Object[] array = (Object[]) Array.newInstance(clazz.getComponentType(), json.size());
            int index = 0;
            for (Object o : json) {
                array[index++] = build(clazz.getComponentType(), o);
            }
            return (T) array;
        } else if (List.class.isAssignableFrom(clazz)) {
            List list = new ArrayList();
            for (Object o : json) {
                list.add(build(o));
            }
            return (T) list;
        } else if (List.class.isAssignableFrom(clazz)) {
            List list = new ArrayList();
            for (Object o : json) {
                list.add(build(o));
            }
            return (T) list;
        } else if (Set.class.isAssignableFrom(clazz)) {
            Set set = new HashSet();
            for (Object o : json) {
                set.add(build(o));
            }
            return (T) set;
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = new ArrayList();
            for (Object o : json) {
                collection.add(build(o));
            }
            return (T) collection;
        } else {
            return build(clazz, json.get(0));
        }
    }

    private <T> void writeMultiField(@NotNull Object instance,
                                     @NotNull Class<?> clazz,
                                     @NotNull Field field,
                                     @NotNull JSONArray json,
                                     @NotNull Configures configures)
            throws ConfigException {

        Object v = null;
        switch (configures.container()) {
            case Array: {
                if (configures.clazz() == boolean.class) {
                    boolean[] array = new boolean[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == byte.class) {
                    byte[] array = new byte[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == short.class) {
                    short[] array = new short[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == int.class) {
                    int[] array = new int[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == long.class) {
                    long[] array = new long[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == float.class) {
                    float[] array = new float[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == double.class) {
                    double[] array = new double[json.size()];
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else {
                    T[] array = (T[]) Array.newInstance(configures.clazz(), json.size());
                    int index = 0;
                    for (Object o : json) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                }
            }
            break;
            case Collection: {
                Collection<Object> collection = new ArrayList();
                for (Object o : json) {
                    collection.add(build(configures.clazz(), o));
                }
                v = collection;
            }
            break;
            case List: {
                List<Object> list = new ArrayList();
                for (Object o : json) {
                    list.add(build(configures.clazz(), o));
                }
                v = list;
            }
            break;
            case Set: {
                Set<Object> set = new HashSet();
                for (Object o : json) {
                    set.add(build(configures.clazz(), o));
                }
                v = set;
            }
            break;
        }

        InstanceHelper.write(instance, clazz, field, v);

    }

}
