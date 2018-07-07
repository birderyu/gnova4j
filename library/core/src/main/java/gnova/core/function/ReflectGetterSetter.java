package gnova.core.function;

import gnova.core.annotation.NotNull;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 使用反射实现（reflection）的Getter和Setter
 *
 * <p>通过Java Bean的规范，使用反射机制实现值的获取和设置
 *
 * @see Getter
 * @see Setter
 * @author birderyu
 * @version 1.0.0
 */
public class ReflectGetterSetter
        implements Getter, Setter {

    /**
     * 符合Java Bean标准的对象，不会为null
     */
    @NotNull
    private final Object bean;

    /**
     * 构建一个使用反射实现的Getter和Setter
     *
     * @param bean 符合Java Bean标准的对象，不允许null
     */
    public ReflectGetterSetter(@NotNull Object bean) {
        this.bean = bean;
    }

    /**
     * 获取一个值
     *
     * @param key 键，不允许为null
     * @param <T> 值的类型
     * @return 字段值，若不存在该字段，则返回null
     * @see Getter#getValue(String)
     */
    @Override
    public <T> T getValue(@NotNull String key) {

        Class<?> clazz = bean.getClass();
        try {
            Field field = clazz.getDeclaredField(key);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method method = pd.getReadMethod();
            Object value = method.invoke(bean);
            return (T) value;
        } catch (NoSuchFieldException e) {
            return null;
        } catch (IntrospectionException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 设置一个值
     *
     * 当键存在时，才会设置这个值，若键不存在，该方法不会增加值
     *
     * @param key 键，不允许为null
     * @param value 值
     * @param <T> 值的类型
     * @see Setter#setValue(String, Object)
     */
    @Override
    public <T> void setValue(String key, T value) {

        Class<?> clazz = bean.getClass();
        try {
            Field field = clazz.getDeclaredField(key);
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method method = pd.getWriteMethod();
            method.invoke(bean, value);
        } catch (NoSuchFieldException e) {
            // do nothing
        } catch (IntrospectionException e) {
            // do nothing
        } catch (IllegalAccessException e) {
            // do nothing
        } catch (InvocationTargetException e) {
            // do nothing
        }
    }

}
