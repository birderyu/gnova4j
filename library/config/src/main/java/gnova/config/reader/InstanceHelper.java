package gnova.config.reader;

import gnova.annotation.NotNull;
import gnova.config.ConfigException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InstanceHelper {

    public static <T> T instance(@NotNull Class<?> clazz)
            throws ConfigException {
        T instance = null;
        try {
            instance = (T) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new ConfigException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigException(e);
        } catch (ClassCastException e) {
            throw new ConfigException(e);
        }
        return instance;
    }

    public static void write(Object instance,
                             Class<?> clazz,
                             Field field,
                             Object value) throws ConfigException {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method method = pd.getWriteMethod();
            method.invoke(instance, value);
        } catch (IntrospectionException e) {
            throw new ConfigException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigException(e);
        } catch (InvocationTargetException e) {
            throw new ConfigException(e);
        }
    }

}
