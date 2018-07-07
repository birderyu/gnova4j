package gnova.config.reader.yaml;

import gnova.annotation.NotNull;
import gnova.config.ConfigException;
import gnova.config.ConfigModule;
import gnova.config.annotation.Configure;
import gnova.config.annotation.Configures;
import gnova.config.reader.InstanceHelper;
import gnova.config.reader.StrategyReader;
import org.yaml.snakeyaml.Yaml;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class YamlStrategyReader implements StrategyReader {

    @Override
    public <T> T read(ConfigModule module) throws ConfigException {

        Object yaml = null;
        try {
            Yaml y = new Yaml();
            yaml = y.load(module.getInputStream());
        } catch (Exception e) {
            throw new ConfigException(e);
        }

        if (yaml instanceof Map && module.getNode() != null) {
            // 子节点
            return build(module.getClazz(), ((Map) yaml).get(module.getNode()));
        } else {
            // 根节点
            return build(module.getClazz(), yaml);
        }
    }

    private <T> T build(Object yaml)
            throws ConfigException {
        if (yaml == null) {
            return null;
        } else if (yaml instanceof Map) {
            throw new ConfigException("无法将一个YAML对象转换为一个未知的结构。");
        } else if (yaml instanceof List) {
            throw new ConfigException("无法将一个YAML数组转换为一个未知的结构。");
        } else {
            // 直接强制转换
            try {
                return (T) yaml;
            } catch (ClassCastException e) {
                throw new ConfigException(e);
            }
        }
    }

    private <T> T build(@NotNull Class<?> clazz, Object yaml)
            throws ConfigException {
        if (yaml == null) {
            return null;
        } else if (yaml instanceof Map) {
            return buildObject(clazz, (Map) yaml);
        } else if (yaml instanceof List) {
            return buildArray(clazz, (List) yaml);
        } else if (yaml instanceof Number) {
            if (clazz == byte.class || clazz == Byte.class) {
                return (T) (Byte) ((Number) yaml).byteValue();
            } else if (clazz == short.class || clazz == Short.class) {
                return (T) (Short) ((Number) yaml).shortValue();
            } else if (clazz == int.class || clazz == Integer.class) {
                return (T) (Integer) ((Number) yaml).intValue();
            } else if (clazz == long.class || clazz == Long.class) {
                return (T) (Long) ((Number) yaml).longValue();
            } else if (clazz == float.class || clazz == Float.class) {
                return (T) (Float) ((Number) yaml).floatValue();
            } else if (clazz == double.class || clazz == Double.class) {
                return (T) (Double) ((Number) yaml).doubleValue();
            } else if (clazz == BigInteger.class) {
                return (T) new BigInteger(yaml.toString());
            } else if (clazz == BigDecimal.class) {
                return (T) new BigDecimal(yaml.toString());
            } else if (clazz == String.class) {
                return (T) yaml.toString();
            } else {
                throw new ConfigException("无法将一个Number类型的值（"
                        + yaml
                        + "）转换为"
                        + clazz
                        + "类型");
            }
        } else {
            return buildSimple(clazz, yaml);
        }
    }

    private <T> T buildSimple(@NotNull Class<?> clazz, Object yaml)
            throws ConfigException {

        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) Boolean.valueOf(yaml.toString());
        } else if (clazz == byte.class || clazz == Byte.class) {
            try {
                return (T) Byte.valueOf(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == short.class || clazz == Short.class) {
            try {
                return (T) Short.valueOf(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == int.class || clazz == Integer.class) {
            try {
                return (T) Integer.valueOf(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == long.class || clazz == Long.class) {
            try {
                return (T) Long.valueOf(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == float.class || clazz == Float.class) {
            try {
                return (T) Float.valueOf(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == double.class || clazz == Double.class) {
            try {
                return (T) Double.valueOf(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == BigInteger.class) {
            try {
                return (T) new BigInteger(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == BigDecimal.class) {
            try {
                return (T) new BigDecimal(yaml.toString());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == String.class) {
            return (T) yaml.toString();
        } else {
            try {
                return (T) yaml;
            } catch (ClassCastException e) {
                throw new ConfigException(e);
            }
        }
    }

    private <T> T buildObject(@NotNull Class<?> clazz, Map yaml)
            throws ConfigException {

        T instance = InstanceHelper.instance(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Configure configure = field.getAnnotation(Configure.class);
            Configures configures = field.getAnnotation(Configures.class);
            if (configure != null) {
                Object o = yaml.get(configure.value());
                if (o != null) {
                    Object v = build(field.getType(), o);
                    InstanceHelper.write(instance, clazz, field, v);
                }
            } else if (configures != null) {
                Object o = yaml.get(configures.value());
                if (o != null) {
                    List a;
                    if (o instanceof List) {
                        a = (List) o;
                    } else {
                        a = new ArrayList();
                        a.add(o);
                    }
                    writeMultiField(instance, clazz, field, a, configures);
                }
            }
        }
        return instance;

    }

    private <T> T buildArray(@NotNull Class<?> clazz, List yaml)
            throws ConfigException {
        if (clazz.isArray()) {
            Object[] array = (Object[]) Array.newInstance(clazz.getComponentType(), yaml.size());
            int index = 0;
            for (Object o : yaml) {
                array[index++] = build(clazz.getComponentType(), o);
            }
            return (T) array;
        } else if (List.class.isAssignableFrom(clazz)) {
            List list = new ArrayList();
            for (Object o : yaml) {
                list.add(build(o));
            }
            return (T) list;
        } else if (List.class.isAssignableFrom(clazz)) {
            List list = new ArrayList();
            for (Object o : yaml) {
                list.add(build(o));
            }
            return (T) list;
        } else if (Set.class.isAssignableFrom(clazz)) {
            Set set = new HashSet();
            for (Object o : yaml) {
                set.add(build(o));
            }
            return (T) set;
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = new ArrayList();
            for (Object o : yaml) {
                collection.add(build(o));
            }
            return (T) collection;
        } else {
            return build(clazz, yaml.get(0));
        }
    }

    private <T> void writeMultiField(@NotNull Object instance,
                                     @NotNull Class<?> clazz,
                                     @NotNull Field field,
                                     @NotNull List yaml,
                                     @NotNull Configures configures)
            throws ConfigException {

        Object v = null;
        switch (configures.container()) {
            case Array: {
                if (configures.clazz() == boolean.class) {
                    boolean[] array = new boolean[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == byte.class) {
                    byte[] array = new byte[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == short.class) {
                    short[] array = new short[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == int.class) {
                    int[] array = new int[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == long.class) {
                    long[] array = new long[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == float.class) {
                    float[] array = new float[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else if (configures.clazz() == double.class) {
                    double[] array = new double[yaml.size()];
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                } else {
                    T[] array = (T[]) Array.newInstance(configures.clazz(), yaml.size());
                    int index = 0;
                    for (Object o : yaml) {
                        array[index++] = build(configures.clazz(), o);
                    }
                    v = array;
                }
            }
            break;
            case Collection: {
                Collection<Object> collection = new ArrayList();
                for (Object o : yaml) {
                    collection.add(build(configures.clazz(), o));
                }
                v = collection;
            }
            break;
            case List: {
                List<Object> list = new ArrayList();
                for (Object o : yaml) {
                    list.add(build(configures.clazz(), o));
                }
                v = list;
            }
            break;
            case Set: {
                Set<Object> set = new HashSet();
                for (Object o : yaml) {
                    set.add(build(configures.clazz(), o));
                }
                v = set;
            }
            break;
        }

        InstanceHelper.write(instance, clazz, field, v);

    }

}
