package ioc;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

public class Container {
    List<Class> classes;
    Map<Class, Object> instances;
    int counter = 0;

    public Container() {
        this.classes = new ArrayList<Class>();
        this.instances = new HashMap<Class, Object>();
    }

    public <T> T construct(Class<T> clazz, Object... remainingParams)
            throws InstantiationException, IllegalAccessException,
            InvocationTargetException {
        Constructor[] constructors = clazz.getConstructors();
        Constructor<T> constructor = (Constructor<T>)constructors[0];
        Class[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = makeParameters(parameterTypes, remainingParams);
        return constructor.newInstance(parameters);
    }

    private Object[] makeParameters(Class[] parameterTypes,
            Object[] remainingParams) throws InstantiationException,
            IllegalAccessException,InvocationTargetException {
        Object[] output = new Object[parameterTypes.length];
        for (int i=0;i<parameterTypes.length;i++) {
            output[i] = makeParameter(parameterTypes[i], remainingParams);
        }
        return output;
    }

    private <T> T makeParameter(Class<T> parameterType,
            Object[] remainingParams) throws InstantiationException,
            IllegalAccessException, InvocationTargetException {
        if (instances.containsKey(parameterType)) {
            return parameterType.cast(instances.get(parameterType));
        } else if (classes.contains(parameterType)) {
            Class<T> c = (Class<T>)classes.get(classes.indexOf(parameterType));
            return construct(c, remainingParams);
        }
        try {
            return (T)remainingParams[counter++];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public <T> void registerType(Class<T> clazz) {
        classes.add(clazz);
    }

    public <T> void registerInstance(T instance) {
       instances.put(instance.getClass(), instance); 
    }

    public <T> Class<T> getType(Class<T> clazz) {
        if (classes.contains(clazz)) {
            return classes.get(classes.indexOf(clazz));
        } else {
            return null;
        }
    }

    public <T> T getInstance(Class<T> clazz) {
        return (T)instances.get(clazz);
    }
}
