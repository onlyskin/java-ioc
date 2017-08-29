package ioc;

public class I {
    String param;
    J j;
    
    public I(J j, String param) {
        this.j = j;
        this.param = param;
    }

}
