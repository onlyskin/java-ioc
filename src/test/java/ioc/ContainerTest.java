package ioc;

import org.junit.Test;
import static org.junit.Assert.*;

public class ContainerTest {
    private Container container = new Container();

    @Test
    public void ConstructsClassWithNoDependencies()
        throws Exception, InstantiationException, IllegalAccessException {
        A a = container.construct(A.class);
        assertNotNull(a);
    }

    @Test
    public void RegistersType() throws Exception {
        container.registerType(A.class);
        assertEquals(container.getType(A.class), A.class);
        assertNull(container.getType(B.class));
    }

    @Test
    public void RegistersInstance() throws Exception {
        C c = new C();
        container.registerInstance(c);
        assertEquals(container.getInstance(C.class), c);
        assertEquals(container.getInstance(C.class).instanceVariable,
                "instanceVariable");
    }

    @Test
    public void ConstructsClassWithInstanceDependencies() throws Exception {
        container.registerType(D.class);
        C c = new C();
        container.registerInstance(c);
        E e = new E();
        container.registerInstance(e);
        D d = container.construct(D.class);
        assertEquals(d.c, c);
        assertEquals(d.e, e);
    }

    @Test
    public void ConstructsClassWithTypeDependency() throws Exception {
        container.registerType(A.class);
        container.registerType(B.class);
        F f = container.construct(F.class);
        assertEquals(f.a.getClass(), A.class);
    }
}
