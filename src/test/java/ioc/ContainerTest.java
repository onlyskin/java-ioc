package ioc;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContainerTest {
    private Container container = new Container();

    @Test
    public void ConstructsClassWithNoDependencies()
        throws InstantiationException, IllegalAccessException {
        Example example = container.construct(Example.class);
        assertNotNull(example);
    }
}
