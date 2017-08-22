package ioc;

public class Container {

    public <T> T construct(Class<T> clazz)
        throws InstantiationException, IllegalAccessException {
            return clazz.newInstance();
    }

}
