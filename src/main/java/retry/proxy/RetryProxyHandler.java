package retry.proxy;

import retry.annotation.Retry;
import retry.annotation.parser.RetryHandlerBuilder;
import retry.callback.Callback;
import retry.callback.MethodExecutingCallback;
import retry.retry.RetryHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RetryProxyHandler implements InvocationHandler {

    private Object service;

    public RetryProxyHandler(Object proxied) {
        this.service = proxied;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.err.println("Method " + method.getName() + " invoked via proxy");
        Method m = service.getClass().getMethod(method.getName(), method.getParameterTypes());
        Callback callback = new MethodExecutingCallback(method, args, service);
        if (m.isAnnotationPresent(Retry.class)) {
            Retry retry = m.getAnnotation(Retry.class);
            RetryHandler handler = new RetryHandlerBuilder().build(retry);
            return handler.execute(callback);
        } else {
            return callback.execute();
        }
    }
}