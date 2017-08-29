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
        assertEquals(f.b.getClass(), B.class);
    }

    @Test
    public void ConstructsClassWithRecursiveDependencies() throws Exception {
        C c = new C();
        container.registerInstance(c);
        container.registerType(E.class);
        container.registerType(D.class);
        G g = container.construct(G.class);
        assertEquals(g.d.getClass(), D.class);
        assertEquals(g.d.c, c);
        assertEquals(g.d.e.getClass(), E.class);
    }
    
    @Test
    public void ConstructsClassWithExtraStringParam() throws Exception {
        A a = new A();
        container.registerInstance(a);
        container.registerType(B.class);
        H h = container.construct(H.class, "varargParam1", "varargParam2");
        assertEquals("varargParam1", h.param1);
        assertEquals("varargParam2", h.param2);
        assertEquals(a, h.a);
        assertEquals(B.class, h.b.getClass());
    }
    
    @Test
    public void ConstructsInnerClassWithExtrastringParam() throws Exception {
        container.registerType(J.class);
        I i = container.construct(I.class, "varargParam1", "varargParam2");
        assertEquals("varargParam1", i.j.param);
        assertEquals("varargParam2", i.param);
    }
}
