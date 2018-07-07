package gnova.config.reader.xml;

import gnova.core.annotation.NotNull;
import gnova.config.ConfigException;
import gnova.config.annotation.Configure;
import gnova.config.ConfigModule;
import gnova.config.annotation.Configures;
import gnova.config.annotation.Container;
import gnova.config.reader.InstanceHelper;
import gnova.config.reader.StrategyReader;
import gnova.config.reader.StreamHelper;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class XmlStrategyReader
        implements StrategyReader {

    @Override
    public <T> T read(ConfigModule module) throws ConfigException {

        String xml = null;
        try {
            xml = StreamHelper.stream2String(module.getInputStream());
        } catch (IOException e) {
            throw new ConfigException(e);
        }

        // 节点
        Element node = null;
        try {
            Element root = DomHelper.readElement(xml);
            if (module.getNode() != null) {
                // 子节点
                Map<String, Object> c = DomHelper.subElements(root);
                Object o = c.get(module.getNode());
                if (o != null) {
                    // 子节点
                    if (o instanceof List) {
                        // 只获取第一个节点
                        node = ((List<Element>) o).get(0);
                    } else {
                        node = (Element) o;
                    }
                } else if (root.getName() == module.getNode()) {
                    // 根节点
                    node = root;
                } else {
                    throw new ConfigException("不包含节点：" + module.getNode());
                }
            } else {
                // 根节点
                node = root;
            }
        } catch (DocumentException e) {
            throw new ConfigException(e);
        }

        // 获取当前节点的子节点
        return build(module.getClazz(), node);
    }

    private <T> T build(@NotNull Class<?> clazz, Element element)
            throws ConfigException {
        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) Boolean.valueOf(element.getTextTrim());
        } else if (clazz == byte.class || clazz == Byte.class) {
            try {
                return (T) Byte.valueOf(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == int.class || clazz == Integer.class) {
            try {
                return (T) Integer.valueOf(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == long.class || clazz == Long.class) {
            try {
                return (T) Long.valueOf(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == short.class || clazz == Short.class) {
            try {
                return (T) Short.valueOf(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == float.class || clazz == Float.class) {
            try {
                return (T) Float.valueOf(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == double.class || clazz == Double.class) {
            try {
                return (T) Double.valueOf(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == BigInteger.class) {
            try {
                return (T) new BigInteger(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == BigDecimal.class) {
            try {
                return (T) new BigDecimal(element.getTextTrim());
            } catch (NumberFormatException e) {
                throw new ConfigException(e);
            }
        } else if (clazz == String.class) {
            return (T) element.getTextTrim();
        } else {
            return buildBean(clazz, DomHelper.subElements(element));
        }
    }

    private <T> T buildBean(@NotNull Class<?> clazz, Map<String, Object> elements)
            throws ConfigException {

        T instance = InstanceHelper.instance(clazz);
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Configure configure = field.getAnnotation(Configure.class);
            Configures configures = field.getAnnotation(Configures.class);
            if (configure != null) {
                Object o = elements.get(configure.value());
                if (o != null) {
                    Element element = null;
                    if (o instanceof List) {
                        element = ((List<Element>) o).get(0);
                    } else {
                        element = (Element) o;
                    }
                    writeSingleField(instance, clazz, field, element);
                }
            } else if (configures != null) {
                Object o = elements.get(configures.value());
                if (o != null) {
                    List<Element> list = null;
                    if (o instanceof List) {
                        list = (List<Element>) o;
                    } else {
                        list = new ArrayList<>();
                        list.add((Element) o);
                    }
                    writeMultiField(instance, clazz,
                            field,
                            list,
                            configures.clazz(), configures.container());
                }
            }
        }
        return instance;
    }

    private void writeSingleField(@NotNull Object instance,
                                  @NotNull Class<?> clazz,
                                  @NotNull Field field,
                                  @NotNull Element elememt)
            throws ConfigException {

        Object v = null;
        if (field.getType() == boolean.class || field.getType() == Boolean.class) {
            v = Boolean.valueOf(elememt.getTextTrim());
        } else if (field.getType() == int.class || field.getType() == Integer.class) {
            try {
                v = Integer.valueOf(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == float.class || field.getType() == Float.class) {
            try {
                v = Float.valueOf(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == long.class || field.getType() == Long.class) {
            try {
                v = Long.valueOf(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == double.class || field.getType() == Double.class) {
            try {
                v = Double.valueOf(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == short.class || field.getType() == Short.class) {
            try {
                v = Short.valueOf(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == byte.class || field.getType() == Byte.class) {
            try {
                v = Byte.valueOf(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == BigInteger.class) {
            try {
                v = new BigInteger(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == BigDecimal.class) {
            try {
                v = new BigDecimal(elememt.getTextTrim());
            } catch (NumberFormatException e) {
                return;
            }
        } else if (field.getType() == String.class) {
            v = elememt.getTextTrim();
        } else {
            v = build(field.getType(), elememt);
        }

        InstanceHelper.write(instance, clazz, field, v);
    }

    private <T> void writeMultiField(@NotNull Object instance,
                                     @NotNull Class<?> clazz,
                                     @NotNull Field field,
                                     @NotNull List<Element> elements,
                                     @NotNull Class<?> component,
                                     @NotNull Container container)
            throws ConfigException {

        Object v = null;
        switch (container) {
            case Array: {
                if (component == boolean.class) {
                    boolean[] array = new boolean[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else if (component == byte.class) {
                    byte[] array = new byte[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else if (component == short.class) {
                    short[] array = new short[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else if (component == int.class) {
                    int[] array = new int[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else if (component == long.class) {
                    long[] array = new long[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else if (component == float.class) {
                    float[] array = new float[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else if (component == double.class) {
                    double[] array = new double[elements.size()];
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                } else {
                    T[] array = (T[]) Array.newInstance(component, elements.size());
                    int index = 0;
                    for (Element element : elements) {
                        array[index++] = build(component, element);
                    }
                    v = array;
                }
            }
                break;
            case Collection: {
                Collection<Object> collection = new ArrayList();
                for (Element element : elements) {
                    collection.add(build(component, element));
                }
                v = collection;
            }
            break;
            case List: {
                List<Object> list = new ArrayList();
                for (Element element : elements) {
                    list.add(build(component, element));
                }
                v = list;
            }
                break;
            case Set: {
                Set<Object> set = new HashSet();
                for (Element element : elements) {
                    set.add(build(component, element));
                }
                v = set;
            }
                break;
        }

        InstanceHelper.write(instance, clazz, field, v);
    }
}
