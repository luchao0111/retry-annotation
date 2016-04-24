package retry.callback;

import java.lang.reflect.Method;

public class MethodExecutingCallback implements Callback {

    private Method method;
    private Object[] args;
    private Object obj;

    public MethodExecutingCallback(Method method, Object[] args, Object obj) {
        this.method = method;
        this.args = args;
        this.obj = obj;
    }

    public Object execute() throws Throwable {
        return method.invoke(obj, args);
    }

}